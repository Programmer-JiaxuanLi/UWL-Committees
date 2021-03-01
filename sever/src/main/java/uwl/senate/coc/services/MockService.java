package uwl.senate.coc.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uwl.senate.coc.entities.College;
import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Criteria;
import uwl.senate.coc.entities.Department;
import uwl.senate.coc.entities.Duty;
import uwl.senate.coc.entities.Gender;
import uwl.senate.coc.entities.Role;
import uwl.senate.coc.entities.Survey;
import uwl.senate.coc.entities.SurveyResponse;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.repositories.CollegeRepository;
import uwl.senate.coc.repositories.CommitteeRepository;
import uwl.senate.coc.repositories.CriteriaRepository;
import uwl.senate.coc.repositories.DepartmentRepository;
import uwl.senate.coc.repositories.DutyRepository;
import uwl.senate.coc.repositories.GenderRepository;
import uwl.senate.coc.repositories.RoleRepository;
import uwl.senate.coc.repositories.SurveyRepository;
import uwl.senate.coc.repositories.SurveyResponseRepository;
import uwl.senate.coc.repositories.UserRepository;
import uwl.senate.coc.utils.EncryptionUtils;

@Service
public class MockService {
    @Autowired
    private CommitteeRepository committeeRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CriteriaRepository criteriaRepo;

    @Autowired
    private DutyRepository dutyRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private CollegeRepository collegeRepo;

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private GenderRepository genderRepo;
    
    @Autowired 
    private SurveyRepository surveyRepo;
    
    @Autowired
    private SurveyResponseRepository surveyResponseRepo;
    
    
    private List<String> ranks = Arrays.asList( "Associate Professor", "Assistant Professor", "Full Professor" );
    private List<String> committeeNames = IntStream.range(0, 50).mapToObj( i -> randomString() ).collect( Collectors.toList() );
    
    public static <T> T one( List<T> ts ) {
        int index = (int)(Math.random() * ts.size() );
        return ts.get(index);
    }

    public <T> List<T> choose( List<T> ts, int n ) {
        if( n > ts.size() ) return ts;
        List<T> copy = new ArrayList<T>( ts );
        Collections.shuffle(copy);
        return copy.subList(0,  n);
    }
    
    public int randomBetween( int low, int high ) { // inclusive
    	return (int)(Math.random() * ( high - low + 1 ) + low );
    }

    public String randomString() {
        int length = (int)(Math.random() * 10 + 5);

        StringBuffer sb = new StringBuffer();
        for( int i = 0; i < length; i++ ) {
            int offset = (int)(Math.random() * 26 );
            sb.append( (char)( 'a' + offset ));
        }
        return sb.toString();
    }

    public String year() {
        return String.valueOf( (int)( Math.random() * 20 + 2000 ) );
    }

    public List<Role> roles() {        
        List<Role> roles = Arrays
        		.asList("Admin", "Normal", "Nominate")
        		.stream()
        		.map( roleName -> new Role.Builder().role(roleName).build() )
        		.collect( Collectors.toList() );
        return roleRepo.saveAll( roles );
    }
    

    public List<College> colleges(List<String> years) {
    	List<College> allYears = new ArrayList<>();
    	years.forEach( year -> {
    		List<College> colleges = Arrays
    				.asList("CSH", "CASSH", "CBA", "SOE")
    				.stream()
    				.map( name -> new College.Builder().name(name).year(year).build() )
    				.collect( Collectors.toList() );
    		allYears.addAll( colleges );    		
         } );

    	return collegeRepo.saveAll( allYears );
    }

    public List<Department> departments(List<College> colleges ) {
    	List<Department> allYears = new ArrayList<>();
    	
    	HashMap<String, List<String>> collegeNameToListOfDepartmentNames = new HashMap<>();
    	collegeNameToListOfDepartmentNames.put( "CSH", Arrays.asList("CS", "MTH", "BIO", "ESS", "HEHP", "CHM", "CPE", "PHY" ));
       	collegeNameToListOfDepartmentNames.put( "CASSH", Arrays.asList("ENG", "EDS", "CHM", "MUS", "SOC", "PHY", "POL") );
       	collegeNameToListOfDepartmentNames.put( "CBA", Arrays.asList("FIN", "ECO", "MGT", "ACT") );
       	collegeNameToListOfDepartmentNames.put( "SOE", Arrays.asList("EDS") );

    	colleges.forEach( college -> {
    		List<Department> departments = 
    				collegeNameToListOfDepartmentNames.get( college.getName() )
    				.stream()
    				.filter( name -> Math.random() < .85 ) // FOR TESTING PURPOSES ENSURE THAT DEPTS ARE NOT THE SAME FOR ALL YEARS
    				.map( departmentName ->
                            new Department.Builder()
    						.name(departmentName)
    						.year( college.getYear() )
    						.college(college)
    						.build()
    				)
    				.collect( Collectors.toList() );

    		allYears.addAll( departments );
    	});
    	
    	return departmentRepo.saveAll( allYears );
    }

    public List<Gender> genders(List<String> years) {
    	List<Gender> allYears = new ArrayList<>();
    	years.forEach( year -> {
    		List<Gender> genders = Arrays
    				.asList("M", "F", "L", "G", "B", "T", "X", "Y", "Z" )
    				.stream()
    				.filter( name -> Math.random() < .85 )
    				.map( name -> new Gender.Builder().name(name).year(year).build() )
    				.collect(Collectors.toList() );
    		allYears.addAll( genders );
    	});
    	
    	return genderRepo.saveAll( allYears );
    }

    public String email() {
        return randomString() + "@uwlax.edu";
    }

    public static boolean firstUser = true;
    public User user(List<Role> roles, List<Gender> genders, List<Department> departments) {

    	final Department department = one( departments );
    	List<Role> userRoles = choose( roles, (int)(Math.random() * 3 + 1 ) );
    	if( firstUser ) {
    		// ensure that user.id == 1 has all roles
    		userRoles = roles;
    		firstUser = false;
    	}
    	final String year = department.getYear();
    	final Gender gender = one( genders.stream().filter( g -> g.getYear().equals( year ) ).collect( Collectors.toList() ) );
        final User user = new User.Builder()
                .first( randomString() )
                .last( randomString() )
                .rank( one( ranks ) )
                .college( department.getCollege() )
                .email( email() )
                .committees( new HashSet<>())
                .volunteeredCommittees(new HashSet<>())
                .gender( gender )
                .dept( department )
                .adminResponsibility(Math.random() < .6)
                .year( year )
                .tenured( Math.random() < .6 )
                .soe( Math.random() < .35 )
                .gradStatus( Math.random() < .35 )
                .chair(Math.random() < .15)
                .roles( userRoles )
                .build();
        
        return user;
    }


    // THIS FUNCTION DOESN"T SAVE IN REPO.  WE DEFER FOR FASTER BATCH SAVE LATER
    public List<Criteria> criteria(final Committee committee, List<College> colleges ) {
        // COLLEGES MUST MATCH COMMITTEE YEAR
    	colleges = colleges.stream().filter( c -> c.getYear().equals( committee.getYear() )).collect(Collectors.toList() );
    	
    	// COLLEGE CRITERIA
    	List<Criteria> criteria = colleges
    		.stream()
    		.map( c -> new Criteria.Builder().criteria( "(college " + c.getName() + " " + randomBetween(3, 5) + ")" ).committee( committee ).build() )
    		.collect(Collectors.toList() );
    	
    	// SOE AFFILIATION
    	criteria.add( new Criteria.Builder().criteria( "(soe " + randomBetween( 1, 2 ) + ")" ).committee( committee ).build()  );
    	
    	
    	// FILTER OUT SOME FOR TESTING PURPOSES
    	criteria = criteria.stream().filter( c -> Math.random() < .7 ).collect( Collectors.toList() );
    	
    	// SIZE IS REQUIRED
    	criteria.add( new Criteria.Builder().criteria( "(size " + randomBetween(9, 12) + ")").committee( committee ).build() );
        return criteria;
    }

    // THIS FUNCTION DOESN"T SAVE IN THE REPO.  WE DEFER FOR FASTER BATCH SAVE LATER
    public List<Duty> duties(Committee committee) {
        List<Duty> duties = IntStream
                .range(0,  (int)(Math.random() * 5 + 10 ) )
                .mapToObj( i -> new Duty.Builder()
                            .duty( randomString() )
                            .committee(committee)
                            .build() )
                .collect(Collectors.toList());

        return duties;
    }

    public List<Committee> committees( List<String> years, List<College> colleges, List<Department> departments, List<Gender> genders ) {
    	final List<Committee> allYears = new ArrayList<>();
    	years.forEach( year -> {
    		List<Committee> committees = committeeNames
    				.stream()
    				.filter( name -> Math.random() < .9 ) // FOR TESTING ... MAKE SURE THAT COMMITTEES AREN'T THE SAME EVERY YEAR
    				.map( name -> new Committee
    						.Builder()
    						.name( name )
    						.year( year )
    						.introduction( randomString() )
    						.criteria( new ArrayList<>() )
    						.duties( new ArrayList<>() )
    						.members( new HashSet<>() )
    						.build() )
    				.collect( Collectors.toList() );
    		allYears.addAll( committees );
    	});

    	// attach criteria
        List<Committee> committeesWithIds = committeeRepo.saveAll( allYears );
        List<Criteria> allCriteria = new ArrayList<>();
        committeesWithIds        		
        		.forEach( committee -> allCriteria.addAll( criteria( committee, colleges )  ) );
        
        criteriaRepo.saveAll( allCriteria );
        
        
        // attach duties
        List<Duty> allDuties = new ArrayList<>();
        committeesWithIds
        	.forEach( committee -> allDuties.addAll( duties( committee )));
        dutyRepo.saveAll( allDuties );
        
        return committeesWithIds;
    }

    public List<User> users(List<Role> roles, List<Gender> genders, List<Department> departments) {
        List<User> users = IntStream
                .range(0,  500 )
                .mapToObj( i -> user(roles, genders, departments) )
                .collect( Collectors.toList() );

        userRepo.saveAll( users ); // all users have been saved with ids
        

        // CREATE SURVEY DATA
        List<Survey> allSurveys = users
        		.stream()
        		.map( user -> new Survey.Builder()
        				.comment( randomString() )
        				.userId( user.getId() )
        				.year( user.getYear() )
        				.isEnabled(true)
        				.urlkey( EncryptionUtils.encrypt( user.getId() + ":" + user.getYear() + ":" + UUID.randomUUID() ) )
        				.build()

    				)
        		.collect( Collectors.toList() );
        
        surveyRepo.saveAll( allSurveys ); // all surveys have been saved with ids
        
        Map<String, List<Committee>> committeesByYear = new HashMap<>();
        
        Function<String, List<Committee>> getCommittees = year -> {
        	if( committeesByYear.containsKey( year ) )  {
        		return committeesByYear.get( year );
        	} else {
        		List<Committee> result = committeeRepo.findByYear(year, Committee.class );
        		committeesByYear.put( year, result );
        		return result;
        	}
        };
        
        List<SurveyResponse> allResponses = new ArrayList<>();
        allSurveys.forEach( s -> {
        	List<Committee> cids = getCommittees.apply( s.getYear() );
        	List<SurveyResponse> surveyResponses = new ArrayList<>();
        	cids.forEach( c -> {
        		SurveyResponse res = new SurveyResponse.Builder()
        			.committee( c )
        			.selected( Math.random() < .1 )
        			.survey( s )
        			.build();
        		allResponses.add( res );
        		surveyResponses.add( res );
        	} );
        	s.setResponses( surveyResponses );        	
        });
        
        surveyResponseRepo.saveAll( allResponses );
        surveyRepo.saveAll( allSurveys );        

        return users;
    }


    public List<String> years(int from, int to ) {
    	return IntStream.range(from, to).mapToObj( val -> String.valueOf( val ) ).collect(Collectors.toList() );
    }

    //	@PostConstruct
    public void makeData() {
        List<Role> roles = roles();
        // EVERYTHING ELSE IS year-based so construct years first
   		List<String> years = years( 1998, 2023 );
    	// YEAR-BASED DATA
        List<Gender> genders = genders( years );
        List<College> colleges = colleges( years );
        List<Department> departments = departments( colleges );
        List<Committee> committees = committees( years, colleges, departments, genders );

        // The USERS are aware of years via departments
        @SuppressWarnings("unused")
        List<User> users = users(roles, genders, departments);
        committees
                .stream()
                .forEach( c -> {
                    List<User> volunteers = choose( userRepo.findByYear(c.getYear()) , randomBetween( 0, 20 ) );
                    List<User> members = choose( volunteers, randomBetween(0, volunteers.size() ) );

                    c.setMembers( new HashSet<>( members ) );
                    c.setVolunteers( new HashSet<>( volunteers ) );
                });
        committeeRepo.saveAll( committees );
   }
}

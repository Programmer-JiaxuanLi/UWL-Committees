package uwl.senate.coc.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uwl.senate.coc.entities.*;
import uwl.senate.coc.projections.CommitteeId;
import uwl.senate.coc.projections.CommitteeSummary;
import uwl.senate.coc.projections.CommitteeWithUserSummaries;
import uwl.senate.coc.projections.CommitteeYear;
import uwl.senate.coc.projections.UserSummary;
import uwl.senate.coc.repositories.CommitteeRepository;
import uwl.senate.coc.repositories.CriteriaRepository;
import uwl.senate.coc.repositories.DutyRepository;
import uwl.senate.coc.repositories.UserRepository;
import uwl.senate.coc.utils.CriteriaPredicateFactory;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CommitteeService {
    @Autowired
    private CommitteeRepository committeeRepo;

    @Autowired
    private CriteriaRepository criteriaRepo;

    @Autowired
    private DutyRepository dutyRepo;

    @Autowired
    private UserRepository userRepo;
    
    
    public List<Criteria> unsatisfiedCriteria( Long cid ) {
    	Committee c = getCommitteeDetail( cid );

    	return c.getCriteria()
    			.stream()
    			.filter( crit -> !CriteriaPredicateFactory.build( crit ).test( c ) )
    	     	.collect( Collectors.toList() );
    }

	public Map<String, List<CommitteeWithUserSummaries>> getCommittees(String start, String end){
        List<CommitteeWithUserSummaries> committeesWithMembersList =  committeeRepo.findByYearBetween(start, end);
        Map<String, List<CommitteeWithUserSummaries>> committeeMap = new HashMap<String,List<CommitteeWithUserSummaries>>();
        committeesWithMembersList.stream().forEach(
                c->{
                    if (committeeMap.keySet().contains(c.getName()))
                    {
                        committeeMap.get(c.getName()).add(c);
                    } else {
                        List<CommitteeWithUserSummaries> list = new ArrayList<>();
                        list.add(c);
                        committeeMap.put(c.getName(),list);
                    }
                });
        return committeeMap;
    }

	public List<Committee> getCommitteesByYear( String year ) {
		return this.committeeRepo.findByYear( year, Committee.class );
	}
	
    public Committee getCommitteeDetail(Long id){
        return this.committeeRepo.findById(id).get();
    }

    public List<CommitteeSummary> getYearsCommittees(String startYear, String endYear){
        return committeeRepo.findByYearBetweenAndIdNotNull(startYear, endYear);
    }

    public List<String> getCommitteesYears(){
        return committeeRepo.findDistinctByYearNotNullOrderByYearAsc()
        		.stream()
        		.map( committee -> committee.getYear() )
        		.collect( Collectors.toList() );
    }

    public String createYear(String year) {
        List<CommitteeYear> years  = committeeRepo.findDistinctByYearNotNullOrderByYearAsc();        
        String lastYear = years.get(years.size() - 1).getYear();
        List<Committee> committees = committeeRepo.findByYear(lastYear, Committee.class);

        committees.forEach(
                committee -> {
                	// SAVE THE NEW COMMITTEE
                    Committee copy = new Committee.Builder()
                            .introduction( committee.getIntroduction() )
                            .name( committee.getName())
                            .year(year)
                            .build();                    
                    Committee finalCopy = committeeRepo.save(copy);

                    // ASSIGN DUTIES
                    List<Duty> newDuties = committee.getDuties()
                    	.stream()
                    	.map( duty -> new Duty.Builder()
                                		.committee( finalCopy )
                                		.duty( duty.getDuty() )
                                		.build() )
                    	.collect( Collectors.toList() );                    
                    finalCopy.setDuties(dutyRepo.saveAll(newDuties));
                    
                    // ASSIGN CRITERIA
                    List<Criteria> newCriteria = committee.getCriteria()
                    		.stream()
                    		.map( crit -> new Criteria.Builder()
                    				.committee( finalCopy )
                    				.criteria( crit.getCriteria() )
                    				.build() )
                    		.collect( Collectors.toList() );
                    		
                    finalCopy.setCriteria( criteriaRepo.saveAll( newCriteria ) );
                    committeeRepo.save( finalCopy );
                }
        );
        return year;
    }

    public List<String> getCommitteeYears(Long id){
        List<String> years = new ArrayList<String>();
        String name =  committeeRepo.findById(id).get().getName();
        committeeRepo.findDistinctByNameEquals(name).stream().forEach(
                committeesYearsOnly -> {
                    years.add(committeesYearsOnly.getYear());
                }
        );
        return years;
    }

    public CommitteeWithUserSummaries read(Long id){
        return committeeRepo.findByIdEqualsAndIdNotNull(id);
    }

    public List<UserSummary> getCommitteeMembers(Long id) {    	
    	CommitteeWithUserSummaries c = committeeRepo.findByIdEquals(id, CommitteeWithUserSummaries.class );
        return c.getMembers();
    }
    
    public List<CommitteeId> getCommitteeIds( String year ) {
    	return committeeRepo.findByYear( year, CommitteeId.class );
    }
    
    public List<UserSummary> getCommitteeVolunteersDetail(Long id) {        
        CommitteeWithUserSummaries c = committeeRepo.findByIdEquals(id, CommitteeWithUserSummaries.class );
        return c.getVolunteers();
    }

    public User assignCommitteeMember(Long id,Long userId){
        final User[] res = new User[1];
        userRepo.findById(Long.valueOf(userId)).map(
                user -> {
                    return committeeRepo.findById(Long.valueOf(id)).map(
                            committee -> {
                                user.getCommittees().add(committee);
                                user.getVolunteeredCommittees().remove(committee);
                                res[0] = userRepo.save(user);
                                return res[0];
                            }
                    );
                }
        );
        return res[0];
    }


    public User removeMember(Long id , Long memberId){
        final User[] res = new User[1];
        committeeRepo.findById(Long.valueOf(id)).map(
                committee -> {
                    return userRepo.findById(Long.valueOf(memberId)).map(
                            user -> {
                                res[0] = user;
                                user.removeCommittee(committee);
                                userRepo.save(user);
                                return user;
                            }
                    );
                }
        );
        return res[0];
    }

    public CommitteeSummary getCommitteeIdByYearAndName(String name, String year) {
        return committeeRepo.findByNameEqualsAndYearEquals(name, year, CommitteeSummary.class );
    }

    public void deleteCommitteeById(Long id) {
        this.committeeRepo.deleteById(id);
    }

    public void createCommittee(Committee c) {
    	// Save the basic entity
    	Committee copy = new Committee.Builder()
    			.year( c.getYear() )
    			.name( c.getName() )
    			.introduction( c.getIntroduction() )
    			.build();
    	
    	// Fill in the missing parts 
    	Committee committee = committeeRepo.save( copy );

        List<Criteria> criteria = c.getCriteria()
                .stream()
                .map( crit -> {
                    crit.setCommittee( committee );
                    return crit;
                })
                .collect( Collectors.toList() );
        criteriaRepo.saveAll( criteria );
        committee.setCriteria(criteria);

        List<Duty> duties = c.getDuties()
                .stream()
                .map( duty -> {
                    duty.setCommittee( committee );
                    return duty;
                })
                .collect( Collectors.toList() );
        dutyRepo.saveAll( duties );
        committee.setDuties( duties );
        committeeRepo.save(committee);
    }

	public List<CommitteeId> getCommitteeIdsByName(String name, String year) {
		return this.committeeRepo.findByNameEquals( name, CommitteeId.class );
	}
}

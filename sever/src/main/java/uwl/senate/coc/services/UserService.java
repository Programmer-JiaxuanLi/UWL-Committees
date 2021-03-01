package uwl.senate.coc.services;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uwl.senate.coc.entities.Survey;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.projections.CommitteeSummary;
import uwl.senate.coc.projections.SurveySummary;
import uwl.senate.coc.repositories.CommitteeRepository;
import uwl.senate.coc.repositories.RoleRepository;
import uwl.senate.coc.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private CommitteeRepository committeeRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private SurveyService surveyService;
    
    public Page<User> getUsers(Example<User> example, Pageable paging){
        return userRepo.findAll(example, paging );
    }

    public User getUser( Long id ) {
    	return userRepo.findById( id ).get();
    }

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> result = Arrays.stream( pds )
        		.map( pd -> pd.getName() )
        		.filter( name -> src.getPropertyValue( name ) == null )
        		.collect( Collectors.toSet() );

        return result.toArray( new String[] {} );
    }
    
    public User modifyUser(User user) {
    	User existingUser = userRepo.getOne( user.getId() );
    	
    	copyNonNullProperties( user, existingUser );

    	if( user.getDept() != null ) {
    		existingUser.setDept( user.getDept() );
    		existingUser.setCollege( user.getDept().getCollege() );
    	}

    	return userRepo.save( existingUser );
    }

    public void deleteUser(Long id){
        userRepo.deleteById(Long.valueOf(id));
    }

    public User createUser(User user){
        roleRepo.saveAll(user.getRoles());
        return userRepo.save(user);
    }

    public List<CommitteeSummary> getUserCommittees(User user) {
        return committeeRepo.findByMembers(user);
    }

    public List<CommitteeSummary> getUserSurveysCommittees(User v){
        return committeeRepo.findByVolunteers(v);
    }
    
    public <T> T getSurvey( Long userId, Class<T> clazz ) {
    	return this.surveyService.getByUserId(userId, clazz);
    }

    public Survey createSurvey( User user ) {
    	return this.surveyService.create(user);
    }

    public List<String> getUserYears(String email){
        ArrayList<String> list =new ArrayList<String>();
        userRepo.findDistinctByEmailOrderByYearAsc(email).forEach(
                userYearsOnly -> {
                    list.add(userYearsOnly.getYear());
                }
        );
        return list;
    }

    public User getUserById(Long Id){
       return userRepo.findById(Id).get();
    }

    public User getUserByEmailAndYear(String email, String year){
        return userRepo.findByEmailEqualsAndYearEquals(email, year);
    }
}

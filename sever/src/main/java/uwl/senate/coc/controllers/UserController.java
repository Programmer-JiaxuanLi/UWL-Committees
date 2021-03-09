package uwl.senate.coc.controllers;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uwl.senate.coc.entities.*;
import uwl.senate.coc.projections.CommitteeSummary;
import uwl.senate.coc.projections.SurveySummary;
import uwl.senate.coc.repositories.UserRepository;
import uwl.senate.coc.services.SurveyService;
import uwl.senate.coc.services.UserService;

@RestController
@RequestMapping( "/api/v1/users" )
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserRepository u;
    
    @RequestMapping( method=RequestMethod.GET )
    public Page<User> getUsers(
            @RequestParam(defaultValue="0") Integer pageNo,
            @RequestParam(defaultValue="10") Integer pageSize,
            @RequestParam(defaultValue="last") String sortBy,
            @Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(name="year", required=true) String year,
            @RequestParam(required=false) String first, 
            @RequestParam(required=false) String last,
            @Pattern( regexp = "Full|Associate|Assistant", message = "the rank format is wrong") @RequestParam(name="rank", required=false) String rank,
            @RequestParam(name="college", required=false) String college,
            @RequestParam(required=false) Boolean tenured,
            @RequestParam(required=false) Boolean soe,
            @RequestParam(required=false) Boolean admin,
            @RequestParam(required=false) Boolean chair,
            @RequestParam(name="dept", required=false) String department,
            @RequestParam(name="gender", required=false) String gender) {
        rank = rank != null ? rank + " Professor" : null;

        User.Builder userBuilder = new User.Builder();
        userBuilder.year(year)
                .first(first)
                .last(last)
                .adminResponsibility( admin )
                .rank( rank )
                .tenured( tenured )
                .soe( soe )
                .chair( chair )
                .build();
        if (college != null) {
            userBuilder.college(new College.Builder().name(college).build());
        }
        if (department != null) {
            userBuilder.dept(new Department.Builder().name(department).build());
        }
        if (gender != null) {
            userBuilder.gender(new Gender.Builder().name(gender).build());
        }
        User user = userBuilder.build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("first", m -> m.contains())
                .withMatcher("last", m -> m.contains());

        Example<User> example = Example.of(user, matcher);
        Pageable paging = PageRequest.of( pageNo, pageSize, Sort.by(sortBy));
        return userService.getUsers(example, paging );
    }

    @RequestMapping( value="/{uid}", method=RequestMethod.GET)
    public User getUser( @PathVariable Long uid ) {
    	return userService.getUser( uid );
    }
    
    // I HAVEN'T TESTED THIS ENDPOINT
    @RequestMapping( value="/{uid}", method=RequestMethod.PUT )
    public User modifyUser( @PathVariable Long uid, @RequestBody(required=true) User user) {  
    	if( uid == null || !uid.equals( user.getId() ) ) throw new IllegalArgumentException("Path id and user.id are not the same");
        
        return userService.modifyUser(user);
    }

    @RequestMapping( value="/{uid}", method=RequestMethod.DELETE )
    public void deleteUser( @PathVariable Long uid ) {
    	// DON'T need YEAR since a user's primary key is their email
        userService.deleteUser( uid );
    }

    @RequestMapping( value="", method=RequestMethod.POST )
    public User createUser( @RequestBody(required=true) User user ) {
        return userService.createUser(user);
    }
    

    @RequestMapping( value="/{uid}/committees", method=RequestMethod.GET )
    public List<CommitteeSummary> getUserCommittees( @PathVariable Long uid ) {
        return  userService.getUserCommittees( userService.getUser( uid ) );
    }

    @RequestMapping( value="/{id}/enlistings/committees", method=RequestMethod.GET )
    public List<CommitteeSummary> getUserSurveysCommittees(@Pattern( regexp = "^[0-9]*$", message = "the UserID format is wrong") @PathVariable String id) {
        //Get all the survey that user have been volunteered
        User v =new User();
        v.setId(Long.valueOf(id));
        return userService.getUserSurveysCommittees(v);
    }


    // This method is poorly constructed
    @RequestMapping( value="/email/{email}/years", method=RequestMethod.GET )
    public List<String> getUserYears(@PathVariable String email) {
        //get all years of users which have same email
        return userService.getUserYears(email);
    }
    
    
    //////////////////////////////////////////////////////////////////////
    /// SURVEYS 
    //////////////////////////////////////////////////////////////////////
    @RequestMapping( value="/{uid}/survey", method=RequestMethod.GET)
    public SurveySummary getSurvey( @PathVariable Long uid ) {
    	return surveyService.getByUserId( uid, SurveySummary.class );
    }

    @RequestMapping( value="/{uid}/survey/responses/{rid}", method=RequestMethod.PUT)
    public SurveyResponse updateSurveyResponse( 
    		@PathVariable Long uid, 
    		@PathVariable Long rid, 
    		@RequestBody SurveyResponse surveyResponse ) {
    	/// NEED SOME VALIDATION HERE..........    	    	
    	return surveyService.updateResponse( surveyResponse );
    }

    @RequestMapping( value="/{uid}/survey/comment", method=RequestMethod.PUT )
    public Survey updateComment(@RequestBody(required=true) Survey surveyWithCommentAndIdOnly,
                              @PathVariable Long uid) {
    	return surveyService.updateComment( surveyWithCommentAndIdOnly );
    }
    
}
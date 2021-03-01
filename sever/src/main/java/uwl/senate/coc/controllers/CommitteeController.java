package uwl.senate.coc.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uwl.senate.coc.entities.Committee;
import uwl.senate.coc.entities.Criteria;
import uwl.senate.coc.entities.User;
import uwl.senate.coc.projections.CommitteeId;
import uwl.senate.coc.projections.CommitteeSummary;
import uwl.senate.coc.projections.CommitteeWithUserSummaries;
import uwl.senate.coc.projections.UserSummary;
import uwl.senate.coc.services.CommitteeService;
@RestController
@RequestMapping( "/committees" )
@Validated
public class CommitteeController {

    @Autowired
    private CommitteeService committeeService;

    @RequestMapping( method=RequestMethod.GET )
    public List<CommitteeSummary> getYearsCommittees(
            @Pattern( regexp = "\\b\\d{4}\\b", message = "the start year format is wrong") @RequestParam(defaultValue = "2000") String startYear,
            @Pattern(regexp = "\\b\\d{4}\\b", message = "the end year format is wrong") @RequestParam(defaultValue = "2050")String endYear) {
        return committeeService.getYearsCommittees(startYear, endYear);
    }
    
    @RequestMapping( value="/{id}", method=RequestMethod.GET )
    public CommitteeWithUserSummaries getCommittee( @PathVariable Long id ) {
        return committeeService.read( id );
    }
    
    @RequestMapping( value="/{id}", method=RequestMethod.DELETE )
    public ResponseEntity<String> delete( @PathVariable Long id ) {
        this.committeeService.deleteCommitteeById(id );
    	return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }

    @RequestMapping( method=RequestMethod.POST)
    public ResponseEntity<String> createCommitte(@RequestBody(required=true) Committee committee) {
        this.committeeService.createCommittee(committee);
        return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }

    @RequestMapping( value="/hashedCommittees", method=RequestMethod.GET )
    public Map<String,List<CommitteeWithUserSummaries>> getYearsCommitteesWithGroup(@Pattern( regexp = "\\b\\d{4}\\b", message = "the start year format is wrong") @RequestParam(name="startYear", required=true) String startYear, @Pattern(regexp = "\\b\\d{4}\\b", message = "the end year format is wrong") @RequestParam(name="endYear",required = true)String endYear) {
        return committeeService.getCommittees(startYear, endYear);
    }

    @RequestMapping( value="/{name}/ids", method=RequestMethod.GET )
    public List<CommitteeId> getCommitteeIdsByName(@PathVariable String name, @PathVariable String year) {
        return committeeService.getCommitteeIdsByName(name, year);
    }

    @RequestMapping( value="/{name}/years/{year}", method=RequestMethod.GET )
    public CommitteeSummary getCommitteeIdByYearAndName(@PathVariable String name, @PathVariable String year) {
        return committeeService.getCommitteeIdByYearAndName(name, year);
    }

    @RequestMapping( value="/{id}/years", method=RequestMethod.GET )
    public List<String> getCommitteeYears( @PathVariable Long id ) {
        return committeeService.getCommitteeYears( id );
    }

    @RequestMapping( value="/{id}/members", method=RequestMethod.GET )
    public List<UserSummary> getCommitteeMembers( @PathVariable Long id ) {
        return committeeService.getCommitteeMembers(id);
    }

    @RequestMapping( value="/{id}/volunteers", method=RequestMethod.GET )
    public List<UserSummary> getCommitteeVolunteers( @PathVariable Long id ){
        return committeeService.getCommitteeVolunteersDetail( id );
    }

    @RequestMapping( value="/{cid}/members/{uid}", method=RequestMethod.PUT )
    public User assignCommitteeMember(
    		@PathVariable Long cid,
    		@PathVariable Long uid ) {
        return committeeService.assignCommitteeMember( cid, uid );
    }

    @RequestMapping( value="/{cid}/members/{uid}", method=RequestMethod.DELETE )
    public User removeMember( 
    		@PathVariable Long cid,
    		@PathVariable Long uid ) {
        return committeeService.removeMember( cid, uid );
    }

    @RequestMapping( value="/{id}/unsatisfiedCriteria/size", method=RequestMethod.GET)
    public Integer unsatisfiedCriteriaSize( @PathVariable Long id ) {
        return this.committeeService.unsatisfiedCriteria(id).size();
    }

    @RequestMapping( value="/{id}/unsatisfiedCriteria", method=RequestMethod.GET)
    public List<Criteria> unsatisfiedCriteria( @PathVariable Long id ) {
        return this.committeeService.unsatisfiedCriteria(id);
    }
}


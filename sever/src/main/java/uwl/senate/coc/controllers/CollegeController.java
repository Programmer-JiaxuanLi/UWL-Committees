package uwl.senate.coc.controllers;

import java.util.List;

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

import uwl.senate.coc.entities.College;
import uwl.senate.coc.services.CollegeService;

@RestController
@RequestMapping( "/settings/colleges" )
@Validated
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @RequestMapping( method= RequestMethod.GET)
    public List<College> getByYear(@Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(required=true) String year) {
        return this.collegeService.findByYear(year);
    }


    @RequestMapping( value="/years", method= RequestMethod.GET)
    public List<College> getByYears(@Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(required=true) String startYear,
                                    @Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(required=true) String endYear) {
        return this.collegeService.findByYears(startYear, endYear);
    }

    @RequestMapping( method= RequestMethod.POST)
    public College create( @RequestBody(required=true) College college) {
        return this.collegeService.create( college );
    }
    
    
    @RequestMapping( value="/{id}", method= RequestMethod.GET)
    public College read(@PathVariable Long id ) {
    	return this.collegeService.read( id );
    }

    @RequestMapping( value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id ) {
    	this.collegeService.delete( id );
    	return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }

    @RequestMapping( value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<String> update(
    		@PathVariable Long id,
    		@RequestBody(required=true) College c ) {
        this.collegeService.update( id, c );
        return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }
}

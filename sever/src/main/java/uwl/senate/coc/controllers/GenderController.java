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

import uwl.senate.coc.entities.Gender;
import uwl.senate.coc.services.GenderService;

@RestController
@RequestMapping( "/api/v1/settings/genders" )
@Validated
public class GenderController {

    @Autowired
    private GenderService genderService;

    @RequestMapping( method= RequestMethod.GET)
    public List<Gender> getByYear(@Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(required=true) String year) {
        return this.genderService.getByYear(year);
    }
    
    @RequestMapping( value="/{id}", method=RequestMethod.GET)
    public Gender get( @PathVariable Long id ) {
    	return this.genderService.read( id );
    }
    
    @RequestMapping( method= RequestMethod.POST)
    public Gender create( @RequestBody( required=true ) Gender gender) {
        return this.genderService.create( gender );
    }
    
    @RequestMapping( value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id ) {
    	this.genderService.delete( id );
    	return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }

    @RequestMapping( value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<String> update(
    		@PathVariable Long id,
    		@RequestBody( required=true ) Gender gender ) {
        this.genderService.update( id, gender );
        return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }    
}

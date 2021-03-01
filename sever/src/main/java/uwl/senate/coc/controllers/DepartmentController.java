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

import uwl.senate.coc.entities.Department;
import uwl.senate.coc.services.DepartmentService;

@RestController
@RequestMapping( "/settings/departments" )
@Validated
public class DepartmentController {

    //DepartmentRepository deptRepo;
    @Autowired
    private DepartmentService deptService;
	
    @RequestMapping( method= RequestMethod.GET)
    public List<Department> getDepartments(
    	 @Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong") @RequestParam(required=true) String year) {
        return this.deptService.getByYear(year);
    }
    
    @RequestMapping( method= RequestMethod.POST)
    public Department createDepartment( @RequestBody( required=true ) Department department) {
        return this.deptService.create( department );
    }
    
    @RequestMapping( value="/{id}", method= RequestMethod.GET)
    public Department read( @PathVariable Long id ) {
        return this.deptService.read( id );
    }
    
    @RequestMapping( value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id ) {
    	this.deptService.delete( id );
    	return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }

    @RequestMapping( value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<String> updateDepartment(
    		@PathVariable Long id, 
    		@RequestBody( required=true ) Department department ) {
    	this.deptService.update( id, department );
        return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
    }    
}

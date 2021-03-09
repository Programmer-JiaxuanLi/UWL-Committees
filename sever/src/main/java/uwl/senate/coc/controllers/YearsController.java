package uwl.senate.coc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import uwl.senate.coc.services.CommitteeService;

import java.util.List;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping( "/api/v1/settings/years" )
@Validated
public class YearsController {

    @Autowired
    private CommitteeService committeeService;

    @RequestMapping( value="/{year}", method=RequestMethod.POST )
    public String create(@Pattern( regexp = "\\b\\d{4}\\b", message = "the year format is wrong")  @PathVariable String year) {
        committeeService.createYear(year);
        return year;
    }

    @RequestMapping( method=RequestMethod.GET ) 
    public List<String> read() {
    	return this.committeeService.getCommitteesYears();
    }
}

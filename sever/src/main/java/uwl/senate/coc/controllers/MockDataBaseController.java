package uwl.senate.coc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uwl.senate.coc.services.MockService;


@RestController
public class MockDataBaseController {
    @Autowired
    MockService mockService;

    @RequestMapping( value="/mockData", method= RequestMethod.GET )
    public void mockData(@RequestParam(name="committeeNum", required=false) Integer committeeNum, @RequestParam(name="userNum", required=false) Integer userNum) {
        //Search users who meet the conditions
        mockService.makeData();
    }
}








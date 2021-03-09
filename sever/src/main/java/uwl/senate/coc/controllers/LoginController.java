package uwl.senate.coc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import uwl.senate.coc.entities.*;
import uwl.senate.coc.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping( "/api/v1" )
@Validated
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @RequestMapping( value="/login", method= RequestMethod.POST )
    public uwl.senate.coc.entities.User login(@RequestBody(required=true) User reauestUser) {
        uwl.senate.coc.entities.User user;
        
        
        List<User> users = userRepo.findByEmailEquals(reauestUser.getEmail());
        if( users.size() >= 1 ) {
        	user = users.get(users.size() - 1);
        } else {
        	user = null;
        }
        
        return user;
    }
}


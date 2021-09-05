package com.webapp.demo.controller;

import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    UserRepo userRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/user/create")
    public ResponseModelParameter<User> createUser(@RequestBody User user){
        User newUser = userRepo.save(user);
        return new ResponseModelParameter<User>(true, "user created", newUser);
    }

}

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

    @CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
    @PostMapping("/user/create")
    public ResponseModelParameter<User> createUser(@RequestBody User user){
        User user1 = userRepo.findByEmail(user.getEmail());
        if(user1.getId() != 0){
            return new ResponseModelParameter<User>(false, "User already present. Login", null);
        }
        User newUser = userRepo.save(user);
        return new ResponseModelParameter<User>(true, "Signup Successfull", newUser);
    }

}

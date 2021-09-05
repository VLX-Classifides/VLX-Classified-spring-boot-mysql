package com.webapp.demo.controller;

import com.webapp.demo.model.ResponseModel;
import com.webapp.demo.model.ResponseModelList;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getUsers")
    public ResponseModelList<User> getUsers(){
        List<User> users =  userRepo.findAll();
        return new ResponseModelList<User>(true, "Users List", users);
    }
}

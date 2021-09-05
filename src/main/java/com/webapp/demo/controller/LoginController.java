package com.webapp.demo.controller;

import com.webapp.demo.model.LoginModel;
import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;
import com.webapp.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    LoginService loginService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user/login")
    public ResponseModelParameter<User> checkUser(@RequestBody LoginModel loginModel){
        return loginService.checkUser(loginModel);
    }
}

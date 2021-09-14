package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.Login;
import com.webapp.demo.model.ResponseModelList;
import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class UserController {
	
	@Autowired
	UserRepo userrepo;
	

	// controller for users login and signup
	@PostMapping("/user/signup")
	public ResponseModelParameter<User> signup(@RequestBody User user) {
		
		User newUser =userrepo.save(user);
		return new ResponseModelParameter<User>(true, "user created", newUser);
	}

	@GetMapping("/users")
	public ResponseModelList<User> getAllUser() {
		List<User> users=userrepo.findAll();
		return new ResponseModelList<User>(true,"user list", users);
	}

	@PostMapping("/user/login1")
	public ResponseModelParameter<User> login(@RequestBody Login login) {
		User user = userrepo.findByEmail(login.getEmail());
		if(user==null)
			return new ResponseModelParameter<>(false, "user not found", null);
		String pass=user.getPassword();
		if(pass.equals(login.getPassword()))
		{
			return new ResponseModelParameter<User>(true, "user found", user);
		}
		else {
			return new ResponseModelParameter<>(false, "Password Incorrect", null);
		}
	}
}

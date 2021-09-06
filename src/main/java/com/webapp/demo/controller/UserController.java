package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class UserController {
	
	@Autowired
	UserRepo userrepo;
	

	// controller for users login and signup
	@PostMapping("/signup")
	public User signup(@RequestBody User user) {
		
		return userrepo.save(user);
	}

	@GetMapping("/users")
	public List<User> getAllUser() {
		return userrepo.findAll();
	}

	@PostMapping("/login")
	public User login(@RequestBody User userLogin) {
		User user = userrepo.findByEmail(userLogin.getEmail());
		String pass=user.getPassword();
		if(pass.equals(userLogin.getPassword()))
			return user;
		return null;
	}
}

package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.Login;
import com.webapp.demo.model.ResponseModelList;
import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.UserRepo;

@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
@RestController
public class UserController {
	
	@Autowired
	UserRepo userrepo;


	@GetMapping("/users")
	public ResponseModelList<User> getAllUser() {
		List<User> users=userrepo.getUsers();
		return new ResponseModelList<User>(true,"user list", users);
	}
	
	// get user details by id
	@GetMapping("/user/{id}")
	public ResponseModelParameter<User> getIndividualUser(@PathVariable("id") int id)
	{
		User user=userrepo.findById(id).orElse(null);
		return new ResponseModelParameter<User>(true, "individual user data", user);
	}
}

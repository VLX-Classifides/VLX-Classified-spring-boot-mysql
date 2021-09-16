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

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class UserController {
	
	@Autowired
	UserRepo userrepo;
	

	// controller for users login and signup
	@PostMapping("/user/signup")
	public ResponseModelParameter<User> signup(@RequestBody User user) {
		User user1 = userrepo.findByEmail(user.getEmail());
        if(user1.getId() != 0){
            return new ResponseModelParameter<User>(false, "User already present. Login", null);
        }
        User newUser = userrepo.save(user);
        return new ResponseModelParameter<User>(true, "Signup Successfull", newUser);
	}

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
	@PostMapping("/user/entry")
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

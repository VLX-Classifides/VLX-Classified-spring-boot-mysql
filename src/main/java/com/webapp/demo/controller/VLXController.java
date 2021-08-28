package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.Products;
import com.webapp.demo.model.User;
import com.webapp.demo.repo.ProductsRepo;
import com.webapp.demo.repo.UserRepo;

@RestController
public class VLXController {
	@Autowired
	ProductsRepo productsrepo;

	@Autowired
	UserRepo userrepo;

	// controller for products
	@GetMapping("/")
	public String welcome() {
		return "Welcome to ecommerce";
	}

	@GetMapping("/products")
	@ResponseBody
	public List<Products> getProducts() {
		return productsrepo.findAll();
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") int id) {
		Products product = productsrepo.findById(id).orElse(null);
		return ResponseEntity.ok(product);
	}

	// controller for users

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

package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webapp.demo.model.Products;
import com.webapp.demo.repo.ProductsRepo;

@RestController
public class VLXController {
	@Autowired
	ProductsRepo productsrepo;
	
	@GetMapping("/")
	public String welcome()
	{
		return "Welcome to ecommerce";
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/products")
	@ResponseBody
	public List<Products> getProducts()
	{
		return productsrepo.findAll();
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") long id)
	{
		Products product= productsrepo.findById(id).orElse(null);
		return ResponseEntity.ok(product);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/api/product")
	public ResponseEntity<Products> createProduct(@RequestBody Products product){
		productsrepo.save(product);
		return ResponseEntity.ok(product);
	}
}

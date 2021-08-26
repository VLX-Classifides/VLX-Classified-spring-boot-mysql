package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/products")
	@ResponseBody
	public List<Products> getProducts()
	{
		return productsrepo.findAll();
	}
	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") long id)
	{
		Products product= productsrepo.findById(id).orElse(null);
		return ResponseEntity.ok(product);
	}
}

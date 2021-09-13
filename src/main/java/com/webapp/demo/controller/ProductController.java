package com.webapp.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.Products;
import com.webapp.demo.model.ResponseModelList;
import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.repo.ProductsRepo;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class ProductController {
	@Autowired
	ProductsRepo productsrepo;
	
	// controller for products
	@GetMapping("/")
	public String welcome() {
		return "Welcome to ecommerce";
	}

	@GetMapping("/products")
	@ResponseBody
	public ResponseModelList<Products> getProducts() {
		List<Products> products= productsrepo.findAll();
		return new ResponseModelList<Products>(true,"all products",products);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable("id") int id) {
		Products product = productsrepo.findById(id).orElse(null);
		return ResponseEntity.ok(product);
	}
	@PostMapping("/addProduct")
	public ResponseModelParameter<Products> createProduct(@RequestBody Products product)
	{
		Products newProduct=productsrepo.save(product);
		return new ResponseModelParameter<Products>(true,"product created",newProduct);
		
	}
	
}

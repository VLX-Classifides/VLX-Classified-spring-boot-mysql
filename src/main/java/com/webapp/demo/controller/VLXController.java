package com.webapp.demo.controller;

import java.io.IOException;
import java.util.List;

import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.model.User;
import com.webapp.demo.service.imageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.webapp.demo.model.Products;
import com.webapp.demo.repo.ProductsRepo;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class VLXController {
	@Autowired
	ProductsRepo productsrepo;

	@Autowired
	imageService imageS;


	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/products")
	public List<Products> getProducts()
	{
		return productsrepo.findAll();
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/product/{id}")
	public ResponseModelParameter<Products> getProductById(@PathVariable("id") int id)
	{
		Products product= productsrepo.findById(id).orElse(null);
		return new ResponseModelParameter<Products>(true, "product body", product);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping("/api/product")
	public ResponseModelParameter<Products> createProduct(@RequestBody Products product) throws IOException {

		Products newProduct = productsrepo.save(product);

		return new ResponseModelParameter<Products>(true, "Product Created", newProduct);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping(value="/api/product/{id}/assign-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseModelParameter<Products> assignImageToProduct(@PathVariable String id, @RequestParam MultipartFile file) throws IOException {

		Products product = imageS.saveImageFile(Long.valueOf(id), file);
		return new ResponseModelParameter<Products>(true, "Image added", product);
	}
}

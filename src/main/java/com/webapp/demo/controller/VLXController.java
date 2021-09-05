package com.webapp.demo.controller;

import java.io.IOException;
import java.util.List;

import com.webapp.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<MultipartFile> createProduct(@RequestBody MultipartFile multipartfile) throws IOException {

		String fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
		//product.setPhotos(fileName);

		//Products newProduct = productsrepo.save(product);
		String uploadDir = "product-photos/" + "1";
		FileUploadUtil.saveFile(uploadDir, fileName, multipartfile);

		return ResponseEntity.ok(multipartfile);
	}
}

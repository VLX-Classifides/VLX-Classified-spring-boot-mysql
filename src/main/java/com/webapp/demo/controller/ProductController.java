package com.webapp.demo.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webapp.demo.model.Products;
import com.webapp.demo.model.ResponseModelList;
import com.webapp.demo.model.ResponseModelParameter;
import com.webapp.demo.repo.ProductsRepo;
import com.webapp.demo.service.ImageService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class ProductController {
	@Autowired
	ProductsRepo productsrepo;
	
	@Autowired
	ImageService imageS;
	
	// controller for products
	@GetMapping("/")
	public String welcome() {
		return "Welcome to ecommerce";
	}

	@GetMapping("/api/approvedProducts")
	@ResponseBody
	public ResponseModelList<Products> getApprovedProducts() {
		List<Products> products= productsrepo.approvedProducts();
		return new ResponseModelList<Products>(true,"all products",products);
	}

	//fetch selective products
	@GetMapping("/api/product/{id}")
	public ResponseModelParameter<Products> getProductById(@PathVariable("id") int id) {
		Products product = productsrepo.findById(id).orElse(null);
		return new ResponseModelParameter<Products>(true,"single created",product);
	}
	
	@GetMapping("/api/products/{category}")
	public ResponseModelList<Products> getProductByCategory(@PathVariable("category") String category){
		List<Products> products = productsrepo.findByCategory(category);
		List<Products> approved=new ArrayList<>();
		for(Products product:products)
		{
			if(product.getStatus().equals("approved"))
				approved.add(product);
		}
		return new ResponseModelList<Products>(true, "Products by category", approved);
	}
	
	@GetMapping("/api/products-by-user/{userid}")
	public ResponseModelList<Products> getProductsByUser(@PathVariable("userid") int userid){
		List<Products> products = productsrepo.findByCreatedby(userid);
		return new ResponseModelList<Products>(true, "Products by User", products);
	}
	
	// get pending product for particular seller
	@GetMapping("/api/pendingProducts/{sellerId}")
	@ResponseBody
	public ResponseModelList<Products> getPendingProductsBySellerId(@PathVariable("sellerId") int sellerId) {
		List<Products> products= productsrepo.pendingProducts();
		List<Products> pending=new ArrayList<>();
		for(Products product :products)
		{
			if(product.getCreatedby()==sellerId)
				pending.add(product);
		}
		return new ResponseModelList<Products>(true,"pending products",pending);
	}
	
	// product creation
	
	@PostMapping("/api/product")
	public ResponseModelParameter<Products> createProduct(@RequestBody Products product) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		product.setCreateddate(dateFormat.format(date));
		Products newProduct = productsrepo.save(product);

		return new ResponseModelParameter<Products>(true, "Product Created", newProduct);
	}
	
	@PostMapping(value="/api/product/{id}/assign-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseModelParameter<Products> assignImageToProduct(@PathVariable String id, @RequestParam MultipartFile file) throws IOException {

		Products product = imageS.saveImageFile(Long.valueOf(id), file);
		return new ResponseModelParameter<Products>(true, "Image added", product);
	}
	
	// get all products which are not approved
	
	@GetMapping("/pendingProducts")
	@ResponseBody
	public ResponseModelList<Products> getPendingProducts() {
		List<Products> products= productsrepo.pendingProducts();
		return new ResponseModelList<Products>(true,"pending products",products);
	}
	
	// approve one product
	@PutMapping("/approveProduct/{id}")
	public ResponseModelParameter<Products> approveProduct(@PathVariable("id") int id)
	{
		Products product=productsrepo.findById(id).orElse(null);
		product.setStatus("approved");
		productsrepo.save(product);
		return new ResponseModelParameter<Products>(true, "product approved", product);
	}
	
	// delete product by id
	@DeleteMapping("/rejectProduct/{id}")
	public ResponseModelParameter<Products> deleteProduct(@PathVariable("id") int id)
	{
		Products product=productsrepo.findById(id).orElse(null);
		productsrepo.delete(product);
		return new ResponseModelParameter<Products>(true, "product deleted", product);
	}
}

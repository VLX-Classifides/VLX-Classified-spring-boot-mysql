package com.webapp.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.webapp.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.webapp.demo.repo.ImageRepo;
import com.webapp.demo.repo.ProductsRepo;
import com.webapp.demo.service.imageService;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class VLXController {
	@Autowired
	ProductsRepo productsrepo;

	@Autowired
	imageService imageS;

	@Autowired
	ImageRepo imageRepo;


	//FETCH ALL PRODUCTS
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/products")
	public List<Products> getProducts()
	{
		return productsrepo.findAll();
	}



	//FETCH SELECTIVE PRODUCTS
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/product/{id}")
	public ResponseModelParameter<ProductDetail> getProductById(@PathVariable("id") int id) {

		Products product= productsrepo.findById(id).orElse(null);
		if(product.getId() == 0){
			return new ResponseModelParameter<ProductDetail>(false, "product not found", null);
		}
		List<Image> images = imageRepo.findAll();
		List<Image> selectedImages = new ArrayList<>();
		for(Image image : images){
			if(image.getProductId() == id){
				selectedImages.add(image);
			}
		}

		ProductDetail productDetail = new ProductDetail(product, selectedImages);
		return new ResponseModelParameter<ProductDetail>(true, "product body", productDetail);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/products/{category}")
	public ResponseModelList<Products> getProductByCategory(@PathVariable("category") String category){
		List<Products> products= productsrepo.approvedProducts();
		List<Products> selectedProducts = new ArrayList<>();
		for(Products product :products){
			if(product.getCategory().equals(category))
				selectedProducts.add(product);
		}
		return new ResponseModelList<Products>(true,"approved products by category",selectedProducts);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/products-by-user/{userid}")
	public ResponseModelList<Products> getProductsByUser(@PathVariable("userid") int userid){
		List<Products> products= productsrepo.approvedProducts();
		List<Products> selectedProducts=new ArrayList<>();
		for(Products product :products){
			if(product.getCreatedby()==userid)
				selectedProducts.add(product);
		}
		return new ResponseModelList<Products>(true,"approved products for seller",selectedProducts);
	}



	//PRODUCT CREATION
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping("/api/product")
	public ResponseModelParameter<Products> createProduct(@RequestBody Products product) throws IOException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		product.setCreateddate(dateFormat.format(date));
		product.setStatus("pending");
		if(product.isDonation()==true)
			product.setPrice(0);
		Products newProduct = productsrepo.save(product);

		return new ResponseModelParameter<Products>(true, "Product Created", newProduct);
	}

	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping(value="/api/product/{id}/assign-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseModelParameter<Products> assignImageToProduct(@PathVariable int id, @RequestParam MultipartFile[] files) throws IOException {
		//System.out.println(file);
		int i = 0;
		Arrays.asList(files).stream().forEach(file -> {
			imageS.saveImageFile(id, file);
		});
		Products product = productsrepo.getById(id);
		return new ResponseModelParameter<Products>(true, "Image added", product);
	}
}

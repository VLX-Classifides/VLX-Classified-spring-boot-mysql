package com.webapp.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.demo.model.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer>, ProductRepoCustom{
	
}

package com.webapp.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.demo.model.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Integer>{
	
	// products which are not approved
	@Query(value = "select * from products p where p.status=\"pending\"", nativeQuery = true)
	List<Products> pendingProducts();
	
	// products approved
	@Query(value = "select * from products p where p.status=\"approved\"", nativeQuery = true)
	List<Products> approvedProducts();
	
	//@Query(value = "select * from products p where p.status=\"approved\" and p.category='$category'", nativeQuery = true)
	List<Products> findByCategory(String category);
    List<Products> findByCreatedby(int id);
}
package com.webapp.demo.repo;

import com.webapp.demo.model.Products;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductRepoImpl implements ProductsRepo{

    @Autowired
    ProductsRepo productsRepo;

    @Override
    public List<Products> findByCategory(String category){
        List<Products> productsList = productsRepo.findAll();
        List<Products> selectedProducts = new ArrayList<>();
        for(Products item : productsList){
            if(item.getCategory().equals(category)){
                selectedProducts.add(item);
            }
        }
        return selectedProducts;
}
}
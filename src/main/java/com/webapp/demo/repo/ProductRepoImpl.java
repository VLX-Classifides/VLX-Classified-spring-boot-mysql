package com.webapp.demo.repo;

import com.webapp.demo.model.Products;
import com.webapp.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductRepoImpl implements ProductRepoCustom{

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

    @Override
    public List<Products> findByCreatedby(int id){
        List<Products> productsList = productsRepo.findAll();
        List<Products> selectedProducts = new ArrayList<>();
        for(Products item : productsList){
            if(item.getCreatedby() == id){
                selectedProducts.add(item);
            }
        }
        return selectedProducts;
    }
}

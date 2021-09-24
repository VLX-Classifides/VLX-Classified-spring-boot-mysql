package com.webapp.demo.repo;

import com.webapp.demo.model.Image;
import com.webapp.demo.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer> {

//    @Query(value = "select * from image i where i.productId='$id'", nativeQuery = true)
//    List<Image> findByProductId(int id);

}

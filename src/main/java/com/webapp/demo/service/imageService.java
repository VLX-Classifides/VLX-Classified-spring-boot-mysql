package com.webapp.demo.service;

import com.webapp.demo.model.Products;
import com.webapp.demo.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ProductsRepo productsRepo;

    //@Override
    @Transactional
    public Products saveImageFile(int id, MultipartFile file) {

        try {
            Products product = productsRepo.findById(Math.toIntExact(id)).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            product.setImage(byteObjects);

            Products newProduct = productsRepo.save(product);
            return newProduct;
        } catch (IOException e) {
            //todo handle better
            //log.error("Error occurred", e);
            Products product = productsRepo.findById(Math.toIntExact(id)).get();

            e.printStackTrace();

            return product;
        }
    }
}

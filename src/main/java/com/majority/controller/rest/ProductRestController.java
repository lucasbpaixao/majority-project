package com.majority.controller.rest;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.majority.model.Product;
import com.majority.repository.ProductRepository;
import com.majority.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AwsS3Service awsS3Service;
    @GetMapping("/products")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> result = productRepository.findById(id);
        return result.map(product -> ResponseEntity.ok().body(product)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id){
        try{
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @Transactional
    @PutMapping("/edit-product/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product newProduct){
        Optional<Product> result = productRepository.findById(id);
        if(result.isPresent()){
            Product product = result.get();
            product.setName(newProduct.getName());
            product.setCategory(newProduct.getCategory());
            product.setPrice(newProduct.getPrice());
            product.setKeywords(newProduct.getKeywords());

            productRepository.saveAndFlush(product);
            return ResponseEntity.created(URI.create("/products/"+id)).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("create-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        try{
            productRepository.saveAndFlush(product);
            return ResponseEntity.created(URI.create("/products")).body(product);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("save-image")
    public String saveImage(@RequestParam MultipartFile image){
        try {
            return awsS3Service.saveImage(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("get-image")
    public S3Object getImage(){
        return awsS3Service.getImage();
    }
}

package com.hardik.controllers;

import com.hardik.dto.ProductRequest;
import com.hardik.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;
    private Logger logger = Logger.getLogger("ProductController.class");

    @GetMapping
    public ResponseEntity<Object> getProducts(){

        return new ResponseEntity<>(productService.getProducts(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return new ResponseEntity<>("product added" , HttpStatus.CREATED);
    }
}

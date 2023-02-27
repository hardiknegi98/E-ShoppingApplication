package com.hardik.services;

import com.hardik.dto.ProductRequest;
import com.hardik.dto.ProductResponse;
import com.hardik.models.Product;
import com.hardik.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    private Logger logger = Logger.getLogger("ProductService.class");

    public void addProduct(ProductRequest productRequest){
        Product product = Product.builder().
                description(productRequest.getDescription()).
                name(productRequest.getName()).
                price(productRequest.getPrice()).
                build();
        logger.info("Product with id "+product.getId()+" added");
        productRepository.save(product);
    }

    public List<ProductResponse> getProducts() {
        List<Product> productList =  productRepository.findAll();
        return productList.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().
                id(product.getId()).
                name(product.getName()).
                description(product.getDescription()).
                price(product.getPrice()).
                build();
    }

}

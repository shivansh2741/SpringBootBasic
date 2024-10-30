package com.example.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController{
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

     // Create a new product
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Get all products
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
        @RequestParam(defaultValue = "0") int pageNo,
        @RequestParam(defaultValue = "10")int pageSize
    ) {
        Page<Product> products = productService.fetchAllProducts(pageNo, pageSize);
        return ResponseEntity.ok(products);
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> fetchedProduct = productService.fetchProductById(id);

        if(fetchedProduct.isPresent()){
            return ResponseEntity.ok(fetchedProduct.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    // Update a product
    @PutMapping(path = "/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Optional<Product> updatedProductOptional = productService.updateProduct(productId, product);
        
        if (updatedProductOptional.isPresent()) {
            Product updatedProduct = updatedProductOptional.get();
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete a product
 @DeleteMapping(path = "/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        boolean deletionStatus = productService.deleteProduct(productId);
        if (deletionStatus) {
            return ResponseEntity.ok("Product with ID " + productId + " has been deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete product with ID " + productId);
        }
    }
}

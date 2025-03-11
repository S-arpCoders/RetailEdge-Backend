package com.coders.RetailEdge.services.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/inventory/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Create a new product
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            productService.createProduct(product);
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 2. Get a product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        try {
            Product product = productService.getProductById(productId);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. Get all products
    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        try {
            Iterable<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. Update an existing product
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int productId, @RequestBody Product product) {
        try {
            boolean updated = productService.updateProduct(productId, product);
            if (updated) {
                return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        try {
            boolean deleted = productService.deleteProduct(productId);
            if (deleted) {
                return new ResponseEntity<>("Product deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

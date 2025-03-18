package com.coders.RetailEdge.services.Inventory;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

@Service
public class ProductService {

    private final String BASE_URL = System.getenv("database-address") +  "/product";
    private final RestTemplate restTemplate = new RestTemplate();

    public void createProduct(Product product) throws Exception {
        String url = BASE_URL;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.CREATED) {
            throw new Exception("Error creating product. Status: " + response.getStatusCode());
        }
    }

    public Product getProductById(int productId) {
        String url = BASE_URL + "/" + productId;
        ResponseEntity<Product> response = restTemplate.exchange(url, HttpMethod.GET, null, Product.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Iterable<Product> getAllProducts() {
        String url = BASE_URL;
        ResponseEntity<Product[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Product[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        } else {
            return null;
        }
    }

    public boolean updateProduct(int productId, Product updatedProduct) {
        String url = BASE_URL + "/" + productId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(updatedProduct, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }

    public boolean deleteProduct(int productId) {
        String url = BASE_URL + "/" + productId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}

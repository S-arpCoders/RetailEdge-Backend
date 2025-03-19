package com.coders.RetailEdge.services.api_gateway.controller;

import com.coders.RetailEdge.services.api_gateway.security.GateServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
public class ProductsControllerGate {

    private final RestTemplate restTemplate;
    private final GateServiceUtil gateServiceUtil = new GateServiceUtil();

    public ProductsControllerGate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Map<String, Object> product) {

        product.put("secret_key", gateServiceUtil.getSecretKey());
        String url = gateServiceUtil.getUrl("/api/internal/inventory/products");

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, product, Map.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Database Error", "details", e.getResponseBodyAsString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected Error", "message", e.getMessage()));
        }
    }
}

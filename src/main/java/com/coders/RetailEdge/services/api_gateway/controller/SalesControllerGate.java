package com.coders.RetailEdge.services.api_gateway.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/sales")
public class SalesControllerGate {

    private final RestTemplate restTemplate;

    private static final Set<String> VALID_PARAMS = Set.of("a", "b", "c");

    public SalesControllerGate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{param}")
    public ResponseEntity<Map<String, String>> handleSales(
            @PathVariable String param,
            @RequestHeader HttpHeaders headers) {

        if (!headers.containsKey("X-Client-Token")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Missing required header: X-Client-Token"));
        }

        if (!VALID_PARAMS.contains(param)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Invalid route: " + param));
        }

        String internalServiceUrl = "http://localhost:8080/api/internal/hello/" + param;

        ResponseEntity<Map> internalResponse = restTemplate.getForEntity(internalServiceUrl, Map.class);

        if (internalResponse.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(Objects.requireNonNull(internalResponse.getBody()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal service unavailable"));
        }
    }
}

package com.coders.RetailEdge.services.api_gateway.controller;

import com.coders.RetailEdge.services.api_gateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerGate {

    private final RestTemplate restTemplate;

    public AuthControllerGate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> credentials) {

        System.out.println(credentials.toString());
        String authServiceUrl = "localhost:8080";
        String loginUrl = authServiceUrl + "/api/auth/login";
        
        ResponseEntity<Map> authResponse = restTemplate.postForEntity(loginUrl, credentials, Map.class);

        if (authResponse.getStatusCode() == HttpStatus.OK) {
            String userName = (String) authResponse.getBody().get("name");

            String jwtToken = "Bearer " + generateJwtToken(userName);

            Map<String, String> response = new HashMap<>();
            response.put("token", jwtToken);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", "Invalid credentials"));
        }
    }

    private String generateJwtToken(String username) {
        return JwtUtil.generateToken(username);
    }
}

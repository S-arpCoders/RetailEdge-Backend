package com.coders.RetailEdge.services.api_gateway.controller;

import com.coders.RetailEdge.services.api_gateway.security.GateServiceUtil;
import com.coders.RetailEdge.services.api_gateway.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerGate {

    private final RestTemplate restTemplate;

    private final GateServiceUtil gateServiceUtil;

    public AuthControllerGate(RestTemplate restTemplate, GateServiceUtil gateServiceUtil) {
        this.restTemplate = restTemplate;
        this.gateServiceUtil = gateServiceUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> credentials) {

        credentials.put("secret_key",gateServiceUtil.getSecretKey());
        String url = gateServiceUtil.getUrl("/api/internal/auth/login");

        ResponseEntity<Map> authResponse = restTemplate.postForEntity(url, credentials, Map.class);

        System.out.println("This is the response👉🏾 " + authResponse.getBody());

        if (authResponse.getStatusCode() == HttpStatus.OK) {
            System.out.println(authResponse.getBody());
            String userName = (String) Objects.requireNonNull(authResponse.getBody()).get("email");

            System.out.println(userName);
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

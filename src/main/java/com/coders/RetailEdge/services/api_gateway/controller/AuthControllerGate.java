package com.coders.RetailEdge.services.api_gateway.controller;

import com.coders.RetailEdge.services.api_gateway.security.GateServiceUtil;
import com.coders.RetailEdge.services.api_gateway.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
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
        credentials.put("secret_key", gateServiceUtil.getSecretKey());
        String url = gateServiceUtil.getUrl("/api/internal/auth/login");

        try {
            ResponseEntity<Map> authResponse = restTemplate.postForEntity(url, credentials, Map.class);
            if (authResponse.getStatusCode() == HttpStatus.OK) {
                String userName = (String) Objects.requireNonNull(authResponse.getBody()).get("email");
                String jwtToken = "Bearer " + generateJwtToken(userName);

                Map<String, String> response = new HashMap<>();
                response.put("token", jwtToken);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("error", "Connection timeout. Please try again later."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userDetails) {
        userDetails.put("secret_key", gateServiceUtil.getSecretKey());
        String url = gateServiceUtil.getUrl("/api/internal/auth/register");

        try {
            ResponseEntity<Map> registerResponse = restTemplate.postForEntity(url, userDetails, Map.class);
            if (registerResponse.getStatusCode() == HttpStatus.CREATED) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Map.of("message", "User registered successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Registration failed"));
            }
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                    .body(Map.of("error", "Connection timeout. Please try again later."));
        }
    }


    private String generateJwtToken(String username) {
        return JwtUtil.generateToken(username);
    }
}

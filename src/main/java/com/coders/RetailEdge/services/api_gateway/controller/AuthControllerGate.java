package com.coders.RetailEdge.services.api_gateway.controller;

import com.coders.RetailEdge.services.api_gateway.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
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
        String authServiceUrl = "http://localhost:8080";  // Adjust this to the correct auth service URL if needed
        String loginUrl = authServiceUrl + "/api/auth/login";

        try {
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
        } catch (HttpClientErrorException.NotFound e) {
            // Handle 404: User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found. Please check your email and try again."));
        } catch (HttpClientErrorException e) {
            // Handle other client errors (e.g., 400, 401, etc.)
            return ResponseEntity.status(e.getStatusCode())
                    .body(Map.of("error", "Authentication failed: " + e.getResponseBodyAsString()));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred. Please try again later."));
        }
    }

    private String generateJwtToken(String username) {
        return JwtUtil.generateToken(username);
    }
}

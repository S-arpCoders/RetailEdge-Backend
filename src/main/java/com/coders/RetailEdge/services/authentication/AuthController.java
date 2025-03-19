package com.coders.RetailEdge.services.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/internal/auth")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class AuthController {

    private final UserService userService;

    // Constructor with correct name
    public AuthController(UserService userService) { // Updated constructor name
        this.userService = userService;
    }

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, Object> userDetails) {
        return userService.registerUser(userDetails);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, Object> credentials) {
        return userService.loginUser(credentials);
    }
}

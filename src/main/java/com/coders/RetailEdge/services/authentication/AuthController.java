package com.coders.RetailEdge.services.authentication;


import com.coders.RetailEdge.services.authentication.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController { // Changed from UserController to AuthController for consistency

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

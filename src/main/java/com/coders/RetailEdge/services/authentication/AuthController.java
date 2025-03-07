package com.coders.RetailEdge.services.authentication;

import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@PropertySource("auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> credentials) {
        String name = (String) credentials.get("name");
        String lastname = (String) credentials.get("lastname");
        int age = (Integer) credentials.get("age");

        // For simplicity, just checking if the user exists with these details
        if ("Hello".equals(name) && "world".equals(lastname) && age > 18) {
            return Map.of("name", name, "status", "success");
        } else {
            return Map.of("error", "Invalid credentials");
        }
    }
}

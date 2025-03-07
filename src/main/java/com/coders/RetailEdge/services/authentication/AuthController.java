package com.coders.RetailEdge.services.authentication;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/internal/auth")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> credentials) {
        System.out.println(credentials.toString());
        String name = (String) credentials.get("name");
        String lastname = (String) credentials.get("lastname");
        int age = (Integer) credentials.get("age");
        String secret_key = (String) credentials.get("secret_key");

        if (!Objects.equals(secret_key, System.getenv("secret_key"))) {
            return Map.of("error", "Permission Rejected");
        }

        if ("Hello".equals(name) && "world".equals(lastname) && age > 18) {
            return Map.of("name", name, "status", "success");
        } else {
            return Map.of("error", "Invalid credentials");
        }
    }

}

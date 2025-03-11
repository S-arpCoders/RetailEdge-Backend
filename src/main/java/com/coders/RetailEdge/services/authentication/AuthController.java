package com.coders.RetailEdge.services.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/internal/auth")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> credentials) {
        System.out.println(credentials.toString());
        String email = (String) credentials.get("email");
        String lastname = (String) credentials.get("password");
        String secret_key = (String) credentials.get("secret_key");

        if (!Objects.equals(secret_key, System.getenv("secret_key"))) {
            // Return 403 Forbidden status for invalid secret_key
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Permission Rejected"));
        }

        if ("valerie@gmail.com".equals(email) && "password".equals(lastname)) {
            // Return 200 OK status for successful login
            return ResponseEntity.ok(Map.of("email", email, "status", "Hi valerie"));
        } else {
            // Return 401 Unauthorized status for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }
}

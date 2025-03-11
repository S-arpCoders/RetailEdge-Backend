package com.coders.RetailEdge.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://172.20.7.187:8080/api/v1/users"; // Update with your backend's base URL

    // Register a new user
    public ResponseEntity<String> registerUser(Map<String, Object> userDetails) {
        String url = BASE_URL + "/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userDetails, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    // Login a user by email and password
    public ResponseEntity<String> loginUser(Map<String, Object> credentials) {

        String email = (String) credentials.get("email");
        String url = BASE_URL + "/email/" + email ;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response);

        return response;
    }
}

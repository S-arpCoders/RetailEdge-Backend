package com.coders.RetailEdge.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    JsonUtil util = new JsonUtil();

    private static final String BASE_URL = "http://172.20.7.187:8081/users"; // Update with your backend's base URL

    // Register a new user
    public ResponseEntity<String> registerUser(Map<String, Object> userDetails) {
        String url = BASE_URL + "/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userDetails, headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    public ResponseEntity<String> loginUser(Map<String, Object> credentials) {
        String email = (String) credentials.get("email");
        String password = (String) credentials.get("password");
        String url = BASE_URL + "/email/" + email;
        System.out.println(credentials.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(credentials, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            System.out.println(response.getStatusCode());

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                String storedPassword = util.extractItemFromJson("password", responseBody);

                if (storedPassword != null && storedPassword.equals(password)) {
                    return ResponseEntity.ok(responseBody);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Incorrect password. Please try again.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found. Please check your email and try again.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            System.out.println(e.getStatusCode());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found. Please check your email and try again.");
        } catch (HttpClientErrorException e) {
            // Handle other HTTP errors
            return ResponseEntity.status(e.getStatusCode()).body("Authentication failed: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

}

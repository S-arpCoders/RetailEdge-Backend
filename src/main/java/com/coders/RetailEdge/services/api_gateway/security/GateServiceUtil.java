package com.coders.RetailEdge.services.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateServiceUtil {

    private final Object secretKey;

    public GateServiceUtil(){
        secretKey = System.getenv("secret_key");
    }

    public String getUrl(String path) {
        try {
            String serverAddress = System.getenv("server-address");
            if (serverAddress == null || serverAddress.isEmpty()) {
                serverAddress = "http://localhost:8080";
            }
            return serverAddress + path;
        } catch (Exception e) {
            return "http://localhost:8080" + path;
        }
    }

    public Object getSecretKey() {
        return secretKey;
    }

}

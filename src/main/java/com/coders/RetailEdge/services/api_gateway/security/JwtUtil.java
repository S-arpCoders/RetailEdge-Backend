package com.coders.RetailEdge.services.api_gateway.security;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {
    private static final String SECRET_KEY = "my-secret-key";

    public static String generateToken(String username) {
        long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60);//for 1 hour
        String header = Base64.getUrlEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());

        String payload = String.format("{\"user\":\"%s\",\"exp\":%d}", username, expirationTime);
        String encodedPayload = Base64.getUrlEncoder().encodeToString(payload.getBytes());

        String signature = sign(header + "." + encodedPayload, SECRET_KEY);

        return header + "." + encodedPayload + "." + signature;
    }

    static String sign(String data, String secret) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            hmac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
            return Base64.getUrlEncoder().encodeToString(hmac.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error signing JWT", e);
        }
    }
}

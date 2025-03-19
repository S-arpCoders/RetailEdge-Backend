package com.coders.RetailEdge.services.api_gateway.security;

import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtil {
    private static final String SECRET_KEY = (String) new GateServiceUtil().getSecretKey();

    public static String generateToken(String username) {
        long expirationTime = System.currentTimeMillis() + (1000 * 60 * 60); // 1 hour
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

    public static String validateToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return "Invalid token";

            String header = parts[0];
            String payload = parts[1];
            String receivedSignature = parts[2];

            // Verify the signature
            String expectedSignature = sign(header + "." + payload, SECRET_KEY);
            if (!expectedSignature.equals(receivedSignature)) {
                return "Invalid token signature";
            }

            // Decode and check expiration
            String payloadJson = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
            JSONObject payloadObj = new JSONObject(payloadJson);

            long exp = payloadObj.getLong("exp");
            if (exp < System.currentTimeMillis()) {
                return "Token expired";
            }

            return "Valid token";
        } catch (Exception e) {
            return "Invalid token";
        }
    }
}

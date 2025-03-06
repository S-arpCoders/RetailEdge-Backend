package com.coders.RetailEdge.services.api_gateway.security;

import java.util.Base64;
import org.json.JSONObject;


public class JwtValidator {
    public static boolean isValidToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;

            String signature = JwtUtil.sign(parts[0] + "." + parts[1], "my-secret-key");
            if (!signature.equals(parts[2])) return false;

            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject jsonPayload = new JSONObject(payload);
            long exp = jsonPayload.getLong("exp");

            return System.currentTimeMillis() < exp;
        } catch (Exception e) {
            return false;
        }
    }
}

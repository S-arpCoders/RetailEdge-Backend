package com.coders.RetailEdge.api_gateway;

import com.coders.RetailEdge.services.api_gateway.security.JwtUtil;
import com.coders.RetailEdge.services.api_gateway.security.JwtValidator;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;


public class TestJwt {
    @Test
    public void JwtTest() {
        String token = JwtUtil.generateToken("testUser");
        System.out.println("Generated Token: " + token);
        System.out.println("Is Token Valid? " + JwtValidator.isValidToken(token));
        //Assert(JwtValidator.isValidToken(token));
    }
}

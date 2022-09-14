package com.microservice.restservice.unit;

import com.microservice.restservice.auxillary.JWTSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTSupportTest {

    JWTSupport jwtSupport = new JWTSupport();

    @Test
    public void generateAndValidateTokenTest() {
        String name = "Ivan";
        String id = "ID_12345678";
        String token = jwtSupport.generateToken(name, id);
        boolean isValidationSucceed = jwtSupport.validateToken(token, name, id);
        Assertions.assertTrue(isValidationSucceed, "JJWT validation failed");
    }

}

package com.worthmate.demo.config.JwtSecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        String base64Key = Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println("Your JWT secret (Base64): " + base64Key);
    }
}

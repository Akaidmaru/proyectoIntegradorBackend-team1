package com.petcare.backend.proyectoIntegrador.util;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;
import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateKey {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Generated Key: " + secretString);
        String secret = "guYM4SQYUmxFx0VQCQnzWCdS+UZ3IiDXpaI9HgqaZmM=";
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        System.out.println("Key length (bits): " + keyBytes.length * 8); // Should print 256

        }

}

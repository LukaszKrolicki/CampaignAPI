package com.app.campaignapi.services.impl.entry;

import com.app.campaignapi.repositories.UserRepository;
import com.app.campaignapi.services.JWTservice_;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTserviceImpl implements JWTservice_ {
    private String secretKey;

    public JWTserviceImpl(UserRepository userRepo){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String generateToken(String username, UUID userId, String role) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("role", role);

        return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()  + 60 * 60 * 30*10))
                .and().signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUserName(String token) {
        token = token.trim();
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractSpecifiedClaim(String token, String type) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return extractClaim(token, claims -> UUID.fromString(claims.get(type, String.class)));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

package com.app.campaignapi.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;
public interface JWTservice_ {
    String generateToken(String username, UUID userId, String role);
    String extractUserName(String token);
    UUID extractSpecifiedClaim(String token, String type);
    boolean validateToken(String token, UserDetails userDetails);
}
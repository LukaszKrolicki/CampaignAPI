package com.app.campaignapi.controllers;

import com.app.campaignapi.domain.Dtos.CampaignDTO;
import com.app.campaignapi.domain.Dtos.ProductDTO;
import com.app.campaignapi.services.impl.entry.JWTserviceImpl;
import com.app.campaignapi.services.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JWTserviceImpl jwtService;

    public UserController(UserService userService, JWTserviceImpl jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestHeader("Authorization") String token, @Valid @RequestBody ProductDTO productDTO) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.addProduct(userId, productDTO));
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String token) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.getProducts(userId));
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String token, @RequestParam UUID productId) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.deleteProduct(userId, productId));
    }

    @PostMapping("/campaigns")
    public ResponseEntity<?> addCampaign(@RequestHeader("Authorization") String token, @Valid @RequestBody CampaignDTO campaignDTO ) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.addCampaign(userId, campaignDTO));
    }

    @GetMapping("/campaigns")
    public ResponseEntity<?> getCampaigns(@RequestHeader("Authorization") String token) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.getCampaigns(userId));
    }

    @DeleteMapping("/campaigns")
    public ResponseEntity<?> deleteCampaign(@RequestHeader("Authorization") String token, @RequestParam UUID campaignId) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.deleteCampaign(userId, campaignId));
    }

    @PutMapping("/campaigns")
    public ResponseEntity<?> updateCampaign(@RequestHeader("Authorization") String token, @Valid @RequestBody CampaignDTO campaignDTO) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.updateCampaign(userId, campaignDTO));
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(@RequestHeader("Authorization") String token) {
        UUID userId = jwtService.extractSpecifiedClaim(token, "userId");
        return ResponseEntity.ok(userService.getUserBalance(userId));
    }
}

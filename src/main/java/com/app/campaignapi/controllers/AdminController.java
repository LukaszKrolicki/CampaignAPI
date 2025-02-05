package com.app.campaignapi.controllers;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Dtos.TownDTO;
import com.app.campaignapi.mappers.impl.TownMapper;
import com.app.campaignapi.repositories.KeywordRepository;
import com.app.campaignapi.repositories.TownRepository;
import com.app.campaignapi.services.impl.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }

    @PostMapping("/towns")
    public ResponseEntity<?> addTown(@Valid @RequestBody TownDTO townDTO) {
        return ResponseEntity.ok(adminService.addTown(townDTO));
    }

    @DeleteMapping("/towns")
    public ResponseEntity<?> deleteTown(@RequestParam UUID id) {
        return ResponseEntity.ok(adminService.deleteTown(id));
    }

    @PostMapping("/keywords")
    public ResponseEntity<?> addKeyword(@Valid @RequestBody KeywordDTO keyword) {
        return ResponseEntity.ok(adminService.addKeyword(keyword));
    }
    @DeleteMapping("/keywords")
    public ResponseEntity<?> deleteKeyword(@RequestParam UUID id) {
        return ResponseEntity.ok(adminService.deleteKeyword(id));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUser(@RequestParam UUID id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }
}

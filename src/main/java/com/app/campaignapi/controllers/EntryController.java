package com.app.campaignapi.controllers;

import com.app.campaignapi.domain.Dtos.AuthResponseDTO;
import com.app.campaignapi.domain.Dtos.UserDTO;
import com.app.campaignapi.services.impl.entry.EntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/entry")
public class EntryController {
    EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }
    @PostMapping("/register")
    public String register(@Valid @RequestBody UserDTO userDTO) {
        return entryService.createUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        AuthResponseDTO authResponse = entryService.login(email, password);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", authResponse.getToken())
                .body(authResponse);
    }
}

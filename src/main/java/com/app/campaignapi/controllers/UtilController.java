package com.app.campaignapi.controllers;

import com.app.campaignapi.services.impl.UtilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/util")
public class UtilController {
    UtilService utilService;

    public UtilController(UtilService utilService) {
        this.utilService = utilService;
    }

    @GetMapping("/towns")
    public ResponseEntity<?> getTowns() {
        return ResponseEntity.ok(utilService.getTowns());
    }

    @GetMapping("/keywords")
    public ResponseEntity<?> getKeywords() {
        return ResponseEntity.ok(utilService.getKeywords());
    }
}

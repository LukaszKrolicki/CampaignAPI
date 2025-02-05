package com.app.campaignapi.services;

import com.app.campaignapi.domain.Dtos.AuthResponseDTO;
import com.app.campaignapi.domain.Dtos.UserDTO;

import java.util.UUID;

public interface EntryService_ {
    String createUser(UserDTO userDTO);
    AuthResponseDTO login(String email, String password);
    boolean userExists(String email);
}
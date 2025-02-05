package com.app.campaignapi.domain.Dtos;

import com.app.campaignapi.domain.Entities.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
public class AuthResponseDTO {
    private UserDTO normalUser;

    private String token;

    public AuthResponseDTO(UserDTO normalUser, String token) {
        this.normalUser = normalUser;
        this.token = token;
    }
}

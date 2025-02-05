package com.app.campaignapi.domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TownDTO {
    private UUID id;

    @NotBlank(message = "Town name is required.")
    private String name;
}

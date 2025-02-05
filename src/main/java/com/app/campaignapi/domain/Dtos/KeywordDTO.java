package com.app.campaignapi.domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordDTO {
    private UUID id;

    @NotBlank(message = "Keyword value is mandatory")
    private String cvalue;
}
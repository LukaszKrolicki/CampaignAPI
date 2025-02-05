package com.app.campaignapi.domain.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    private UUID sellerId;
}

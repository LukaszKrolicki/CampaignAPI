package com.app.campaignapi.domain.Dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private UUID id;

    @NotBlank(message = "Campaign name is mandatory")
    private String campaignName;

    @NotNull(message = "Keywords are mandatory")
    private Set<UUID> keywordIds;

    private List<KeywordDTO> keywords;

    @NotNull(message = "Bid amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Bid amount must be greater than zero")
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Campaign fund must be greater than zero")
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    private Boolean status;

    private UUID townId;

    private String townCname;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 0, message = "Radius must be greater than or equal to zero")
    private Integer radius;

    @NotNull(message = "Product ID is mandatory")
    private UUID productId;
}
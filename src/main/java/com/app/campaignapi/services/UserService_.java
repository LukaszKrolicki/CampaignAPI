package com.app.campaignapi.services;

import com.app.campaignapi.domain.Dtos.CampaignDTO;
import com.app.campaignapi.domain.Dtos.ProductDTO;
import java.util.List;
import java.util.UUID;

public interface UserService_ {
    String addProduct(UUID userId, ProductDTO productDTO);
    List<ProductDTO> getProducts(UUID userId);
    String deleteProduct(UUID userId, UUID productId);
    String addCampaign(UUID userId, CampaignDTO campaignDTO);
    List<CampaignDTO> getCampaigns(UUID userId);
    String deleteCampaign(UUID userId, UUID campaignId);
    String updateCampaign(UUID userId, CampaignDTO campaignDTO);
}
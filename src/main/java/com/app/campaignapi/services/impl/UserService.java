package com.app.campaignapi.services.impl;

import com.app.campaignapi.domain.Dtos.CampaignDTO;
import com.app.campaignapi.domain.Dtos.ProductDTO;
import com.app.campaignapi.domain.Entities.*;
import com.app.campaignapi.exceptions.NotAuthorizedException;
import com.app.campaignapi.mappers.Mapper;
import com.app.campaignapi.repositories.*;
import com.app.campaignapi.services.UserService_;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserService_ {
    private final ProductRepository productRepository;
    private final Mapper<Product, ProductDTO> productMapper;
    private final UserRepository userRepository;
    private final Mapper<Campaign, CampaignDTO> campaignMapper;
    private final CampaignRepository campaignRepository;
    private final TownRepository townRepository;
    private final KeywordRepository keywordRepository;

    public UserService(ProductRepository productRepository, Mapper<Product, ProductDTO> productMapper, UserRepository userRepository, Mapper<Campaign, CampaignDTO> campaignMapper, CampaignRepository campaignRepository, TownRepository townRepository, KeywordRepository keywordRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.userRepository = userRepository;
        this.campaignMapper = campaignMapper;
        this.campaignRepository = campaignRepository;
        this.townRepository = townRepository;
        this.keywordRepository = keywordRepository;
    }

    public String addProduct(UUID userId, ProductDTO productDTO) {
        Product product = productMapper.mapToEntity(productDTO);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotAuthorizedException("User not found"));
        product.setSeller(user);
        productRepository.save(product);
        return "Product added successfully";
    }

    public List<ProductDTO> getProducts(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotAuthorizedException("User not found"));
        return productRepository.findAllBySeller(user).stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public String deleteProduct(UUID userId, UUID productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotAuthorizedException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!product.getSeller().equals(user)) {
            throw new IllegalArgumentException("You are not the owner of this product");
        }

        product.getCampaigns().forEach(campaign -> {
            user.setBalance(user.getBalance().add(campaign.getCampaignFund()));
        });

        productRepository.deleteById(productId);
        userRepository.save(user);

        return "Product deleted successfully and remaining budget returned to seller";
    }

    @Transactional
    public String addCampaign(UUID userId, CampaignDTO campaignDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotAuthorizedException("User not found"));
        Product product = productRepository.findById(campaignDTO.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getSeller().equals(user)) {
            throw new NotAuthorizedException("You are not the owner of this product");
        }

        Town town;
        if (campaignDTO.getTownId() != null) {
            town = townRepository.findById(campaignDTO.getTownId()).orElseThrow(() -> new IllegalArgumentException("Town not found"));
        } else {
            try {
                town = townRepository.findByName(campaignDTO.getTownCname()).orElseGet(() -> {
                    Town newTown = new Town();
                    newTown.setName(campaignDTO.getTownCname());
                    return townRepository.save(newTown);
                });
            } catch (Exception e) {
                throw new IllegalArgumentException("Town not found");
            }
        }

        Set<Keyword_> keywords = new HashSet<>(keywordRepository.findAllById(campaignDTO.getKeywordIds()));
        if (keywords.size() != campaignDTO.getKeywordIds().size()) {
            throw new IllegalArgumentException("Some keywords are not found in the database");
        }

        Campaign campaign = campaignMapper.mapToEntity(campaignDTO);
        campaign.setProduct(product);
        campaign.setTown(town);
        campaign.setKeywords(keywords);

        if (campaignDTO.getBidAmount().compareTo(campaignDTO.getCampaignFund()) > 0) {
            throw new IllegalArgumentException("Bid amount cannot be higher than campaign budget");
        }

        user.setBalance(user.getBalance().subtract(campaignDTO.getCampaignFund()));
        if (user.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        campaignRepository.save(campaign);
        userRepository.save(user);

        return "Campaign added successfully";
    }

    public List<CampaignDTO> getCampaigns(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Campaign> campaigns = campaignRepository.findAllByProduct_Seller(user);
        return campaigns.stream()
                .map(campaignMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public String deleteCampaign(UUID userId, UUID campaignId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Campaign campaign = campaignRepository.findById(campaignId).orElseThrow(() -> new IllegalArgumentException("Campaign not found"));
        if (!campaign.getProduct().getSeller().equals(user)) {
            throw new IllegalArgumentException("You are not the owner of this campaign");
        }

        user.setBalance(user.getBalance().add(campaign.getCampaignFund()));
        campaignRepository.deleteById(campaignId);
        userRepository.save(user);

        return "Campaign deleted successfully and remaining budget returned to seller";
    }

    @Transactional
    public String updateCampaign(UUID userId, CampaignDTO campaignDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Campaign existingCampaign = campaignRepository.findById(campaignDTO.getId()).orElseThrow(() -> new RuntimeException("Campaign not found"));

        if (!existingCampaign.getProduct().getSeller().equals(user)) {
            throw new NotAuthorizedException("You are not the owner of this campaign");
        }

        BigDecimal oldFund = existingCampaign.getCampaignFund();
        BigDecimal newFund = campaignDTO.getCampaignFund();
        BigDecimal fundDifference = newFund.subtract(oldFund);

        if (campaignDTO.getBidAmount().compareTo(campaignDTO.getCampaignFund()) > 0) {
            throw new IllegalArgumentException("Bid amount cannot be higher than campaign budget");
        }

        user.setBalance(user.getBalance().subtract(fundDifference));
        if (user.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        existingCampaign.setCampaignName(campaignDTO.getCampaignName());
        existingCampaign.setBidAmount(campaignDTO.getBidAmount());
        existingCampaign.setCampaignFund(newFund);
        existingCampaign.setStatus(campaignDTO.getStatus());
        existingCampaign.setRadius(campaignDTO.getRadius());

        if (campaignDTO.getTownId() != null) {
            Town town = townRepository.findById(campaignDTO.getTownId()).orElseThrow(() -> new IllegalArgumentException("Town not found"));
            existingCampaign.setTown(town);
        } else {
            Town town = townRepository.findByName(campaignDTO.getTownCname()).orElseGet(() -> {
                Town newTown = new Town();
                newTown.setName(campaignDTO.getTownCname());
                return townRepository.save(newTown);
            });
            existingCampaign.setTown(town);
        }

        Set<Keyword_> keywords = new HashSet<>(keywordRepository.findAllById(campaignDTO.getKeywordIds()));
        if (keywords.size() != campaignDTO.getKeywordIds().size()) {
            throw new IllegalArgumentException("Some keywords are not found in the database");
        }
        existingCampaign.setKeywords(keywords);

        campaignRepository.save(existingCampaign);
        userRepository.save(user);

        return "Campaign updated successfully";
    }

    public BigDecimal getUserBalance(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getBalance();
    }
}
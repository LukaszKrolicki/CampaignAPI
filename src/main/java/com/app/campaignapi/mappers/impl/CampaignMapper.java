package com.app.campaignapi.mappers.impl;

import com.app.campaignapi.domain.Dtos.CampaignDTO;
import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Entities.Campaign;
import com.app.campaignapi.domain.Entities.Keyword_;
import com.app.campaignapi.mappers.Mapper;
import com.app.campaignapi.repositories.KeywordRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CampaignMapper implements Mapper<Campaign, CampaignDTO> {

    private final KeywordRepository keywordRepository;
    private final Mapper<Keyword_, KeywordDTO> keywordMapper;

    public CampaignMapper(KeywordRepository keywordRepository, Mapper<Keyword_, KeywordDTO> keywordMapper) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    @Override
    public CampaignDTO mapToDto(Campaign campaign) {
        if (campaign == null) {
            return null;
        }

        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(campaign.getId());
        campaignDTO.setCampaignName(campaign.getCampaignName());
        campaignDTO.setKeywordIds(campaign.getKeywords().stream().map(Keyword_::getId).collect(Collectors.toSet()));
        campaignDTO.setKeywords(campaign.getKeywords().stream().map(keywordMapper::mapToDto).collect(Collectors.toList()));
        campaignDTO.setBidAmount(campaign.getBidAmount());
        campaignDTO.setCampaignFund(campaign.getCampaignFund());
        campaignDTO.setStatus(campaign.getStatus());
        campaignDTO.setTownId(campaign.getTown().getId());
        campaignDTO.setTownCname(campaign.getTown().getName());
        campaignDTO.setRadius(campaign.getRadius());
        campaignDTO.setProductId(campaign.getProduct().getId());

        return campaignDTO;
    }

    @Override
    public Campaign mapToEntity(CampaignDTO campaignDTO) {
        if (campaignDTO == null) {
            return null;
        }

        Campaign campaign = new Campaign();
        campaign.setId(campaignDTO.getId());
        campaign.setCampaignName(campaignDTO.getCampaignName());
        campaign.setBidAmount(campaignDTO.getBidAmount());
        campaign.setCampaignFund(campaignDTO.getCampaignFund());
        campaign.setStatus(campaignDTO.getStatus());
        campaign.setRadius(campaignDTO.getRadius());

        Set<Keyword_> keywords = new HashSet<>(keywordRepository.findAllById(campaignDTO.getKeywordIds()));
        campaign.setKeywords(keywords);
        return campaign;
    }
}
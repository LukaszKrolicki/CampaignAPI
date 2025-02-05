package com.app.campaignapi.services.impl;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Dtos.TownDTO;
import com.app.campaignapi.domain.Entities.Keyword_;
import com.app.campaignapi.domain.Entities.Town;
import com.app.campaignapi.mappers.Mapper;
import com.app.campaignapi.repositories.KeywordRepository;
import com.app.campaignapi.repositories.TownRepository;
import com.app.campaignapi.services.UtilService_;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilService implements UtilService_ {
    private final TownRepository townRepository;
    private final Mapper<Town, TownDTO> townMapper;
    private final KeywordRepository keywordRepository;
    private final Mapper<Keyword_, KeywordDTO> keywordMapper;

    public UtilService(TownRepository townRepository, Mapper<Town, TownDTO> townMapper, KeywordRepository keywordRepository, Mapper<Keyword_, KeywordDTO> keywordMapper) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
    }

    public List<TownDTO> getTowns() {
        return townRepository.findAll().stream()
                .map(townMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<KeywordDTO> getKeywords() {
        return keywordRepository.findAll().stream()
                .map(keywordMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
package com.app.campaignapi.mappers.impl;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Entities.Keyword_;
import com.app.campaignapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KeywordMapper implements Mapper<Keyword_, KeywordDTO> {

    private final ModelMapper mapper;

    public KeywordMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public KeywordDTO mapToDto(Keyword_ keyword) {
        return mapper.map(keyword, KeywordDTO.class);
    }

    @Override
    public Keyword_ mapToEntity(KeywordDTO keywordDTO) {
        return mapper.map(keywordDTO, Keyword_.class);
    }
}
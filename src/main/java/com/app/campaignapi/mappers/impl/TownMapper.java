package com.app.campaignapi.mappers.impl;

import com.app.campaignapi.domain.Dtos.TownDTO;
import com.app.campaignapi.domain.Entities.Town;
import com.app.campaignapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TownMapper implements Mapper<Town, TownDTO> {

    private final ModelMapper mapper;

    public TownMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TownDTO mapToDto(Town town) {
        return mapper.map(town, TownDTO.class);
    }

    @Override
    public Town mapToEntity(TownDTO townDTO) {
        return mapper.map(townDTO, Town.class);
    }
}

package com.app.campaignapi.services;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Dtos.TownDTO;

import java.util.List;

public interface UtilService_ {
    List<TownDTO> getTowns();
    List<KeywordDTO> getKeywords();
}
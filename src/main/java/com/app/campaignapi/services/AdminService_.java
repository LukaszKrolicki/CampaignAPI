package com.app.campaignapi.services;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Dtos.TownDTO;
import com.app.campaignapi.domain.Dtos.UserDTO;
import java.util.List;
import java.util.UUID;

public interface AdminService_ {
    String addTown(TownDTO townDTO);
    String deleteTown(UUID id);
    String addKeyword(KeywordDTO keywordDTO);
    String deleteKeyword(UUID id);
    List<UserDTO> getUsers();
    String deleteUser(UUID id);
}
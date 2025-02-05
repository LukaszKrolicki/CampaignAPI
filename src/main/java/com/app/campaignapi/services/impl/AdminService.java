package com.app.campaignapi.services.impl;

import com.app.campaignapi.domain.Dtos.KeywordDTO;
import com.app.campaignapi.domain.Dtos.TownDTO;
import com.app.campaignapi.domain.Dtos.UserDTO;
import com.app.campaignapi.domain.Entities.Keyword_;
import com.app.campaignapi.domain.Entities.Town;
import com.app.campaignapi.domain.Entities.User;
import com.app.campaignapi.mappers.Mapper;
import com.app.campaignapi.repositories.KeywordRepository;
import com.app.campaignapi.repositories.TownRepository;
import com.app.campaignapi.repositories.UserRepository;
import com.app.campaignapi.services.AdminService_;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService implements AdminService_ {
    private final TownRepository townRepository;
    private final Mapper<Town, TownDTO> townMapper;
    private final KeywordRepository keywordRepository;
    private final Mapper<Keyword_, KeywordDTO> keywordMapper;
    private final Mapper<User, UserDTO> userMapper;
    private final UserRepository userRepository;

    public AdminService(TownRepository townRepository, Mapper<Town, TownDTO> townMapper, KeywordRepository keywordRepository, Mapper<Keyword_, KeywordDTO> keywordMapper, Mapper<User, UserDTO> userMapper, UserRepository userRepository) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public String addTown(TownDTO townDTO) {
        System.out.println("townDTO = " + townDTO.getName());
        Town town = townMapper.mapToEntity(townDTO);
        System.out.println("town = " + town);
        townRepository.save(town);
        return "Town added successfully";
    }

    @Transactional
    public String deleteTown(UUID id) {
        townRepository.findById(id).orElseThrow(() -> new RuntimeException("Town not found"));
        townRepository.deleteById(id);
        return "Town deleted successfully";
    }

    public String addKeyword(KeywordDTO keywordDTO) {
        Keyword_ keyword = keywordMapper.mapToEntity(keywordDTO);
        keywordRepository.save(keyword);
        return ".keyword added successfully";

    }

    public String deleteKeyword(UUID id) {
        keywordRepository.findById(id).orElseThrow(() -> new RuntimeException("Keyword not found"));
        keywordRepository.deleteById(id);
        return ".keyword deleted successfully";
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public String deleteUser(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public KeywordDTO getKeywordByValue(String value) {
        Keyword_ keyword = keywordRepository.findByCvalue(value);
        return keywordMapper.mapToDto(keyword);
    }
}
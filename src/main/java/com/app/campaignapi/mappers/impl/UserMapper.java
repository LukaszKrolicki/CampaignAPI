package com.app.campaignapi.mappers.impl;
import com.app.campaignapi.domain.Dtos.UserDTO;
import com.app.campaignapi.domain.Entities.User;
import com.app.campaignapi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    private final ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDTO mapToDto(User user) {
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User mapToEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
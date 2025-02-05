package com.app.campaignapi.services.impl.entry;

import com.app.campaignapi.domain.Dtos.AuthResponseDTO;
import com.app.campaignapi.domain.Dtos.UserDTO;
import com.app.campaignapi.domain.Entities.User;
import com.app.campaignapi.exceptions.NotAuthorizedException;
import com.app.campaignapi.mappers.Mapper;
import com.app.campaignapi.repositories.UserRepository;
import com.app.campaignapi.services.EntryService_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EntryService implements EntryService_ {
    private final UserRepository userRepository;
    private final Mapper<User, UserDTO> userMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    private final JWTserviceImpl jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public EntryService(UserRepository userRepository, Mapper<User, UserDTO> userDTOMapper, JWTserviceImpl jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userDTOMapper;
        this.jwtService = jwtService;
    }

    @Transactional
    public String createUser(UserDTO userDTO) {
        System.out.println("userDTO = " + userDTO);
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = userMapper.mapToEntity(userDTO);
        System.out.println("user = " + user);
        userRepository.save(user);
        return "User created successfully";
    }

    @Transactional
    public AuthResponseDTO login(String email, String password) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (!authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        User userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new NotAuthorizedException("User not found");
        }

        UUID userId = userEntity.getId();
        String role = userEntity.getRole();
        String token = jwtService.generateToken(email, userId, role);
        return new AuthResponseDTO(userMapper.mapToDto(userEntity), token);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public UUID getUserIdByEmail(String email) {
        return userRepository.findByEmail(email).getId();
    }
}
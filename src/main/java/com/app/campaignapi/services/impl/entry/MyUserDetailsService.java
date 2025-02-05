package com.app.campaignapi.services.impl.entry;

import com.app.campaignapi.domain.Entities.User;
import com.app.campaignapi.domain.UserPrincipal;
import com.app.campaignapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user != null){
            return new UserPrincipal(user);
        }
        else{
            throw new UsernameNotFoundException("User not found");
        }
    }
}
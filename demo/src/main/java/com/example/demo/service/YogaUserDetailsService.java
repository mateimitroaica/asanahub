package com.example.demo.service;

import com.example.demo.domain.YogaUser;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YogaUserDetailsService implements UserDetailsService {

    private final YogaUserRepository yogaUserRepository;

    public YogaUserDetailsService(YogaUserRepository yogaUserRepository) {
        this.yogaUserRepository = yogaUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        YogaUser yogaUser = yogaUserRepository.findYogaUsersByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(email)
        );

        return new User(
                yogaUser.getEmail(),
                yogaUser.getPassword(),
                List.of(new SimpleGrantedAuthority(yogaUser.getRole().name()))
        );
    }
}

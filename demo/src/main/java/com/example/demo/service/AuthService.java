package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.mappers.YogaUserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final YogaUserMapper yogaUserMapper;

    public AuthService(AuthenticationManager authenticationManager, YogaUserMapper yogaUserMapper) {
        this.authenticationManager = authenticationManager;
        this.yogaUserMapper = yogaUserMapper;
    }

    public void login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authToken = yogaUserMapper.toAuthentication(loginDTO);
        authenticationManager.authenticate(authToken);
    }
}

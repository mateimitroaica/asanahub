package com.example.demo.service;

import com.example.demo.domain.YogaUser;
import com.example.demo.dto.YogaUserRegistrationDTO;
import com.example.demo.mappers.YogaUserMapper;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class YogaUserService {
    private final YogaUserRepository yogaUserRepository;
    private final YogaUserMapper yogaUserMapper;

    public YogaUserService(YogaUserRepository yogaUserRepository, YogaUserMapper yogaUserMapper) {
        this.yogaUserRepository = yogaUserRepository;
        this.yogaUserMapper = yogaUserMapper;
    }

    public boolean registerNewUser(YogaUserRegistrationDTO yogaUserRegistrationDTO) {
        if (yogaUserRepository.existsByEmail(yogaUserRegistrationDTO.getEmail())) {
            return false;
        }
        YogaUser yogaUser = yogaUserMapper.toYogaUser(yogaUserRegistrationDTO);
        yogaUserRepository.save(yogaUser);
        return true;
    }
}

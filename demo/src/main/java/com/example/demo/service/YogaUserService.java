package com.example.demo.service;

import com.example.demo.domain.YogaUser;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.dto.YogaUserRegistrationDTO;
import com.example.demo.exceptions.EmailAlreadyExistsException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.YogaUserMapper;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class YogaUserService {
    private final YogaUserRepository yogaUserRepository;
    private final YogaUserMapper yogaUserMapper;
    private final PasswordEncoder passwordEncoder;

    public YogaUserService(YogaUserRepository yogaUserRepository,
                           YogaUserMapper yogaUserMapper,
                           PasswordEncoder passwordEncoder) {
        this.yogaUserRepository = yogaUserRepository;
        this.yogaUserMapper = yogaUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerNewUser(YogaUserRegistrationDTO yogaUserRegistrationDTO) {
        if (yogaUserRepository.existsByEmail(yogaUserRegistrationDTO.getEmail())) {
            return false;
        }
        YogaUser yogaUser = yogaUserMapper.toYogaUser(yogaUserRegistrationDTO);
        yogaUserRepository.save(yogaUser);
        return true;
    }

    public String updateUserProfile(String email, UserUpdateDTO dto) {
        YogaUser user = yogaUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());

        if (!user.getEmail().equals(dto.getEmail())) {
            if (yogaUserRepository.existsByEmail(dto.getEmail())) {
                throw new EmailAlreadyExistsException(dto.getEmail());
            }
            user.setEmail(dto.getEmail());
        }

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
                throw new InvalidPasswordException("Current password is incorrect.");
            }
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                throw new InvalidPasswordException("New passwords do not match.");
            }
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        yogaUserRepository.save(user);
        return user.getEmail();
    }

}

package com.example.demo.mappers;

import com.example.demo.domain.Role;
import com.example.demo.domain.YogaUser;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.YogaUserRegistrationDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class YogaUserMapper {

    private final PasswordEncoder passwordEncoder;

    public YogaUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public YogaUser toYogaUser(YogaUserRegistrationDTO user) {
        YogaUser yogaUser = new YogaUser();
        yogaUser.setFirstName(user.getFirstName());
        yogaUser.setLastName(user.getLastName());
        yogaUser.setEmail(user.getEmail());
        yogaUser.setAge(user.getAge());
        yogaUser.setGender(user.getGender());
        yogaUser.setPassword(passwordEncoder.encode(user.getPassword()));
        yogaUser.setRole(Role.ROLE_USER);
        return yogaUser;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(LoginDTO loginDTO) {
        return new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    }
}

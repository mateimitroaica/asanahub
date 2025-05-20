package com.example.demo.service;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaUser;
import com.example.demo.exceptions.YogaClassNotFoundException;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final YogaUserRepository yogaUserRepository;
    private final YogaClassRepository yogaClassRepository;

    public ReservationService(YogaUserRepository yogaUserRepository, YogaClassRepository yogaClassRepository) {
        this.yogaUserRepository = yogaUserRepository;
        this.yogaClassRepository = yogaClassRepository;
    }

    public boolean reserveClassForUser(String userEmail, Long classId) {
        YogaUser user = yogaUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        YogaClass yogaClass = yogaClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!user.getReservations().contains(yogaClass)) {
            user.getReservations().add(yogaClass);
            yogaUserRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean isUserEnrolledInClass(String email, Long classId) {
        return yogaUserRepository.findByEmail(email)
                .map(user -> user.getReservations().stream()
                        .anyMatch(c -> c.getId().equals(classId)))
                .orElse(false);
    }

    public void cancelReservation(String userEmail, Long classId) {
        YogaUser user = yogaUserRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        YogaClass yogaClass = yogaClassRepository.findById(classId).orElseThrow(
                () -> new YogaClassNotFoundException(classId)
        );

        user.getReservations().remove(yogaClass);
        yogaClass.getUsers().remove(user);

        yogaUserRepository.save(user);

    }


}

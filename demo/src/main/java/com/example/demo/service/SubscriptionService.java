package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.SubscriptionRepository;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final YogaUserRepository yogaUserRepository;
    private final YogaClassRepository yogaClassRepository;
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(YogaUserRepository yogaUserRepository, YogaClassRepository yogaClassRepository, SubscriptionRepository subscriptionRepository) {
        this.yogaUserRepository = yogaUserRepository;
        this.yogaClassRepository = yogaClassRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void createSubscription(String userEmail, SubscriptionType type, Long classId) {
        YogaUser user = yogaUserRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        YogaClass yogaClass = yogaClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Studio studio = yogaClass.getStudio();

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(type);

        if (type == SubscriptionType.STANDARD) {
            subscription.setPrice(50.0);
        } else if (type == SubscriptionType.PRO) {
            subscription.setPrice(90.0);
        }

        subscription.setStartDate(new Date());

        Calendar cal = Calendar.getInstance();
        cal.setTime(subscription.getStartDate());
        cal.add(Calendar.MONTH, 1);
        subscription.setEndDate(cal.getTime());

        subscription.setActive(true);
        subscription.setStudio(studio);
        subscription.setUser(user);

        subscriptionRepository.save(subscription);
    }

    public Optional<Subscription> getActiveSubscription(String email, Long studioId) {
        return yogaUserRepository.findByEmail(email)
                .flatMap(user -> user.getSubscriptions().stream()
                        .filter(s -> s.getStudio().getId().equals(studioId) && s.getActive())
                        .findFirst());
    }
}

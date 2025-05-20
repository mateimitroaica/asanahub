package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.exceptions.SubscriptionConflictException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.exceptions.YogaClassNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        YogaClass yogaClass = yogaClassRepository.findById(classId)
                .orElseThrow(() -> new YogaClassNotFoundException(classId));

        Studio studio = yogaClass.getStudio();

        Optional<Subscription> existing = user.getSubscriptions().stream()
                .filter(s -> s.getStudio().getId().equals(studio.getId()) && s.getActive())
                .findFirst();

        if (existing.isPresent()) {
            Subscription sub = existing.get();

            if (sub.getSubscriptionType() == SubscriptionType.PRO) {
                throw new SubscriptionConflictException("You already have a PRO subscription for this studio.");
            }

            if (sub.getSubscriptionType() == SubscriptionType.STANDARD && type == SubscriptionType.PRO) {
                sub.setSubscriptionType(SubscriptionType.PRO);
                sub.setPrice(90.0);
                subscriptionRepository.save(sub);
                return;
            }

            throw new SubscriptionConflictException("You already have an active subscription for this studio.");
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(type);
        subscription.setPrice(type == SubscriptionType.PRO ? 90.0 : 50.0);

        Date startDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.MONTH, 1);

        subscription.setStartDate(startDate);
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

package com.example.demo.controller;

import com.example.demo.domain.YogaUser;
import com.example.demo.repository.YogaUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ProfileController {

    private final YogaUserRepository yogaUserRepository;

    public ProfileController(YogaUserRepository yogaUserRepository) {
        this.yogaUserRepository = yogaUserRepository;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal,
                              @RequestParam(value = "highlight", required = false) String highlight) {
        String email = principal.getName();

        YogaUser user = yogaUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        model.addAttribute("subscriptions", user.getSubscriptions());
        model.addAttribute("reservations", user.getReservations());
        model.addAttribute("highlight", highlight);

        return "user-profile";
    }
}
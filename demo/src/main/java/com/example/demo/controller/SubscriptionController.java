package com.example.demo.controller;

import com.example.demo.domain.Studio;
import com.example.demo.domain.Subscription;
import com.example.demo.domain.SubscriptionType;
import com.example.demo.domain.YogaClass;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.service.SubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final YogaClassRepository yogaClassRepository;

    public SubscriptionController(SubscriptionService subscriptionService, YogaClassRepository yogaClassRepository) {
        this.subscriptionService = subscriptionService;
        this.yogaClassRepository = yogaClassRepository;
    }

    @GetMapping("/subscription/new")
    public String showSubscriptionForm(@RequestParam Long classId, Model model, Principal principal) {
        YogaClass yogaClass = yogaClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Studio studio = yogaClass.getStudio();
        model.addAttribute("classId", classId);
        model.addAttribute("studio", studio);

        if (principal != null) {
            Optional<Subscription> activeSub = subscriptionService.getActiveSubscription(principal.getName(), studio.getId());

            if (activeSub.isPresent()) {
                Subscription current = activeSub.get();
                model.addAttribute("currentSubscription", current);

                if (current.getSubscriptionType() == SubscriptionType.STANDARD) {
                    model.addAttribute("upgradeAvailable", true);
                    model.addAttribute("subscriptionTypes", List.of(SubscriptionType.PRO));
                } else {
                    model.addAttribute("upgradeAvailable", false);
                    model.addAttribute("subscriptionTypes", List.of());
                }
            } else {
                model.addAttribute("subscriptionTypes", List.of(SubscriptionType.STANDARD, SubscriptionType.PRO));
            }
        } else {
            model.addAttribute("subscriptionTypes", List.of(SubscriptionType.STANDARD, SubscriptionType.PRO));
        }

        return "subscription-form";
    }

    @PostMapping("/subscription/new")
    public String createSubscription(@RequestParam SubscriptionType type,
                                     @RequestParam Long classId,
                                     Principal principal,
                                     RedirectAttributes redirectAttributes) {

        subscriptionService.createSubscription(principal.getName(), type, classId);
        redirectAttributes.addFlashAttribute("success", "Subscription created successfully!");
        return "redirect:/profile?highlight=subs";
    }
}

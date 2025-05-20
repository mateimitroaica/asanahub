package com.example.demo.controller;

import com.example.demo.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/enroll")
    public String enrollUserInClass(@RequestParam Long classId, Principal principal, RedirectAttributes redirectAttributes) {
        String email = principal.getName();

        boolean success = reservationService.reserveClassForUser(email, classId);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Successfully reserved a spot!");
        } else {
            redirectAttributes.addFlashAttribute("info", "You are already enrolled in this class.");
        }

        return "redirect:/profile?highlight=res";
    }

    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam("classId") Long classId, Principal principal) {
        String userEmail = principal.getName();
        reservationService.cancelReservation(userEmail, classId);
        return "redirect:/profile?highlight=res";
    }
}

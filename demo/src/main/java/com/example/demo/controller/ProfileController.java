package com.example.demo.controller;

import com.example.demo.domain.YogaUser;
import com.example.demo.dto.UserUpdateDTO;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.YogaUserRepository;
import com.example.demo.service.YogaUserDetailsService;
import com.example.demo.service.YogaUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    private final YogaUserRepository yogaUserRepository;
    private final YogaUserService yogaUserService;
    private final YogaUserDetailsService yogaUserDetailsService;

    public ProfileController(YogaUserRepository yogaUserRepository,
                             YogaUserService yogaUserService,
                             YogaUserDetailsService yogaUserDetailsService) {
        this.yogaUserRepository = yogaUserRepository;
        this.yogaUserService = yogaUserService;
        this.yogaUserDetailsService = yogaUserDetailsService;
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

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model, Principal principal) {
        String email = principal.getName();
        YogaUser user = yogaUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setEmail(user.getEmail());

        model.addAttribute("userUpdate", dto);
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("userUpdate") UserUpdateDTO dto,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        String oldEmail = principal.getName();
        String newEmail = yogaUserService.updateUserProfile(oldEmail, dto);

        UserDetails updatedUserDetails = yogaUserDetailsService.loadUserByUsername(newEmail);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");
        return "redirect:/profile";
    }
}
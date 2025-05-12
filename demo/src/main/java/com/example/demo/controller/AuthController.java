package com.example.demo.controller;

import com.example.demo.dto.YogaUserRegistrationDTO;
import com.example.demo.service.YogaUserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final YogaUserService yogaUserService;
    private final AuthenticationManager authenticationManager;

    public AuthController(YogaUserService yogaUserService, AuthenticationManager authenticationManager) {
        this.yogaUserService = yogaUserService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                                Model model) {
        if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("yogaUser", new YogaUserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("yogaUser") YogaUserRegistrationDTO dto,
                               BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("passwordMismatch", "Passwords do not match.");
            return "register";
        }

        boolean created = yogaUserService.registerNewUser(dto);

        if (!created) {
            model.addAttribute("emailExists", "Email is already in use.");
            return "register";
        }

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());

            return "redirect:/yoga-classes";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "redirect:/login?error";
        }

//        Authentication authentication = authenticationManager.authenticate(
//          new UsernamePasswordAuthenticationToken(
//                  dto.getEmail(), dto.getPassword()
//          )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return "redirect:/yoga-classes";
    }
}

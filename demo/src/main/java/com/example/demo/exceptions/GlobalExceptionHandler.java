package com.example.demo.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ioException, Model model) {
        model.addAttribute("error", ioException.getMessage());
        return "error-page";
    }

    @ExceptionHandler(YogaClassNotFoundException.class)
    public String handleYogaClassNotFound(YogaClassNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/yoga-class-not-found";
    }

    @ExceptionHandler(SubscriptionConflictException.class)
    public String handleSubscriptionConflict(SubscriptionConflictException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/yoga-classes";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailConflict(EmailAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/profile/edit";
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public String handleInvalidPassword(InvalidPasswordException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/profile/edit";
    }
}

package com.example.demo.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ioException, Model model) {
        model.addAttribute("error", ioException.getMessage());
        return "error-page";
    }
}

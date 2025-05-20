package com.example.demo.exceptions;

public class YogaClassNotFoundException extends RuntimeException {
    public YogaClassNotFoundException(Long id) {
        super("Yoga class with ID " + id + " not found.");
    }
}

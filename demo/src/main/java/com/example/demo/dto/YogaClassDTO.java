package com.example.demo.dto;

import com.example.demo.domain.YogaClassType;

import java.time.LocalDateTime;

public class YogaClassDTO {
    private String name;
    private LocalDateTime timeAndDate;
    private double price;
    private YogaClassType type;
    private YogaInstructorDTO instructor;

    public YogaClassDTO() {}

    public YogaClassDTO(String name, LocalDateTime timeAndDate, double price, YogaClassType type, YogaInstructorDTO instructor) {
        this.name = name;
        this.timeAndDate = timeAndDate;
        this.price = price;
        this.type = type;
        this.instructor = instructor;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeAndDate() {
        return timeAndDate;
    }

    public double getPrice() {
        return price;
    }

    public YogaClassType getType() {
        return type;
    }

    public YogaInstructorDTO getInstructor() {
        return instructor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeAndDate(LocalDateTime timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(YogaClassType type) {
        this.type = type;
    }

    public void setInstructor(YogaInstructorDTO instructor) {
        this.instructor = instructor;
    }
}

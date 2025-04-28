package com.example.demo.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.YogaClassType;

import java.time.LocalDateTime;

public class YogaClassDTO {
    private String studioName;
    private String studioLocation;
    private String name;
    private LocalDateTime timeAndDate;
    private double price;
    private YogaClassType type;
    private String instructorFirstName;
    private String instructorLastName;
    private Integer instructorAge;
    private Gender instructorGender;
    private String formattedTimeAndDate;


    public YogaClassDTO() {}

    public YogaClassDTO(String studioName, String studioLocation, String name, LocalDateTime timeAndDate, double price, YogaClassType type, String instructorFirstName, String instructorLastName, Integer instructorAge, Gender instructorGender) {
        this.studioName = studioName;
        this.studioLocation = studioLocation;
        this.name = name;
        this.timeAndDate = timeAndDate;
        this.price = price;
        this.type = type;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.instructorAge = instructorAge;
        this.instructorGender = instructorGender;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeAndDate(LocalDateTime timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public YogaClassType getType() {
        return type;
    }

    public void setType(YogaClassType type) {
        this.type = type;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    public String getStudioLocation() {
        return studioLocation;
    }

    public void setStudioLocation(String studioLocation) {
        this.studioLocation = studioLocation;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public Integer getInstructorAge() {
        return instructorAge;
    }

    public void setInstructorAge(Integer instructorAge) {
        this.instructorAge = instructorAge;
    }

    public Gender getInstructorGender() {
        return instructorGender;
    }

    public void setInstructorGender(Gender instructorGender) {
        this.instructorGender = instructorGender;
    }

    public String getFormattedTimeAndDate() {
        return formattedTimeAndDate;
    }

    public void setFormattedTimeAndDate(String formattedTimeAndDate) {
        this.formattedTimeAndDate = formattedTimeAndDate;
    }
}

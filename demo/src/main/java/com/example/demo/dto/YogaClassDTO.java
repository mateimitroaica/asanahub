package com.example.demo.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.YogaClassType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class YogaClassDTO {
    @NotBlank(message = "Studio name is required.")
    private String studioName;
    private String studioLocation;
    private MultipartFile file;
    @NotBlank(message = "Class name is required.")
    private String name;
    @NotNull(message = "Date and time are required.")
    private LocalDateTime timeAndDate;
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "1.0", message = "Price must be greater than 0.")
    private double price;
    @NotNull(message = "Yoga class type is required.")
    private YogaClassType type;
    @NotBlank(message = "Instructor first name is required.")
    private String instructorFirstName;
    @NotBlank(message = "Instructor last name is required.")
    private String instructorLastName;
    @Min(value = 0, message = "Instructor age must be a positive number.")
    private Integer instructorAge;
    private Gender instructorGender;
    private String formattedTimeAndDate;
    private String studioPhotoPath;

    public YogaClassDTO() {}

    public YogaClassDTO(String studioName, String studioLocation, String name, LocalDateTime timeAndDate, double price, YogaClassType type, String instructorFirstName, String instructorLastName, Integer instructorAge, Gender instructorGender, MultipartFile file, String studioPhotoPath) {
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
        this.file = file;
        this.studioPhotoPath = studioPhotoPath;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getStudioPhotoPath() {
        return studioPhotoPath;
    }

    public void setStudioPhotoPath(String studioPhotoPath) {
        this.studioPhotoPath = studioPhotoPath;
    }
}

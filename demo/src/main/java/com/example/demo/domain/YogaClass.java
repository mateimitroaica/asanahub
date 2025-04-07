package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class YogaClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime timeAndDate;

    private double price;

    @OneToOne
    private YogaStyle yogaStyle;

    @ManyToOne
    private Studio studio;

    @ManyToOne
    private YogaInstructor instructor;

    @ManyToMany(mappedBy = "reservations")
    private List<YogaUser> users;

    public YogaClass() {}

    public YogaClass(Long id, String name, LocalDateTime timeAndDate, double price, YogaStyle yogaStyle, Studio studio, YogaInstructor instructor, List<YogaUser> users) {
        this.id = id;
        this.name = name;
        this.timeAndDate = timeAndDate;
        this.price = price;
        this.yogaStyle = yogaStyle;
        this.studio = studio;
        this.instructor = instructor;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(LocalDateTime timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public YogaStyle getYogaStyle() {
        return yogaStyle;
    }

    public void setYogaStyle(YogaStyle yogaStyle) {
        this.yogaStyle = yogaStyle;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public YogaInstructor getInstructor() {
        return instructor;
    }

    public void setInstructor(YogaInstructor instructor) {
        this.instructor = instructor;
    }

    public List<YogaUser> getUsers() {
        return users;
    }

    public void setUsers(List<YogaUser> users) {
        this.users = users;
    }
}

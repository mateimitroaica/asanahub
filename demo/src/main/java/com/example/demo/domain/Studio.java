package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YogaClass> yogaClasses;

    public Studio() {}

    public Studio(Long id, String name, String location, List<Subscription> subscriptions, List<YogaClass> yogaClasses) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.subscriptions = subscriptions;
        this.yogaClasses = yogaClasses;
    }
}

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<YogaClass> getYogaClasses() {
        return yogaClasses;
    }

    public void setYogaClasses(List<YogaClass> yogaClasses) {
        this.yogaClasses = yogaClasses;
    }
}

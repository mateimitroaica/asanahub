package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @ManyToOne
    private Studio studio;

    @ManyToOne
    private YogaUser user;

    public Subscription() {}

    public Subscription(Long id, Double price, Date startDate, Date endDate, Boolean isActive, SubscriptionType subscriptionType, Studio studio, YogaUser user) {
        this.id = id;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.subscriptionType = subscriptionType;
        this.studio = studio;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public YogaUser getUser() {
        return user;
    }

    public void setUser(YogaUser user) {
        this.user = user;
    }
}

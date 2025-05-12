package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class YogaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions;

    @ManyToMany
    @JoinTable(
            name = "reservations",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "yoga_class_id", referencedColumnName = "id")
    )
    private List<YogaClass> reservations;

    public YogaUser(){}

    public YogaUser(Long id, String firstName, String lastName, Integer age, String email, String password, Gender gender, List<Subscription> subscriptions, List<YogaClass> reservations, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.subscriptions = subscriptions;
        this.reservations = reservations;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<YogaClass> getReservations() {
        return reservations;
    }

    public void setReservations(List<YogaClass> reservations) {
        this.reservations = reservations;
    }

    public void removeClass(YogaClass yogaClass) {
        this.reservations.remove(yogaClass);
        yogaClass.getUsers().remove(this);
    }

    public void removeSubscription(Subscription subscription) {
        subscriptions.remove(subscription);
        this.subscriptions.remove(subscription);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

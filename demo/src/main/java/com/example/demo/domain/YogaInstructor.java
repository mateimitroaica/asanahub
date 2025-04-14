package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class YogaInstructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YogaClass> classes;

    public YogaInstructor() {}

    public YogaInstructor(Long id, String firstName, String lastName, Integer age, Gender gender, List<YogaClass> classes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.classes = classes;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<YogaClass> getClasses() {
        return classes;
    }

    public void setClasses(List<YogaClass> classes) {
        this.classes = classes;
    }

    public void removeYogaClass(YogaClass yogaClass) {
        yogaClass.setInstructor(null);
        this.classes.remove(yogaClass);
    }
}

package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
public class YogaStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private YogaClassType classType;

    @OneToOne(mappedBy = "yogaStyle")
    private YogaClass yogaClass;

    public YogaStyle() {}

    public YogaStyle(Long id, YogaClassType classType, YogaClass yogaClass) {
        this.id = id;
        this.classType = classType;
        this.yogaClass = yogaClass;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YogaClassType getClassType() {
        return classType;
    }

    public void setClassType(YogaClassType classType) {
        this.classType = classType;
    }

    public YogaClass getYogaClass() {
        return yogaClass;
    }

    public void setYogaClass(YogaClass yogaClass) {
        this.yogaClass = yogaClass;
    }
}

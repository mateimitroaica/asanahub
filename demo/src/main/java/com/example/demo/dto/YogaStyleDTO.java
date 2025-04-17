package com.example.demo.dto;

import com.example.demo.domain.YogaClassType;

public class YogaStyleDTO {
    private YogaClassType classType;

    public YogaStyleDTO() {}

    public YogaStyleDTO(YogaClassType classType) {
        this.classType = classType;
    }

    public YogaClassType getClassType() {
        return classType;
    }

    public void setClassType(YogaClassType classType) {
        this.classType = classType;
    }
}

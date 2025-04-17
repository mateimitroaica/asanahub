package com.example.demo.dto;

import java.util.List;

public class StudioDTO {
    private String name;
    private String location;
    private List<YogaClassDTO> yogaClasses;

    public StudioDTO() {}

    public StudioDTO(String name, String location, List<YogaClassDTO> yogaClasses) {
        this.name = name;
        this.location = location;
        this.yogaClasses = yogaClasses;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<YogaClassDTO> getYogaClasses() {
        return yogaClasses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setYogaClasses(List<YogaClassDTO> yogaClasses) {
        this.yogaClasses = yogaClasses;
    }
}

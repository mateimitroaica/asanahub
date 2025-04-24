package com.example.demo.mappers;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaClassDTO;
import org.springframework.stereotype.Component;

@Component
public class YogaClassMapper {

    public static YogaClass toEntity(YogaClassDTO dto) {
        if (dto == null) return null;

        YogaClass yogaClass = new YogaClass();
        yogaClass.setName(dto.getName());
        yogaClass.setTimeAndDate(dto.getTimeAndDate());
        yogaClass.setPrice(dto.getPrice());

        YogaStyle style = new YogaStyle();
        style.setClassType(dto.getType());
        yogaClass.setYogaStyle(style);

        Studio studio = new Studio();
        studio.setName(dto.getStudioName());
        studio.setLocation(dto.getStudioLocation());
        yogaClass.setStudio(studio);

        YogaInstructor instructor = new YogaInstructor();
        instructor.setFirstName(dto.getInstructorFirstName());
        instructor.setLastName(dto.getInstructorLastName());
        instructor.setAge(dto.getInstructorAge());
        instructor.setGender(dto.getInstructorGender());
        yogaClass.setInstructor(instructor);

        return yogaClass;
    }
}

package com.example.demo.mappers;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaClassDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class YogaClassMapper {
    public static YogaClassDTO toDTO(YogaClass yogaClass) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        YogaClassDTO dto = new YogaClassDTO();
        dto.setStudioName(yogaClass.getStudio().getName());
        dto.setStudioLocation(yogaClass.getStudio().getLocation());
        dto.setName(yogaClass.getName());
        dto.setTimeAndDate(yogaClass.getTimeAndDate());
        dto.setPrice(yogaClass.getPrice());
        dto.setType(yogaClass.getYogaStyle().getClassType());
        dto.setInstructorFirstName(yogaClass.getInstructor().getFirstName());
        dto.setInstructorLastName(yogaClass.getInstructor().getLastName());
        dto.setInstructorAge(yogaClass.getInstructor().getAge());
        dto.setInstructorGender(yogaClass.getInstructor().getGender());
        dto.setStudioPhotoPath(yogaClass.getStudio().getPhotoPath());

        dto.setFormattedTimeAndDate(yogaClass.getTimeAndDate().format(formatter));
        return dto;
    }

    public static YogaClass toEntity(YogaClassDTO dto) {
        if (dto == null) return null;

        YogaClass yogaClass = new YogaClass();
        yogaClass.setName(dto.getName());
        yogaClass.setTimeAndDate(dto.getTimeAndDate());
        yogaClass.setPrice(dto.getPrice());

        return yogaClass;
    }

    public static Studio toStudio(YogaClassDTO dto) {
        if (dto == null) return null;

        Studio studio = new Studio();
        studio.setName(dto.getStudioName());
        studio.setLocation(dto.getStudioLocation());
        studio.setYogaClasses(new ArrayList<>());

        return studio;
    }

    public static YogaInstructor toYogaInstructor(YogaClassDTO dto) {
        if (dto == null) return null;

        YogaInstructor instructor = new YogaInstructor();
        instructor.setFirstName(dto.getInstructorFirstName());
        instructor.setLastName(dto.getInstructorLastName());
        instructor.setAge(dto.getInstructorAge());
        instructor.setGender(dto.getInstructorGender());
        instructor.setClasses(new ArrayList<>());

        return instructor;
    }

    public static YogaStyle toYogaStyle(YogaClassDTO dto) {
        if (dto == null) return null;

        YogaStyle style = new YogaStyle();
        style.setClassType(dto.getType());

        return style;
    }
}

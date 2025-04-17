package com.example.demo.mappers;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.dto.YogaStyleDTO;
import org.springframework.stereotype.Component;

@Component
public class YogaClassMapper {
    public static YogaClassDTO toDto(YogaClass yogaClass) {
        return new YogaClassDTO(
                yogaClass.getName(),
                yogaClass.getTimeAndDate(),
                yogaClass.getPrice(),
                yogaClass.getYogaStyle().getClassType(),
                YogaInstructorMapper.toDTO(yogaClass.getInstructor())
        );
    }

    public static YogaClass toEntity(YogaClassDTO yogaClassDTO) {
        YogaClass yogaClass = new YogaClass();
        yogaClass.setName(yogaClassDTO.getName());
        yogaClass.setTimeAndDate(yogaClassDTO.getTimeAndDate());
        yogaClass.setPrice(yogaClassDTO.getPrice());
        yogaClass.setYogaStyle(YogaStyleMapper.fromClassToStyle(yogaClassDTO.getType()));
        yogaClass.setInstructor(YogaInstructorMapper.toEntity(yogaClassDTO.getInstructor()));
        return yogaClass;
    }
}

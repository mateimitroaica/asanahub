package com.example.demo.mappers;

import com.example.demo.domain.Gender;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.dto.YogaInstructorDTO;
import org.springframework.stereotype.Component;

@Component
public class YogaInstructorMapper {
    public static YogaInstructorDTO toDTO(YogaInstructor yogaInstructor) {
        String firstName = yogaInstructor.getFirstName();
        String lastName = yogaInstructor.getLastName();
        Integer age = yogaInstructor.getAge();
        Gender gender = yogaInstructor.getGender();
        return new YogaInstructorDTO(firstName, lastName, age, gender);
    }
    public static YogaInstructor toEntity(YogaInstructorDTO yogaInstructorDTO) {
        YogaInstructor yogaInstructor = new YogaInstructor();
        yogaInstructor.setFirstName(yogaInstructorDTO.getFirstName());
        yogaInstructor.setLastName(yogaInstructorDTO.getLastName());
        yogaInstructor.setAge(yogaInstructorDTO.getAge());
        yogaInstructor.setGender(yogaInstructorDTO.getGender());
        return yogaInstructor;
    }
}

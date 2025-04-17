package com.example.demo.service;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.StudioDTO;
import com.example.demo.mappers.StudioMapper;
import com.example.demo.repository.YogaInstructorRepository;
import com.example.demo.repository.YogaStudioRepository;
import com.example.demo.repository.YogaStyleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioService {

    private final YogaStudioRepository yogaStudioRepository;
    private final StudioMapper studioMapper;
    private final YogaStyleRepository yogaStyleRepository;
    private final YogaInstructorRepository yogaInstructorRepository;

    public StudioService(YogaStudioRepository yogaStudioRepository,
                         StudioMapper studioMapper,
                         YogaStyleRepository yogaStyleRepository,
                         YogaInstructorRepository yogaInstructorRepository) {
        this.yogaStudioRepository = yogaStudioRepository;
        this.studioMapper = studioMapper;
        this.yogaStyleRepository = yogaStyleRepository;
        this.yogaInstructorRepository = yogaInstructorRepository;
    }

    public Studio saveStudio(StudioDTO studioDTO) {
        Studio studio = studioMapper.toEntity(studioDTO);
        List<YogaClass> yogaClassList = studio.getYogaClasses();

        for (YogaClass yogaClass : yogaClassList) {
                YogaStyle yogaStyle = yogaClass.getYogaStyle();
                YogaStyle savedYogaStyle = yogaStyleRepository.save(yogaStyle);
                savedYogaStyle.setYogaClass(yogaClass);
                yogaClass.setYogaStyle(savedYogaStyle);
        }

        for (YogaClass yogaClass : yogaClassList) {
            YogaInstructor yogaInstructor = yogaClass.getInstructor();
            if (yogaInstructorRepository.findYogaInstructorByFirstNameAndLastName(
                    yogaInstructor.getFirstName(), yogaInstructor.getLastName()
            ).isPresent()) {
                YogaInstructor instructor = yogaInstructorRepository.findYogaInstructorByFirstNameAndLastName(yogaInstructor.getFirstName(), yogaInstructor.getLastName()).get();
                instructor.getClasses().add(yogaClass);
                yogaClass.setInstructor(instructor);
            } else {
                YogaInstructor savedYogaInstructor = yogaInstructorRepository.save(yogaInstructor);
                savedYogaInstructor.getClasses().add(yogaClass);
                yogaClass.setInstructor(savedYogaInstructor);
            }
        }

        return yogaStudioRepository.save(studio);
    }
}

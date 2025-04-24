package com.example.demo.service;

import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.mappers.YogaClassMapper;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaInstructorRepository;
import com.example.demo.repository.YogaStudioRepository;
import com.example.demo.repository.YogaStyleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class YogaClassService {
    private final YogaClassRepository yogaClassRepository;
    private final YogaStudioRepository yogaStudioRepository;
    private final YogaInstructorRepository yogaInstructorRepository;
    private final YogaStyleRepository yogaStyleRepository;

    public YogaClassService(YogaClassRepository yogaClassRepository,
                            YogaStudioRepository yogaStudioRepository,
                            YogaInstructorRepository yogaInstructorRepository,
                            YogaStyleRepository yogaStyleRepository) {
        this.yogaClassRepository = yogaClassRepository;
        this.yogaStudioRepository = yogaStudioRepository;
        this.yogaInstructorRepository = yogaInstructorRepository;
        this.yogaStyleRepository = yogaStyleRepository;
    }

    @Transactional
    public YogaClass saveYogaClass(YogaClassDTO yogaClassDTO) {
        YogaClass yogaClass = YogaClassMapper.toEntity(yogaClassDTO);

        Studio studio;
        Optional<Studio> existingStudio = yogaStudioRepository
                .findByName(yogaClassDTO.getStudioName());

        if (existingStudio.isPresent()) {
            studio = existingStudio.get();
        } else {
            studio = YogaClassMapper.toStudio(yogaClassDTO);
            studio = yogaStudioRepository.save(studio);
        }
        yogaClass.setStudio(studio);
        studio.getYogaClasses().add(yogaClass);

        YogaInstructor instructor;
        Optional<YogaInstructor> existing = yogaInstructorRepository
                .findYogaInstructorByFirstNameAndLastName(yogaClassDTO.getInstructorFirstName(), yogaClassDTO.getInstructorLastName());

        if (existing.isPresent()) {
            instructor = existing.get();
        } else {
            instructor = YogaClassMapper.toYogaInstructor(yogaClassDTO);
            instructor = yogaInstructorRepository.save(instructor);
        }
        yogaClass.setInstructor(instructor);
        instructor.getClasses().add(yogaClass);

        YogaStyle style = YogaClassMapper.toYogaStyle(yogaClassDTO);

        YogaClass savedClass = yogaClassRepository.save(yogaClass);

        style.setYogaClass(savedClass);
        style = yogaStyleRepository.save(style);
        savedClass.setYogaStyle(style);

        return yogaClassRepository.save(savedClass);

    }

}

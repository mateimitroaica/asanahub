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

        YogaStyle yogaStyle = yogaClass.getYogaStyle();
        yogaStyle.setYogaClass(yogaClass);
        yogaStyle = yogaStyleRepository.save(yogaStyle);

        yogaClass.setYogaStyle(yogaStyle);

        Optional<Studio> optionalStudio = yogaStudioRepository.findByName(yogaClassDTO.getStudioName());
        Studio studio;
        if (optionalStudio.isPresent()) {
            studio = optionalStudio.get();
        } else {
            studio = yogaClass.getStudio();
            studio.setYogaClasses(new ArrayList<>());
        }
        yogaClass.setStudio(studio);
        studio.getYogaClasses().add(yogaClass);

        Optional<YogaInstructor> optionalYogaInstructor = yogaInstructorRepository
                .findYogaInstructorByFirstNameAndLastName(yogaClassDTO.getInstructorFirstName(),
                        yogaClassDTO.getInstructorLastName());
        YogaInstructor yogaInstructor;
        if (optionalYogaInstructor.isPresent()) {
            yogaInstructor = optionalYogaInstructor.get();
        } else {
            yogaInstructor = yogaClass.getInstructor();
            yogaInstructor.setClasses(new ArrayList<>());
        }
        yogaClass.setInstructor(yogaInstructor);
        yogaInstructor.getClasses().add(yogaClass);

        yogaStudioRepository.save(studio);
        yogaInstructorRepository.save(yogaInstructor);

        return yogaClassRepository.save(yogaClass);
    }

}

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
import java.util.List;
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

//    @Transactional
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

    public List<YogaClass> findAllClasses() {
        return yogaClassRepository.findAll();
    }

    public YogaClass findYogaClassById(Long id) {
        return yogaClassRepository.findById(id).orElse(null);
    }

    public void deleteYogaClass(Long id) {
        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(id);
        if (optionalYogaClass.isPresent()){
            YogaClass yogaClass = optionalYogaClass.get();
            YogaStyle style = yogaClass.getYogaStyle();
            yogaClass.setYogaStyle(null);
            style.setYogaClass(null);
            yogaClassRepository.delete(yogaClass);
            yogaStyleRepository.delete(style);
        }
    }

    public YogaClass updateYogaClass(Long id, YogaClassDTO yogaClassDTO) {
        YogaClass yogaClass = yogaClassRepository.findById(id).orElseThrow(
                ()->new RuntimeException("YogaClass not found")
        );
        yogaClass.setName(yogaClassDTO.getName());
        yogaClass.setTimeAndDate(yogaClassDTO.getTimeAndDate());
        yogaClass.setPrice(yogaClassDTO.getPrice());

        YogaStyle style = yogaClass.getYogaStyle();
        if (style == null) {
            style = new YogaStyle();
            style.setClassType(yogaClassDTO.getType());
            style.setYogaClass(yogaClass);
            yogaStyleRepository.save(style);
            yogaClass.setYogaStyle(style);
        } else if (style.getClassType() != yogaClassDTO.getType()) {
            style.setClassType(yogaClassDTO.getType());
            style = yogaStyleRepository.save(style);
            yogaClass.setYogaStyle(style);
        }

        Studio studio = yogaStudioRepository.findByName(yogaClassDTO.getStudioName())
                .orElseGet(() -> {
                    Studio newStudio = new Studio();
                    newStudio.setName(yogaClassDTO.getStudioName());
                    newStudio.setLocation(yogaClassDTO.getStudioLocation());
                    return yogaStudioRepository.save(newStudio);
                });
        yogaClass.setStudio(studio);

        YogaInstructor instructor = yogaInstructorRepository.findYogaInstructorByFirstNameAndLastName(
                        yogaClassDTO.getInstructorFirstName(),
                        yogaClassDTO.getInstructorLastName())
                .orElseGet(() -> {
                    YogaInstructor newInstructor = new YogaInstructor();
                    newInstructor.setFirstName(yogaClassDTO.getInstructorFirstName());
                    newInstructor.setLastName(yogaClassDTO.getInstructorLastName());
                    newInstructor.setAge(yogaClassDTO.getInstructorAge());
                    newInstructor.setGender(yogaClassDTO.getInstructorGender());
                    return yogaInstructorRepository.save(newInstructor);
                });
        yogaClass.setInstructor(instructor);

        return yogaClassRepository.save(yogaClass);

    }

}
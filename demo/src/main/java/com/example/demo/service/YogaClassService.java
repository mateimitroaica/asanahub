package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.exceptions.YogaClassNotFoundException;
import com.example.demo.mappers.YogaClassMapper;
import com.example.demo.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class YogaClassService {
    private final YogaClassRepository yogaClassRepository;
    private final YogaStudioRepository yogaStudioRepository;
    private final YogaInstructorRepository yogaInstructorRepository;
    private final YogaStyleRepository yogaStyleRepository;
    private final YogaUserRepository yogaUserRepository;

    public YogaClassService(YogaClassRepository yogaClassRepository,
                            YogaStudioRepository yogaStudioRepository,
                            YogaInstructorRepository yogaInstructorRepository,
                            YogaStyleRepository yogaStyleRepository,
                            YogaUserRepository yogaUserRepository) {
        this.yogaClassRepository = yogaClassRepository;
        this.yogaStudioRepository = yogaStudioRepository;
        this.yogaInstructorRepository = yogaInstructorRepository;
        this.yogaStyleRepository = yogaStyleRepository;
        this.yogaUserRepository = yogaUserRepository;
    }

    public YogaClass saveYogaClass(YogaClassDTO yogaClassDTO) throws IOException {
        YogaClass yogaClass = YogaClassMapper.toEntity(yogaClassDTO);

        Studio studio;
        Optional<Studio> existingStudio = yogaStudioRepository
                .findByName(yogaClassDTO.getStudioName());

        if (existingStudio.isPresent()) {
            studio = existingStudio.get();
        } else {
            studio = YogaClassMapper.toStudio(yogaClassDTO);

            MultipartFile photo = yogaClassDTO.getFile();
            if (photo != null && !photo.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, photo.getBytes());

                studio.setPhotoPath("uploads/" + fileName);
            }

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
        return yogaClassRepository.findById(id).orElseThrow(
                () -> new YogaClassNotFoundException(id)
        );
    }

    public void deleteYogaClass(Long id) {
        YogaClass yogaClass = yogaClassRepository.findById(id)
                .orElseThrow(() -> new YogaClassNotFoundException(id));

        List<YogaUser> allUsers = yogaUserRepository.findAll();
        for (YogaUser user : allUsers) {
            user.getReservations().remove(yogaClass);
        }
        yogaUserRepository.saveAll(allUsers);

        YogaStyle style = yogaClass.getYogaStyle();
        if (style != null) {
            yogaClass.setYogaStyle(null);
            style.setYogaClass(null);
            yogaStyleRepository.save(style);
        }

        yogaClassRepository.delete(yogaClass);

        if (style != null) {
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
        studio.setLocation(yogaClassDTO.getStudioLocation());
        yogaClass.setStudio(studio);
        MultipartFile newFile = yogaClassDTO.getFile();
        if (newFile != null && !newFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + newFile.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, newFile.getBytes());

                studio.setPhotoPath("uploads/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save new photo", e);
            }
        }

        studio = yogaStudioRepository.save(studio);
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
        instructor.setAge(yogaClassDTO.getInstructorAge());
        instructor.setGender(yogaClassDTO.getInstructorGender());
        yogaClass.setInstructor(instructor);

        return yogaClassRepository.save(yogaClass);

    }


    public Page<YogaClass> getPaginatedClasses(int page, int size) {
        return yogaClassRepository.findAll(PageRequest.of(page, size));
    }

    public Page<YogaClass> searchPaginated(String query, int page, int size) {
        return yogaClassRepository.findByNameContainingIgnoreCase(query, PageRequest.of(page, size));
    }

    public Page<YogaClass> filterPaginated(YogaClassType style, LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (style != null && date != null) {
            return yogaClassRepository.findByYogaStyle_ClassTypeAndDate(style, date, pageable);
        } else if (style != null) {
            return yogaClassRepository.findByYogaStyle_ClassType(style, pageable);
        } else if (date != null) {
            return yogaClassRepository.findByDate(date, pageable);
        } else {
            return yogaClassRepository.findAll(pageable);
        }
    }



}
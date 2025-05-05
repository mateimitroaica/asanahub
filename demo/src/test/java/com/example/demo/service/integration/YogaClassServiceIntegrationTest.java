package com.example.demo.service.integration;

import com.example.demo.domain.*;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaInstructorRepository;
import com.example.demo.repository.YogaStudioRepository;
import com.example.demo.repository.YogaStyleRepository;
import com.example.demo.service.YogaClassService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class YogaClassServiceIntegrationTest {
    @Autowired
    private YogaClassService yogaClassService;

    @Autowired
    private YogaClassRepository yogaClassRepository;

    @Autowired
    private YogaStyleRepository yogaStyleRepository;

    @Autowired
    private YogaInstructorRepository yogaInstructorRepository;

    @Autowired
    private YogaStudioRepository yogaStudioRepository;

    @Test
    public void testSaveYogaClass() throws IOException {
        YogaClassDTO dto = new YogaClassDTO();
        dto.setName("Sunrise Power Flow");
        dto.setTimeAndDate(LocalDateTime.of(2025, 5, 5, 6, 30));
        dto.setPrice(30.0);
        dto.setType(YogaClassType.VINYASA);
        dto.setStudioName("Shanti Studio");
        dto.setStudioLocation("Austin");
        dto.setInstructorFirstName("Ava");
        dto.setInstructorLastName("Stone");
        dto.setInstructorAge(29);
        dto.setInstructorGender(Gender.FEMALE);

        YogaClass yogaClass = yogaClassService.saveYogaClass(dto);

        Studio studio = yogaStudioRepository.findById(yogaClass.getStudio().getId())
                .orElseThrow(() -> new AssertionError("Studio not found"));
        assertEquals("Shanti Studio", studio.getName());
        assertEquals(1, studio.getYogaClasses().size());

        YogaInstructor yogaInstructor = yogaInstructorRepository.findById(yogaClass.getInstructor().getId())
                        .orElseThrow(() -> new AssertionError("Instructor not found"));
        assertEquals("Ava", yogaInstructor.getFirstName());
        assertEquals(1, yogaInstructor.getClasses().size());

        YogaStyle yogaStyle = yogaStyleRepository.findById(yogaClass.getYogaStyle().getId())
                        .orElseThrow(() -> new AssertionError("Style not found"));
        assertEquals(YogaClassType.VINYASA, yogaStyle.getClassType());

        assertNotNull(yogaClass.getId());
        assertNotNull(yogaClass.getInstructor());
        assertEquals("Ava", yogaClass.getInstructor().getFirstName());
        assertEquals("Stone", yogaClass.getInstructor().getLastName());
        assertNotNull(yogaClass.getStudio());
        assertEquals("Shanti Studio", yogaClass.getStudio().getName());
        assertEquals("Austin", yogaClass.getStudio().getLocation());
        assertNotNull(yogaClass.getYogaStyle());
        assertEquals(YogaClassType.VINYASA, yogaClass.getYogaStyle().getClassType());
        assertTrue(yogaClass.getInstructor().getClasses().contains(yogaClass));
        assertTrue(yogaClass.getStudio().getYogaClasses().contains(yogaClass));
        assertEquals(yogaClass, yogaClass.getYogaStyle().getYogaClass());
    }

    @Test
    public void testDeleteYogaClass() {
        YogaClass yogaClass = new YogaClass();
        yogaClass.setName("Test Class");
        yogaClass.setTimeAndDate(LocalDateTime.now());
        yogaClass.setPrice(20.0);

        YogaStyle yogaStyle = new YogaStyle();
        yogaStyle.setClassType(YogaClassType.VINYASA);

        yogaClass.setYogaStyle(yogaStyle);
        yogaStyle.setYogaClass(yogaClass);

        yogaClass = yogaClassRepository.save(yogaClass);
        YogaStyle savedStyle = yogaStyleRepository.save(yogaStyle);

        Long yogaClassId = yogaClass.getId();
        Long yogaStyleId = savedStyle.getId();

        yogaClassService.deleteYogaClass(yogaClassId);

        assertFalse(yogaClassRepository.findById(yogaClassId).isPresent());
        assertFalse(yogaStyleRepository.findById(yogaStyleId).isPresent());
    }

    @Test
    public void testUpdateYogaClass() {

        YogaClassDTO dto = new YogaClassDTO();
        dto.setName("Updated Class Name");
        dto.setTimeAndDate(LocalDateTime.now().plusDays(1));
        dto.setPrice(45.0);
        dto.setType(YogaClassType.HATHA);
        dto.setStudioName("Updated Studio");
        dto.setStudioLocation("New Location");
        dto.setInstructorFirstName("Ava");
        dto.setInstructorLastName("Stone");
        dto.setInstructorAge(32);
        dto.setInstructorGender(Gender.FEMALE);

        YogaClass updated = yogaClassService.updateYogaClass(1L, dto);
        Studio studio = yogaStudioRepository.findByName(dto.getStudioName()).orElseThrow(() -> new AssertionError("Studio not found"));
        YogaInstructor instructor = yogaInstructorRepository.findYogaInstructorByFirstNameAndLastName(dto.getInstructorFirstName(), dto.getInstructorLastName()).orElseThrow(() -> new AssertionError("Instructor not found"));

        assertEquals("Updated Class Name", updated.getName());
        assertEquals(45.0, updated.getPrice());
        assertEquals("Updated Studio", updated.getStudio().getName());
        assertEquals("Ava", updated.getInstructor().getFirstName());
        assertEquals(YogaClassType.HATHA, updated.getYogaStyle().getClassType());
        assertEquals("Updated Studio", studio.getName());
        assertEquals("Ava", instructor.getFirstName());
    }
}

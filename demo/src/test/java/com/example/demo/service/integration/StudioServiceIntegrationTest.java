package com.example.demo.service.integration;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Studio;
import com.example.demo.domain.YogaClassType;
import com.example.demo.dto.StudioDTO;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.dto.YogaInstructorDTO;
import com.example.demo.dto.YogaStyleDTO;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaInstructorRepository;
import com.example.demo.repository.YogaStudioRepository;
import com.example.demo.repository.YogaStyleRepository;
import com.example.demo.service.StudioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class StudioServiceIntegrationTest {
    @Autowired
    private StudioService studioService;

    @Autowired
    private YogaStudioRepository yogaStudioRepository;

    @Autowired
    private YogaClassRepository yogaClassRepository;

    @Autowired
    private YogaInstructorRepository yogaInstructorRepository;

    @Autowired
    private YogaStyleRepository yogaStyleRepository;

    @Test
    void testSaveStudioWithNewData() {
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setName("Example Studio");
        studioDTO.setLocation("Example Location");

        YogaInstructorDTO yogaInstructorDTO = new YogaInstructorDTO();
        yogaInstructorDTO.setFirstName("First Name");
        yogaInstructorDTO.setLastName("Last Name");
        yogaInstructorDTO.setAge(30);
        yogaInstructorDTO.setGender(Gender.FEMALE);

        YogaClassDTO yogaClassDTO1 = new YogaClassDTO();
        yogaClassDTO1.setName("Yoga Class Name 1");
        yogaClassDTO1.setPrice(15.0);
        yogaClassDTO1.setTimeAndDate(LocalDateTime.now().plusDays(1));
        yogaClassDTO1.setType(YogaClassType.KUNDALINI);
        yogaClassDTO1.setInstructor(yogaInstructorDTO);

        YogaClassDTO yogaClassDTO2 = new YogaClassDTO();
        yogaClassDTO2.setName("Yoga Class Name 2");
        yogaClassDTO2.setPrice(25.0);
        yogaClassDTO2.setTimeAndDate(LocalDateTime.now().plusDays(1));
        yogaClassDTO2.setType(YogaClassType.PRENATAL);
        yogaClassDTO2.setInstructor(yogaInstructorDTO);

        studioDTO.setYogaClasses(List.of(yogaClassDTO1, yogaClassDTO2));

        Studio savedStudio = studioService.saveStudio(studioDTO);

        assertNotNull(savedStudio.getId());

    }
}

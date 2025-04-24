package com.example.demo.service.integration;

import com.example.demo.domain.Gender;
import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaClassType;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void testSaveYogaClass() {
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

        assertNotNull(yogaClass.getId());
    }
}

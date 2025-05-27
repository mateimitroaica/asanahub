package com.example.demo.service.unit;

import com.example.demo.domain.*;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.repository.*;
import com.example.demo.service.YogaClassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YogaClassServiceUnitTest {

    @Mock
    private YogaClassRepository yogaClassRepository;

    @Mock
    private YogaStyleRepository yogaStyleRepository;

    @Mock
    private YogaStudioRepository yogaStudioRepository;

    @Mock
    private YogaInstructorRepository yogaInstructorRepository;

    @Mock
    private YogaUserRepository yogaUserRepository;


    @InjectMocks
    private YogaClassService yogaClassService;

    @Test
    public void findClasses() {
        List<YogaClass> yogaClasses = new ArrayList<>();
        YogaClass yogaClass = new YogaClass();
        yogaClasses.add(yogaClass);

        when(yogaClassRepository.findAll()).thenReturn(yogaClasses);
        List<YogaClass> result = yogaClassService.findAllClasses();
        assertEquals(1,result.size());
    }

    @Test
    public void deleteClass() {
        YogaClass yogaClass = new YogaClass();
        YogaStyle yogaStyle = new YogaStyle();
        yogaClass.setYogaStyle(yogaStyle);

        when(yogaClassRepository.findById(1L)).thenReturn(Optional.of(yogaClass));

        yogaClassService.deleteYogaClass(1L);

        verify(yogaClassRepository).delete(yogaClass);
        verify(yogaStyleRepository).delete(yogaStyle);
    }


    @Test
    public void updateClass() throws IOException {
        YogaClass existingClass = new YogaClass();
        existingClass.setId(1L);
        existingClass.setName("Old Class");

        YogaClassDTO dto = new YogaClassDTO();
        dto.setName("Updated Class");
        dto.setTimeAndDate(LocalDateTime.now());
        dto.setPrice(30.0);
        dto.setType(YogaClassType.PRENATAL);
        dto.setStudioName("Studio A");
        dto.setStudioLocation("Downtown");
        dto.setInstructorFirstName("Alice");
        dto.setInstructorLastName("Smith");
        dto.setInstructorAge(35);
        dto.setInstructorGender(Gender.FEMALE);

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(true);
        dto.setFile(mockFile);

        Studio studio = new Studio();
        studio.setName("Studio A");
        studio.setLocation("Old Location");

        YogaInstructor newInstructor = new YogaInstructor();
        newInstructor.setFirstName("Alice");
        newInstructor.setLastName("Smith");
        newInstructor.setAge(35);
        newInstructor.setGender(Gender.FEMALE);

        YogaStyle newStyle = new YogaStyle();
        newStyle.setClassType(YogaClassType.PRENATAL);
        newStyle.setYogaClass(existingClass);

        when(yogaClassRepository.findById(1L)).thenReturn(Optional.of(existingClass));
        when(yogaStudioRepository.findByName("Studio A")).thenReturn(Optional.of(studio));
        when(yogaInstructorRepository.findYogaInstructorByFirstNameAndLastName("Alice", "Smith"))
                .thenReturn(Optional.empty());
        when(yogaInstructorRepository.save(any())).thenReturn(newInstructor);
        when(yogaStyleRepository.save(any())).thenReturn(newStyle);
        when(yogaStudioRepository.save(any())).thenReturn(studio);
        when(yogaClassRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        YogaClass result = yogaClassService.updateYogaClass(1L, dto);

        assertEquals("Updated Class", result.getName());
        assertEquals(30.0, result.getPrice());
        assertNotNull(result.getYogaStyle());
        assertEquals(YogaClassType.PRENATAL, result.getYogaStyle().getClassType());
        assertEquals("Downtown", result.getStudio().getLocation());
        assertEquals("Alice", result.getInstructor().getFirstName());
        assertEquals(35, result.getInstructor().getAge());

        verify(yogaClassRepository).save(existingClass);
        verify(yogaStyleRepository).save(any());
        verify(yogaStudioRepository).findByName("Studio A");
        verify(yogaStudioRepository).save(studio);
        verify(yogaInstructorRepository).findYogaInstructorByFirstNameAndLastName("Alice", "Smith");
        verify(yogaInstructorRepository).save(any());
    }



}

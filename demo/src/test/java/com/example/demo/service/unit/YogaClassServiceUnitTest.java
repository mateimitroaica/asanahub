package com.example.demo.service.unit;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaClassType;
import com.example.demo.domain.YogaInstructor;
import com.example.demo.domain.YogaStyle;
import com.example.demo.dto.YogaClassDTO;
import com.example.demo.repository.YogaClassRepository;
import com.example.demo.repository.YogaInstructorRepository;
import com.example.demo.repository.YogaStudioRepository;
import com.example.demo.repository.YogaStyleRepository;
import com.example.demo.service.YogaClassService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void updateClass() {
        YogaClass existingClass = new YogaClass();
        existingClass.setName("test");

        YogaClassDTO yogaClassDTO = new YogaClassDTO();
        yogaClassDTO.setName("test updated");
        yogaClassDTO.setType(YogaClassType.PRENATAL);

        when(yogaClassRepository.findById(1L)).thenReturn(Optional.of(existingClass));
        when(yogaClassRepository.save(existingClass)).thenReturn(existingClass);

        YogaClass result = yogaClassService.updateYogaClass(1L, yogaClassDTO);

        assertEquals("test updated",result.getName());
        assertEquals(YogaClassType.PRENATAL, result.getYogaStyle().getClassType());
        verify(yogaClassRepository).save(existingClass);
    }
}

package com.example.demo.repository;

import com.example.demo.domain.YogaClass;
import com.example.demo.domain.YogaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("h2")
public class YogaClassRepositoryTest {

    @Autowired
    private YogaClassRepository yogaClassRepository;

    @Test
    public void testFindUsersByYogaClassId() {
        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(2L);
        assertTrue(optionalYogaClass.isPresent());
        YogaClass yogaClass = optionalYogaClass.get();

        List<YogaUser> users = yogaClassRepository.findUsersByYogaClassId(yogaClass.getId());
        assertFalse(users.isEmpty());
        assertEquals("Alice", users.getLast().getFirstName());
    }
}

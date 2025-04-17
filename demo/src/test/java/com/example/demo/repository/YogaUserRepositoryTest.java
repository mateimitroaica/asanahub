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
public class YogaUserRepositoryTest {
    @Autowired
    private YogaUserRepository yogaUserRepository;

    @Test
    public void testFindAllByYogaClassId() {
        Optional<YogaUser> yogaUser = yogaUserRepository.findById(1L);
        assertTrue(yogaUser.isPresent());
        YogaUser user = yogaUser.get();

        YogaClass yogaClass = user.getReservations().getLast();
        List<YogaUser> users = yogaUserRepository.findAllByYogaClassId(yogaClass.getId());

        assertFalse(users.isEmpty());
        assertEquals(user.getFirstName(), users.getLast().getFirstName());
    }
}

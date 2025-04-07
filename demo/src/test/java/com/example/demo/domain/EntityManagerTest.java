package com.example.demo.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("h2")
public class EntityManagerTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void findYogaClass() {
        YogaClass yogaClass = em.find(YogaClass.class, 1L);
        assertEquals(yogaClass.getName(), "Morning Vinyasa");
    }
}

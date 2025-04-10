package com.example.demo.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void updateYogaClass() {
        YogaClass yogaClass = em.find(YogaClass.class, 1L);
        yogaClass.setName("Updated YogaClass");
        em.flush();

        YogaClass updatedYogaClass = em.find(YogaClass.class, 1L);
        assertEquals(updatedYogaClass.getName(), "Updated YogaClass");
    }

//    @Test
//    void testDeleteYogaClassProperly() {
//        Long userId = 1L;
//        Long yogaClassId = 1L;
//
//        // Confirm the reservation exists first
//        Number countBefore = (Number) em.createNativeQuery(
//                        "SELECT COUNT(*) FROM reservations WHERE user_id = ?1 AND yoga_class_id = ?2")
//                .setParameter(1, userId)
//                .setParameter(2, yogaClassId)
//                .getSingleResult();
//
//        assertEquals(1, countBefore.intValue(), "Reservation should exist before deletion");
//
//        // Delete the reservation directly (only this!)
//        em.createNativeQuery("DELETE FROM reservations WHERE user_id = ?1 AND yoga_class_id = ?2")
//                .setParameter(1, userId)
//                .setParameter(2, yogaClassId)
//                .executeUpdate();
//
//        // Confirm the reservation is gone
//        Number countAfter = (Number) em.createNativeQuery(
//                        "SELECT COUNT(*) FROM reservations WHERE user_id = ?1 AND yoga_class_id = ?2")
//                .setParameter(1, userId)
//                .setParameter(2, yogaClassId)
//                .getSingleResult();
//
//        assertEquals(0, countAfter.intValue(), "Reservation should be deleted");
//
//    }

    @Test
    public void deleteYogaClass() {
        YogaClass yogaClass = em.find(YogaClass.class, 1L);
        YogaUser yogaUser = em.find(YogaUser.class, 1L);
        yogaUser.removeClass(yogaClass);
        em.flush();
        em.remove(yogaClass);

        YogaClass deletedYogaClass = em.find(YogaClass.class, 1L);

        assertEquals(1, yogaUser.getReservations().size());
        assertNull(deletedYogaClass);
    }

    @Test
    public void insertYogaClass() {
        YogaClass yogaClass = new YogaClass();
        yogaClass.setName("Test YogaClass");

        em.persist(yogaClass);
        em.flush();

        YogaClass saved = em.createQuery("SELECT y FROM YogaClass y WHERE y.name = :name", YogaClass.class)
                .setParameter("name", "Test YogaClass")
                .getSingleResult();

        assertNotNull(saved);
        assertEquals("Test YogaClass", saved.getName());
        assertEquals(100L,saved.getId());
    }
}
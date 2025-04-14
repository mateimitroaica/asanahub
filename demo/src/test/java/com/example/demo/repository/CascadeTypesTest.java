package com.example.demo.repository;

import com.example.demo.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.domain.YogaClassType.KUNDALINI;
import static com.example.demo.domain.YogaClassType.PRENATAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
public class CascadeTypesTest {

    @Autowired
    YogaStudioRepository yogaStudioRepository;

    @Autowired
    YogaClassRepository yogaClassRepository;

    @Autowired
    YogaStyleRepository yogaStyleRepository;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private YogaUserRepository yogaUserRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void updateYogaStyle() {
        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(1L);
        assertTrue(optionalYogaClass.isPresent());
        YogaClass yogaClass = optionalYogaClass.get();

        yogaClass.getYogaStyle().setClassType(PRENATAL);
        yogaClassRepository.save(yogaClass);

        optionalYogaClass = yogaClassRepository.findById(1L);
        assertTrue(optionalYogaClass.isPresent());
        yogaClass = optionalYogaClass.get();

        assertEquals(PRENATAL, yogaClass.getYogaStyle().getClassType());
    }

    @Test
    public void saveClassAndStyle() {
        YogaClass yogaClass = new YogaClass();
        yogaClass.setName("Example name");

        YogaStyle yogaStyle = new YogaStyle();
        yogaStyle.setClassType(KUNDALINI);
        yogaStyleRepository.save(yogaStyle);

        yogaClass.setYogaStyle(yogaStyle);
        yogaClassRepository.save(yogaClass);

        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(100L);
        assertTrue(optionalYogaClass.isPresent());
        yogaClass = optionalYogaClass.get();

        assertEquals(KUNDALINI, yogaClass.getYogaStyle().getClassType());
        assertEquals(100L, yogaClass.getYogaStyle().getId());
        assertEquals("Example name", yogaClass.getName());
    }

    @Test
    public void saveStudioSaveYogaClass() {
        Studio studio = new Studio();
        studio.setName("Shanti Loft");

        YogaClass yogaClass = new YogaClass();
        yogaClass.setName("Example name");

        studio.setYogaClasses(new ArrayList<>());
        studio.getYogaClasses().add(yogaClass);

        yogaStudioRepository.save(studio);

        Optional<Studio> optionalStudio = yogaStudioRepository.findByName("Shanti Loft");
        assertTrue(optionalStudio.isPresent());
        studio = optionalStudio.get();
        yogaClass = studio.getYogaClasses().getFirst();

        assertEquals("Example name",yogaClass.getName());
    }

    @Test
    public void removeClassFromStudio(){
        Optional<Studio> optionalStudio = yogaStudioRepository.findById(1L);
        assertTrue(optionalStudio.isPresent());
        Studio studio = optionalStudio.get();

        YogaClass yogaClass = studio.getYogaClasses().getFirst();
        YogaStyle yogaStyle = yogaClass.getYogaStyle();
        YogaInstructor yogaInstructor = yogaClass.getInstructor();
        List<YogaUser> yogaUsers = yogaClassRepository.findUsersByYogaClassId(yogaClass.getId());

        yogaStyle.setYogaClass(null);
        yogaClass.setYogaStyle(null);
        yogaInstructor.removeYogaClass(yogaClass);
        for (var user: yogaUsers) {
            user.removeClass(yogaClass);
        }
        studio.removeYogaClass(yogaClass);
        yogaStudioRepository.saveAndFlush(studio);

        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(1L);
        assertTrue(optionalYogaClass.isEmpty());
    }

    @Test
    public void removeStudioWithClass() {
        Optional<Studio> optionalStudio = yogaStudioRepository.findById(1L);
        assertTrue(optionalStudio.isPresent());
        Studio studio = optionalStudio.get();
        Long id = studio.getYogaClasses().getFirst().getId();

        yogaStudioRepository.delete(studio);

        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(id);
        assertTrue(optionalYogaClass.isEmpty());
    }

    @Test
    public void saveUserSaveSubscription() {
        YogaUser yogaUser = new YogaUser();
        yogaUser.setFirstName("Exemplu");

        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(SubscriptionType.STANDARD);
        yogaUser.setSubscriptions(new ArrayList<>());
        yogaUser.getSubscriptions().add(subscription);

        yogaUserRepository.save(yogaUser);

        assertTrue(yogaUserRepository.findById(100L).isPresent());
        assertEquals("Exemplu", yogaUserRepository.findById(100L).get().getFirstName());
        assertEquals(100L, yogaUserRepository.findById(100L).get().getSubscriptions().getFirst().getId());
        assertEquals(SubscriptionType.STANDARD, yogaUser.getSubscriptions().getFirst().getSubscriptionType());
    }

    @Test
    public void removeSubscriptionFromUser() {
        Optional<YogaUser> optionalYogaUser = yogaUserRepository.findById(1L);
        assertTrue(optionalYogaUser.isPresent());
        YogaUser yogaUser = optionalYogaUser.get();

        Subscription subscription = yogaUser.getSubscriptions().getFirst();
        Long id = subscription.getId();

        yogaUser.removeSubscription(subscription);
        yogaUserRepository.saveAndFlush(yogaUser);

        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(id);
        assertTrue(optionalSubscription.isEmpty());
    }

    @Test
    public void enrollUserToYogaClass() {
        YogaUser yogaUser = new YogaUser();
        yogaUser.setReservations(new ArrayList<>());

        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(1L);
        assertTrue(optionalYogaClass.isPresent());
        YogaClass yogaClass = optionalYogaClass.get();

        yogaUser.getReservations().add(yogaClass);
        yogaUserRepository.save(yogaUser);

        assertEquals(100L, yogaUser.getId());
        assertEquals(yogaClass.getId(), yogaUser.getReservations().getFirst().getId());
    }

    @Test
    public void removeClassFromUserDoesNotDeleteIt() {
        Optional<YogaUser> optionalYogaUser = yogaUserRepository.findById(2L);
        assertTrue(optionalYogaUser.isPresent());
        YogaUser yogaUser = optionalYogaUser.get();

        YogaClass yogaClass = yogaUser.getReservations().getFirst();
        yogaUser.removeClass(yogaClass);
        yogaUserRepository.saveAndFlush(yogaUser);

        Optional<YogaClass> optionalYogaClass = yogaClassRepository.findById(2L);

        assertEquals(0, yogaUser.getReservations().size());
        assertTrue(optionalYogaClass.isPresent());
    }

}

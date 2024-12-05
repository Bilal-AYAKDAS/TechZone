package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserManagerTest {

    private UserManager userManager;

    @Autowired
    private IUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userManager = new UserManager(userRepository);
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void getByEmail() {
    }

    @Test
    void getByEmailAndPassword() {
    }

    @Test
    @Transactional
    @Rollback(false)
    void add() {
        User user = new User();
        user.setFirstName("Zeynep");
        user.setLastName("Kaya");
        user.setAge(23);
        user.setEmail("zeynepkaya@gmail.com");
        user.setPhoneNumber("5555555");
        user.setPassword("password");

        User savedUser = userManager.add(user);

        //test first name
        assertEquals("Zeynep", savedUser.getFirstName());
        //test last name
        assertEquals("Kaya", savedUser.getLastName());
        // test age
        assertEquals(23, savedUser.getAge());
        //test email
        assertEquals("zeynepkaya@gmail.com", savedUser.getEmail());
        //test phone number
        assertEquals("5555555", savedUser.getPhoneNumber());
        //test password
        assertEquals("password", savedUser.getPassword());
        //test date
        user.setCreatedAt(new Date());
        Date createdAt = user.getCreatedAt();
        assertEquals(createdAt, savedUser.getCreatedAt());

        //Register is correct
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(savedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(savedUser.getLastName(), foundUser.getLastName());
        assertEquals(savedUser.getAge(), foundUser.getAge());
        assertEquals(savedUser.getEmail(), foundUser.getEmail());
        assertEquals(savedUser.getPhoneNumber(), foundUser.getPhoneNumber());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
        assertEquals(savedUser.getCreatedAt(), foundUser.getCreatedAt());
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }
}

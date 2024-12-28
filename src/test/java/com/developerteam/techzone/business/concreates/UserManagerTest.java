package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthService authService;

    @BeforeEach
    void setUp() {
        /* for testUpdateOwnInfo
        SecurityContextHolder.getContext().setAuthentication(

                new UsernamePasswordAuthenticationToken("aysecelik@gmail.com", null, List.of())
        ); */

        //for testGetOwnInfo
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("yagmurozler@gmail.com", null, List.of()));
    }
    @Test
    void testGetAll() {
        List<User> users = userManager.getAll();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Zeynep", users.get(0).getFirstName());
        assertEquals("Can", users.get(1).getLastName());
        assertEquals(23,users.get(0).getAge());
        assertEquals("password", users.get(0).getPassword());
        assertEquals("zeynepkaya@gmail.com", users.get(0).getEmail());
    }

    @Test
    void testGetById() {
        User user = userManager.getById(1);
        assertNotNull(user);
        assertEquals("Zeynep", user.getFirstName());
        assertEquals("Kaya", user.getLastName());
        assertEquals(23, user.getAge());
        assertEquals("password", user.getPassword());
        assertEquals("zeynepkaya@gmail.com", user.getEmail());
    }

    @Test
    void testGetByEmail() {
        User user = userManager.getByEmail("mehmetcan@gmail.com");
        assertNotNull(user);
        assertEquals("Mehmet", user.getFirstName());
        assertEquals("Can", user.getLastName());
        assertEquals(30, user.getAge());
        assertEquals("mehmetcan", user.getPassword());
        assertEquals("1111111", user.getPhoneNumber());
        assertEquals(2, user.getId());
    }

    @Test
    void testGetByEmailAndPassword() {
        User user = userManager.getByEmailAndPassword("mehmetcan@gmail.com", "mehmetcan");
        assertNotNull(user);
        assertEquals("Mehmet", user.getFirstName());
        assertEquals("Can", user.getLastName());
        assertEquals(30, user.getAge());
        assertEquals("mehmetcan", user.getPassword());
        assertEquals("1111111", user.getPhoneNumber());
        assertEquals(2, user.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        User user = new User();
        user.setFirstName("Zeynep");
        user.setLastName("Kaya");
        user.setAge(23);
        user.setEmail("zeynepkaya@gmail.com");
        user.setPhoneNumber("5555555");
        user.setPassword("password");
        user.setCreatedAt(new Date());
        Date createdAt = user.getCreatedAt();

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
    @Transactional
    @Rollback(false)
    void testUpdate() {
        User user = new User();
        user.setFirstName("Mehmet");
        user.setLastName("Can");
        user.setAge(30);
        user.setEmail("mehmetcan@gmail.com");
        user.setPhoneNumber("1111111");
        user.setPassword("mehmetcan");
        user.setCreatedAt(new Date());

        User updatedUser = userManager.update(2, user);

        assertNotNull(updatedUser);
        assertEquals("Mehmet", updatedUser.getFirstName());
        assertEquals("Can", updatedUser.getLastName());
        assertEquals(30, updatedUser.getAge());
        assertEquals("mehmetcan@gmail.com", updatedUser.getEmail());
        assertEquals("1111111", updatedUser.getPhoneNumber());
        assertEquals("mehmetcan", updatedUser.getPassword());

        User foundUser = userRepository.findById(updatedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals(updatedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(updatedUser.getLastName(), foundUser.getLastName());
        assertEquals(updatedUser.getAge(), foundUser.getAge());
        assertEquals(updatedUser.getEmail(), foundUser.getEmail());
        assertEquals(updatedUser.getPhoneNumber(), foundUser.getPhoneNumber());
        assertEquals(updatedUser.getPassword(), foundUser.getPassword());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        User user =userRepository.getById(3);
        assertNotNull(user);
        userRepository.delete(user);

    }

    @Test
    void testGetOwnInfo() {
        DtoUser user = userManager.getOwnInfo();
        assertNotNull(user);
        assertEquals("Yağmur", user.getFirstName());
        assertEquals("Özler", user.getLastName());
        assertEquals(18, user.getAge());
        assertEquals("yagmurozler@gmail.com", user.getEmail());
        assertEquals("0000000", user.getPhoneNumber());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdateOwnInfo() {
        DtoUser updateUser = new DtoUser();
        updateUser.setFirstName("Yağmur");
        updateUser.setLastName("Özler");
        updateUser.setAge(18);
        updateUser.setEmail("yagmurozler@gmail.com");
        updateUser.setPhoneNumber("0000000");

        DtoUser updatedUser = userManager.updateOwnInfo(updateUser);
        assertNotNull(updatedUser);
        assertEquals("Yağmur", updatedUser.getFirstName());
        assertEquals("Özler", updatedUser.getLastName());
        assertEquals(18, updatedUser.getAge());
        assertEquals("yagmurozler@gmail.com", updatedUser.getEmail());
        assertEquals("0000000", updatedUser.getPhoneNumber());

        User foundUser = userRepository.findById(14).orElse(null);
        assertNotNull(foundUser);
        assertEquals(updatedUser.getFirstName(), foundUser.getFirstName());
        assertEquals(updatedUser.getLastName(), foundUser.getLastName());
        assertEquals(updatedUser.getAge(), foundUser.getAge());
        assertEquals(updatedUser.getEmail(), foundUser.getEmail());
        assertEquals(updatedUser.getPhoneNumber(), foundUser.getPhoneNumber());
    }
}

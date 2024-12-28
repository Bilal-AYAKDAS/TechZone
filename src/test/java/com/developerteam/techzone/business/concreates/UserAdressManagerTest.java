package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.IUserAdressRepository;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.concreates.UserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdressIU;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class UserAdressManagerTest {

    @Autowired
    private IUserAdressRepository userAdressRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private UserAdressManager userAdressManager;

    @Autowired
    private IUserService userService;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of()));
    }

    @Test
    void testGetAll() {
        List<UserAdress> userAdressList = userAdressManager.getAll();
        assertNotNull(userAdressList);
        assertEquals("addressOfİzmir", userAdressList.get(0).getAdress());
        assertEquals("İzmir" , userAdressList.get(0).getCity());
        assertEquals("Türkiye", userAdressList.get(0).getCountry());
        assertEquals("Egion", userAdressList.get(0).getDistrict());
        assertEquals("35000", userAdressList.get(0).getPostCode());

    }

    @Test
    void testGetById() {
        UserAdress userAddress = userAdressManager.getById(1);
        assertNotNull(userAddress);
        assertEquals("addressOfİzmir", userAddress.getAdress());
        assertEquals("İzmir" , userAddress.getCity());
        assertEquals("Türkiye", userAddress.getCountry());
        assertEquals("Egion", userAddress.getDistrict());
        assertEquals("35000", userAddress.getPostCode());
    }

    @Test
    void testGetByUserId() {
        List<UserAdress> userAdresses = userAdressManager.getByUserId(1);
        assertNotNull(userAdresses);
        assertEquals("addressOfİzmir", userAdresses.get(0).getAdress());
        assertEquals("İzmir" , userAdresses.get(0).getCity());
        assertEquals("Türkiye", userAdresses.get(0).getCountry());
        assertEquals("Egion", userAdresses.get(0).getDistrict());
        assertEquals("35000", userAdresses.get(0).getPostCode());
    }

    @Test
    void testGetOwnAdressByUserId() {
        List<DtoUserAdress> dtoUserAdress = userAdressManager.getOwnAdressByUserId();
        assertNotNull(dtoUserAdress);

        assertEquals("addressOfİzmir", dtoUserAdress.get(0).getAdress());
        assertEquals("İzmir" , dtoUserAdress.get(0).getCity());
        assertEquals("Türkiye", dtoUserAdress.get(0).getCountry());
        assertEquals("Egion", dtoUserAdress.get(0).getDistrict());
        assertEquals("35000", dtoUserAdress.get(0).getPostCode());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoUserAdressIU dtoUserAdressIU = new DtoUserAdressIU();
        dtoUserAdressIU.setCity("aa");
        dtoUserAdressIU.setCountry("Türkiye");
        dtoUserAdressIU.setDistrict("bb");
        dtoUserAdressIU.setPostCode("30000");
        dtoUserAdressIU.setAdress("addressOfAa");

        DtoUserAdress result = userAdressManager.add(dtoUserAdressIU);
        assertNotNull(result);
        assertEquals("aa", result.getCity());
        assertEquals("Türkiye", result.getCountry());
        assertEquals("bb", result.getDistrict());
        assertEquals("30000", result.getPostCode());
        assertEquals("addressOfAa", result.getAdress());

        UserAdress savedAddress = userAdressRepository.getById(10);
        assertNotNull(savedAddress);
        assertEquals(result.getAdress(), savedAddress.getAdress());
        assertEquals(result.getCity(), savedAddress.getCity());
        assertEquals(result.getCountry(), savedAddress.getCountry());
        assertEquals(result.getDistrict(), savedAddress.getDistrict());
        assertEquals(result.getPostCode(), savedAddress.getPostCode());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        DtoUserAdressIU updateUserAddress = new DtoUserAdressIU();
        updateUserAddress.setCity("Manisa");
        updateUserAddress.setCountry("Türkiye");
        updateUserAddress.setDistrict("Egion");
        updateUserAddress.setPostCode("45000");
        updateUserAddress.setAdress("addressOfManisa");

        DtoUserAdress result = userAdressManager.update(14, updateUserAddress);
        assertNotNull(result);
        assertEquals("Manisa", result.getCity());
        assertEquals("45000", result.getPostCode());
        assertEquals("addressOfManisa", result.getAdress());

        UserAdress savedAddress = userAdressRepository.findById(14).orElse(null);
        assertNotNull(savedAddress);
        assertEquals(result.getAdress(), savedAddress.getAdress());
        assertEquals(result.getCity(), savedAddress.getCity());
        assertEquals(result.getPostCode(), savedAddress.getPostCode());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        UserAdress userAdress = userAdressRepository.getById(4);
        assertNotNull(userAdress);
        userAdressManager.delete(userAdress.getId());
    }
}

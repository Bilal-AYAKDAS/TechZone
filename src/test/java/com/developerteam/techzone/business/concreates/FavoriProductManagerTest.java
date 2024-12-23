package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.IFavoriProductRepository;
import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoFavoriProduct;
import com.developerteam.techzone.entities.dto.DtoFavoriProductIU;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoUserIU;
import com.developerteam.techzone.jwt.AuthRequest;
import com.developerteam.techzone.jwt.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class FavoriProductManagerTest {

    @Autowired
    private IFavoriProductRepository favoriProductRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserManager userManager;

    private FavoriProductManager favoriProductManager;


    @BeforeEach
    void setUp() {
        favoriProductManager = new FavoriProductManager(favoriProductRepository, authService);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("zeynepkaya@gmail.com", null, List.of()));
    }


    @Test
    void testFindUserOrThrow() {
        User result = favoriProductManager.findUserOrThrow();
        assertNotNull(result);
        assertEquals(1 , result.getId());
        assertEquals("zeynepkaya@gmail.com", result.getEmail());
    }

    @Test
    void testGetAll() {
        List<DtoFavoriProduct> favoriProductList = favoriProductManager.getAll();
        assertNotNull(favoriProductList);
        assertEquals(4 , favoriProductList.get(0).getId());
        assertEquals(2, favoriProductList.get(0).getProduct().getId());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoProduct dtoProduct = productManager.getById(2);

        DtoFavoriProductIU dtoFavoriProductIU = new DtoFavoriProductIU();
        dtoFavoriProductIU.setProductId(2);

        DtoFavoriProduct result = favoriProductManager.add(dtoFavoriProductIU);

        assertNotNull(result);
        assertEquals(4, result.getId());
        assertEquals(dtoProduct.getId(), result.getProduct().getId());

        FavoriProduct savedFavoriProduct = favoriProductRepository.getById(4);
        assertNotNull(savedFavoriProduct);
        assertEquals(result.getId(), savedFavoriProduct.getId());
        assertEquals(result.getProduct().getId(), savedFavoriProduct.getProduct().getId());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        FavoriProduct favoriProduct = favoriProductRepository.getById(3);
        assertNotNull(favoriProduct);
        favoriProductRepository.delete(favoriProduct);
    }
}

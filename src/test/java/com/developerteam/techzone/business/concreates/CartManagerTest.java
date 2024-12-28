package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoCartItem;
import com.developerteam.techzone.entities.dto.DtoCartItemIU;
import com.developerteam.techzone.entities.dto.DtoProduct;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class CartManagerTest {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CartManager cartManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of()));
    }

    @Test
    void testGetAll() {
        List<Cart> carts = cartManager.getAll();
        assertNotNull(carts);
        assertTrue(carts.size() > 0);
        assertEquals(1, carts.get(0).getId());
        assertEquals(2, carts.get(1).getUser().getId());


    }

    @Test
    void testGetById() {
        Cart savedCart = cartManager.getById(1);
        assertNotNull(savedCart);
        assertEquals(1, savedCart.getId());
        assertEquals(1, savedCart.getUser().getId());
    }

    @Test
    void testGetByUserId() {
        List<DtoCartItem> dtoCartItems = cartManager.getByUserId(1);
        assertNotNull(dtoCartItems);
        assertEquals(1, dtoCartItems.get(0).getQuantity());
        assertEquals(2, dtoCartItems.get(0).getProduct().getId());
    }
//
//    @Test
//    void testGetOwnCart() {
//        List<DtoCartItem> dtoCartItems = cartManager.getOwnCart();
//        assertNotNull(dtoCartItems);
//        assertEquals(3,dtoCartItems.get(0).getId());
//        assertEquals(3,dtoCartItems.get(0).getQuantity());
//        assertEquals(1,dtoCartItems.get(0).getProduct().getId());
//    }

    @Test
    @Transactional
    @Rollback(false)
    void testAddItemToCart() {
        DtoProduct product = productManager.getById(1);

        DtoCartItemIU dtoCartItemIU = new DtoCartItemIU();
        dtoCartItemIU.setProductId(product.getId());
        dtoCartItemIU.setQuantity(8);

        DtoCartItem result = cartManager.addItemToCart(dtoCartItemIU);
        assertNotNull(result);
        assertEquals(8,result.getQuantity());
        assertEquals(product.getId(), result.getProduct().getId());

        Optional<Cart> userCart = cartRepository.findByUserId(1);
        assertTrue(userCart.isPresent());
    }


    @Test
    @Transactional
    @Rollback(false)
    void testRemoveItemFromCart() {
        CartItem cartItem = cartItemRepository.getById(4);
        assertNotNull(cartItem);

        cartManager.removeItemFromCart(cartItem.getId());

        Optional<CartItem> deletedItem = cartItemRepository.findById(cartItem.getId());
        assertFalse(deletedItem.isPresent());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Cart cart = cartRepository.getById(3);
        assertNotNull(cart);

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
        cartItemRepository.flush();

        cartManager.delete(cart.getId());

        assertFalse(cartRepository.existsById(cart.getId()));
    }

    @Test
    void testFindCartOrThrow() {
        Cart result = cartManager.findCartOrThrow(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getUser().getId());
    }
}

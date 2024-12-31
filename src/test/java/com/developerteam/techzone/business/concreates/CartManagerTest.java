package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoCart;
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
    private CartManager cartManager;


    @Autowired
    private ProductManager productManager;
    @Autowired
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("mehmetcan@gmail.com", null, List.of()));

        /*for testAdd
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of()));
    */
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

    @Test
    void testGetOwnCart() {
        DtoCart dtoCart = cartManager.getOwnCart();
        assertNotNull(dtoCart);

        DtoCartItem cartItem = dtoCart.getCartItems().get(0);
        assertNotNull(cartItem);
        System.out.println(cartItem.getProduct().getId());
        assertEquals(dtoCart.getCartItems().get(0).getId(), cartItem.getId());
        assertEquals(dtoCart.getCartItems().get(0).getQuantity(), cartItem.getQuantity());
        assertEquals(dtoCart.getCartItems().get(0).getProduct().getId(), cartItem.getProduct().getId());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testAddItemToCart() {
        User user = userManager.getById(15);
        DtoProduct product = productManager.getById(1);

        DtoCartItemIU dtoCartItemIU = new DtoCartItemIU();
        dtoCartItemIU.setProductId(product.getId());
        dtoCartItemIU.setQuantity(1);

        DtoCartItem result = cartManager.addItemToCart(dtoCartItemIU);
        assertNotNull(result);
        assertEquals(1,result.getQuantity());
        assertEquals(product.getId(), result.getProduct().getId());

        CartItem savedCartItem = cartItemRepository.findByCartIdAndProductId(
                cartRepository.findByUserId(user.getId()).get().getId(),
                product.getId()
        ).orElse(null);
        assertNotNull(savedCartItem);
        assertEquals(result.getQuantity(), savedCartItem.getQuantity());
    }


    @Test
    @Transactional
    @Rollback(false)
    void testRemoveItemFromCart() {
        CartItem cartItem = cartItemRepository.getById(3);
        assertNotNull(cartItem);

        cartManager.removeItemFromCart(cartItem.getId());

        CartItem updatedCartItem = cartItemRepository.findById(cartItem.getId()).orElse(null);
        assertNotNull(updatedCartItem);
        assertEquals(1, updatedCartItem.getQuantity());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        List<CartItem> cartItems = cartItemRepository.findByCartId(4);
        assertNotNull(cartItems);
        for(CartItem cartItem : cartItems) {
            cartItemRepository.deleteById(cartItem.getId());
        }

        Cart cart = cartRepository.getById(4);
        assertNotNull(cart);

        cartManager.delete(cart.getId());
    }

    @Test
    void testFindCartOrThrow() {
        Cart result = cartManager.findCartOrThrow(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getUser().getId());
    }

}

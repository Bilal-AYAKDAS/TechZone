package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.dataAccess.abstracts.*;
import com.developerteam.techzone.entities.concreates.*;
import com.developerteam.techzone.entities.dto.DtoOrder;
import com.developerteam.techzone.entities.dto.DtoOrderIU;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class OrderManagerTest {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of())
        );
    }
    @Test
    void testFindUserOrThrow() {
    }

    @Test
    void testGetAllOrders() {
    }

    @Test
    void testGetOrderById() {
    }

    @Test
    @Transactional
    @Rollback(false)
    void testCreateOrder() {
        Cart cart = cartRepository.getById(5);
        Product product = productRepository.getById(2);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);

        DtoOrderIU dtoOrderIU = new DtoOrderIU();
        dtoOrderIU.setCartId(cart.getId());

        Date createdDate = new Date();
        createdDate.setTime(createdDate.getTime());

        DtoOrder createdOrder = orderManager.createOrder(dtoOrderIU);
        assertNotNull(createdOrder);
        System.out.println(createdOrder);
        assertEquals(product.getPrice() * cartItem.getQuantity(), createdOrder.getTotalPrice());
        assertEquals("CREATED", createdOrder.getStatus());
        assertEquals(product.getName(),createdOrder.getOrderItems());
        assertEquals(createdDate,createdOrder.getCreatedDate());

        OrderItem orderItems = orderItemRepository.findById(createdOrder.getId()).orElse(null);
        assertNotNull(orderItems);
        assertEquals(product.getId(), orderItems.getProduct().getId());
    }

    @Test
    void testGetAllCustomersOrder() {
    }

    @Test
    void testGetByIdCustomersOrder() {
    }
}

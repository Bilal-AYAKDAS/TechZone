package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.dataAccess.abstracts.*;
import com.developerteam.techzone.entities.concreates.*;
import com.developerteam.techzone.entities.dto.DtoOrder;
import com.developerteam.techzone.entities.dto.DtoOrderIU;
import com.developerteam.techzone.entities.dto.DtoOrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Nested
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
        User user = orderManager.findUserOrThrow();

        assertNotNull(user);
        assertEquals(15, user.getId());

    }

    @Test
    @Transactional
    void testGetAllOrders() {
        Order order = orderRepository.getById(1);

        List<DtoOrder> dtoOrders = orderManager.getAllOrders();
        assertNotNull(dtoOrders);
        assertFalse(dtoOrders.isEmpty());
        assertEquals(order.getId(), dtoOrders.get(0).getId());
        assertEquals(order.getTotalPrice(), dtoOrders.get(0).getTotalPrice());
        assertEquals(order.getStatus(), dtoOrders.get(0).getStatus());

        // Siparişe ait ürünlerin doğru olduğunu kontrol et
        assertFalse(dtoOrders.get(0).getOrderItems().isEmpty());
        assertEquals(3, dtoOrders.get(0).getOrderItems().get(0).getQuantity());

    }

    @Test
    @Transactional
    void testGetOrderById() {
        Order order = orderRepository.getById(1);

        DtoOrder dtoOrder = orderManager.getOrderById(order.getId());

        // Döndürülen siparişin doğru olduğunu kontrol et
        assertNotNull(dtoOrder);
        assertEquals(order.getId(), dtoOrder.getId());
        assertEquals(order.getTotalPrice(), dtoOrder.getTotalPrice());
        assertEquals(order.getStatus(), dtoOrder.getStatus());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testCreateOrder() {
        Cart cart = cartRepository.getById(5);
        Product product = productRepository.getById(2);

        CartItem cartItem = cartItemRepository.getById(6);

        DtoOrderIU dtoOrderIU = new DtoOrderIU();
        dtoOrderIU.setCartId(cart.getId());

        Date createdDate = new Date();
        createdDate.setTime(createdDate.getTime());

        DtoOrder createdOrder = orderManager.createOrder(dtoOrderIU);
        assertNotNull(createdOrder);
        assertEquals(product.getPrice() * cartItem.getQuantity(), createdOrder.getTotalPrice());
        assertEquals("CREATED", createdOrder.getStatus());
        assertEquals(product.getName(),createdOrder.getOrderItems());
        assertEquals(createdDate,createdOrder.getCreatedDate());

        OrderItem orderItems = orderItemRepository.findById(createdOrder.getId()).orElse(null);
        assertNotNull(orderItems);
        assertEquals(product.getId(), orderItems.getProduct().getId());
    }

    @Test
    @Transactional
    void testGetAllCustomersOrder() {
        CartItem cartItem = cartItemRepository.getById(1);
        Product product = productRepository.getById(1);
        Order order = orderRepository.getById(1);
        OrderItem orderItem = orderItemRepository.getById(1);

        List<DtoOrder> dtoOrders = orderManager.getAllOrders();
        assertNotNull(dtoOrders);
        assertFalse(dtoOrders.isEmpty());

        // İlk siparişi kontrol et
        DtoOrder dtoOrder = dtoOrders.get(0);
        assertEquals(order.getId(), dtoOrder.getId());
        assertEquals(order.getTotalPrice(), dtoOrder.getTotalPrice());
        assertEquals(order.getStatus(), dtoOrder.getStatus());

        // Sipariş öğelerinin doğru şekilde döndüğünü kontrol et
        assertNotNull(dtoOrder.getOrderItems());
        assertFalse(dtoOrder.getOrderItems().isEmpty());
        DtoOrderItem dtoOrderItem = dtoOrder.getOrderItems().get(0);
        assertEquals(product.getId(), dtoOrderItem.getProduct().getId());

    }

    @Test
    @Transactional
    void testGetByIdCustomersOrder() {
        DtoOrder dtoOrder = orderManager.getByIdCustomersOrder(1);
        assertNotNull(dtoOrder);

        assertEquals(1,dtoOrder.getId());
        assertEquals(60000,dtoOrder.getTotalPrice());
        assertEquals("CREATED",dtoOrder.getStatus());
    }
    }

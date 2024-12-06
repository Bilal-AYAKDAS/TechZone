package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.entities.concreates.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class CartManagerTest {
    private CartManager cartManager;

    @Autowired
    private ICartRepository cartRepository;
    private ICartItemRepository cartItemRepository;

    @Test
    void testGetAll() {
        List<Cart> cards = cartManager.getAll();
    }

    @Test
    void testGetById() {

    }

    @Test
    void testGetByUserId() {
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        Cart cart = new Cart();

    }

    @Test
    void testDelete() {
    }

    @Test
    void testGetOrCreateCart() {
    }

    @Test
    void testGetCartItemsByUserId() {
    }

    @Test
    void testAddItemToCart() {
    }

    @Test
    void testRemoveItemFromCart() {
    }
}

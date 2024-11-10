package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart,Integer> {
    Optional <Cart> findByUserId(int userId);
}

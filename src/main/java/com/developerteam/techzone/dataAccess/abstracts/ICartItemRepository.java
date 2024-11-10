package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndProductId(int cartId, int productId);

    List<CartItem> findByCartId(int cartId);
}

package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Order;
import com.developerteam.techzone.entities.concreates.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrder(Order order);
}

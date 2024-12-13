package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

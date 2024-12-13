package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    public List<Order> findByUserId(int userId);
}

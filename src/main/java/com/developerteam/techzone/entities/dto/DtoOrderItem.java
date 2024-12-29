package com.developerteam.techzone.entities.dto;

import com.developerteam.techzone.entities.concreates.Order;
import com.developerteam.techzone.entities.concreates.OrderItem;
import com.developerteam.techzone.entities.concreates.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoOrderItem {

    private int id;
    private Product product;
    private int quantity;
    private Double price;

}

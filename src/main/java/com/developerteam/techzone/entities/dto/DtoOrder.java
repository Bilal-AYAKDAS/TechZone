package com.developerteam.techzone.entities.dto;

import com.developerteam.techzone.entities.concreates.OrderItem;

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
public class DtoOrder {

    private int id;
    private Double totalPrice;
    private String status;
    private Date createdDate;
    private Date modifiedDate;
    private List<OrderItem> orderItems;
}

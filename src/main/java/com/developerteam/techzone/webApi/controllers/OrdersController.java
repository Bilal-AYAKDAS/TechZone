package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.IOrderService;
import com.developerteam.techzone.entities.dto.DtoOrder;
import com.developerteam.techzone.entities.dto.DtoOrderIU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrdersController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/getall")
    public List <DtoOrder> getAll(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public DtoOrder getById(@PathVariable int id){
        return orderService.getOrderById(id);
    }

    @PostMapping("/createOrder")
    public DtoOrder create(@RequestBody DtoOrderIU dtoOrderIU){
        return orderService.createOrder(dtoOrderIU);
    }


    @GetMapping("/getAdminAllOrders")
    public List <DtoOrder> getAdminAllOrders(){
        return orderService.getAllCustomersOrder();
    }
    @GetMapping("/getAdminOrder/{id}")
    public DtoOrder getAdminAllOrders(@PathVariable int id){
        return orderService.getByIdCustomersOrder(id);
    }




}

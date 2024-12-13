package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.ICartService;
import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import com.developerteam.techzone.entities.dto.DtoCartItem;
import com.developerteam.techzone.entities.dto.DtoCartItemIU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    //For Admin
    @GetMapping("/getall")
    public List<Cart> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("/{id}")
    public Cart getById(@PathVariable int id){
        return this.cartService.getById(id);
    }

    @GetMapping("/getbyuserid/{userId}")
    public List<DtoCartItem> getByUserId(@PathVariable int userId){
        return this.cartService.getByUserId(userId);
    }

    //For Customer
    @GetMapping("/getOwnCart")
    public List<DtoCartItem> getOwnCart(){
        return this.cartService.getOwnCart();
    }

    @PostMapping("/addCartItem")
    public DtoCartItem addCartItem(@RequestBody DtoCartItemIU dtoCartItemIU){
        return this.cartService.addItemToCart(dtoCartItemIU);
    }

    @DeleteMapping("/deleteCartItem/{id}")
    public void deleteCartItem(@PathVariable int id){
        this.cartService.removeItemFromCart(id);
    }



}

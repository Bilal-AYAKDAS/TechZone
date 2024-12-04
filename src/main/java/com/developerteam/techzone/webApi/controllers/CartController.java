package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.ICartService;
import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
<<<<<<< HEAD
=======

>>>>>>> fa31834 (feat: Implement DTO objects and exception handling architecture)
    private ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getall")
    public List<Cart> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("/{id}")
    public Cart getById(@PathVariable int id){
        return this.cartService.getById(id);
    }

    @GetMapping("/getbyuserid/{userId}")
    public Cart getByUserId(@PathVariable int userId){
        return this.cartService.getByUserId(userId);
    }

    @PostMapping("/add")
    public Cart add(@RequestBody Cart cart){
        return this.cartService.add(cart);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.cartService.delete(id);
    }

    @GetMapping("/getcartitembyuserid/{userId}")
    public List<CartItem> getCartItemsByUserId(@PathVariable int userId){
        return this.cartService.getCartItemsByUserId(userId);
    }

    @PostMapping("/addproducttocart")
    public CartItem addProductToCartItems(@RequestBody CartItem cartItem){
        return this.cartService.addItemToCart(cartItem);
    }

    @DeleteMapping("/deleteproductfromcart/{cartItemId}")
    public void deleteProductFromCartItems(@PathVariable int cartItemId){
        this.cartService.removeItemFromCart(cartItemId);
    }

}

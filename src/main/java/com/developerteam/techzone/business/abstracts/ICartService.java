package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.concreates.User;

import java.util.List;

public interface ICartService {
    List<Cart> getAll();
    Cart getById(int id); // Belirli bir sepeti getirir.
    Cart getByUserId(int userId); // Belirli bir kullanıcının sepetini getirir.
    Cart add(Cart cart); // Yeni sepet ekler.
    void delete(int id); // Mevcut bir sepeti siler.


    public List <CartItem> getCartItemsByUserId(int userId) ;
    Cart getOrCreateCart(User user);
    CartItem addItemToCart(CartItem cartItem);
    void removeItemFromCart(int id);
}

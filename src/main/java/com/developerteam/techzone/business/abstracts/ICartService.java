package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.dto.DtoCart;
import com.developerteam.techzone.entities.dto.DtoCartItem;
import com.developerteam.techzone.entities.dto.DtoCartItemIU;

import java.util.List;

public interface ICartService {
    List<Cart> getAll();
    Cart getById(int id); // Belirli bir sepeti getirir.
    List<DtoCartItem> getByUserId(int userId); // Belirli bir kullanıcının sepetini getirir.


    DtoCart getOwnCart();
    DtoCartItem addItemToCart(DtoCartItemIU dtoCartItemIU); // Yeni sepet ekler.
    void removeItemFromCart(int id);
    void delete(int id); // Mevcut bir sepeti siler.

}

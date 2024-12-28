package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.ICartService;
import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.entities.concreates.*;
import com.developerteam.techzone.entities.dto.DtoCartItem;
import com.developerteam.techzone.entities.dto.DtoCartItemIU;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartManager implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IAuthService authService;

    //For Admin
    @Override
    public List<Cart> getAll() {
       return cartRepository.findAll();
    }

    @Override
    public Cart getById(int id) {
        return findCartOrThrow(id);
    }

    @Override
    public List<DtoCartItem> getByUserId(int userId) {
        Cart cart = cartRepository.findByUserId(userId).get();
        List <CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        List<DtoCartItem> dtoCartItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            DtoCartItem dtoCartItem = new DtoCartItem();
            dtoCartItem.setId(cartItem.getId());
            dtoCartItem.setQuantity(cartItem.getQuantity());
            dtoCartItem.setProduct(cartItem.getProduct());
            dtoCartItems.add(dtoCartItem);
        }
        return dtoCartItems;
    }


    //For Customer
    private boolean currentCartHasUser(User user, Cart cart) {
        return user.getId()== cart.getUser().getId();
    }

    @Override
    public List<DtoCartItem> getOwnCart() {
        Optional <User> optionalUser = authService.getAuthenticatedUser();
        Optional <Cart> optionalCart = cartRepository.findByUserId(optionalUser.get().getId());
        findCartOrThrow(optionalCart.get().getId());
        List <CartItem> cartItems = cartItemRepository.findByCartId(optionalCart.get().getId());

        List<DtoCartItem> dtoCartItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            DtoCartItem dtoCartItem = new DtoCartItem();
            dtoCartItem.setId(cartItem.getId());
            dtoCartItem.setQuantity(cartItem.getQuantity());
            dtoCartItem.setProduct(cartItem.getProduct());
            dtoCartItems.add(dtoCartItem);
        }
        return dtoCartItems;
    }

    @Override
    public DtoCartItem addItemToCart(DtoCartItemIU dtoCartItemIU) {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        Optional <Cart> optionalCart = cartRepository.findByUserId(optionalUser.get().getId());
        Cart userCart;
        if (optionalCart.isEmpty()){
            Cart newCart = new Cart();
            newCart.setUser(optionalUser.get());
            userCart = cartRepository.save(newCart);
        }else {
            userCart = optionalCart.get();
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(userCart);
        // Aynı Product Userın Catında Bulunuyor mu
        Optional <CartItem> optionalCartItem = cartItemRepository.findByCartIdAndProductId(userCart.getId(),dtoCartItemIU.getProductId());
        if (optionalCartItem.isEmpty()){
            cartItem.setQuantity(dtoCartItemIU.getQuantity());
            Product product = new Product();
            product.setId(dtoCartItemIU.getProductId());
            cartItem.setProduct(product);
        }else{
            cartItem = optionalCartItem.get();
            cartItem.setQuantity(optionalCartItem.get().getQuantity() + dtoCartItemIU.getQuantity());
        }

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        DtoCartItem dtoCartItem = new DtoCartItem();
        dtoCartItem.setId(savedCartItem.getId());
        dtoCartItem.setQuantity(savedCartItem.getQuantity());
        dtoCartItem.setProduct(savedCartItem.getProduct());
        return dtoCartItem;
    }

    @Override
    public void removeItemFromCart(int cartItemId) {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        Optional <Cart> optionalCart = cartRepository.findByUserId(optionalUser.get().getId());

        if (!(cartItemRepository.findById(cartItemId).get().getCart().getUser().getId() == optionalUser.get().getId())){
            new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(optionalCart.get().getId())));
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void delete(int id) {
        findCartOrThrow(id);
        cartRepository.deleteById(id);
    }

    public Cart findCartOrThrow(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(id))));
    }


}

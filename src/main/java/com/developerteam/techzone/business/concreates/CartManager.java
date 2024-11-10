package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.ICartService;
import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.entities.concreates.Cart;
import com.developerteam.techzone.entities.concreates.CartItem;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartManager implements ICartService {

    private ICartRepository cartRepository;
    private ICartItemRepository cartItemRepository;

    @Autowired
    public CartManager(ICartRepository cartRepository,ICartItemRepository cartItemRepository) {
        this.cartRepository  = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Cart getByUserId(int userId) {
        return cartRepository.findByUserId(userId).get();
    }

    @Override
    public Cart add(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void delete(int id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getOrCreateCart(User user) {
        Optional <Cart> optionalCart = cartRepository.findByUserId(user.getId());
        if(optionalCart.isPresent()){
            return optionalCart.get();
        }else{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        }
    }

    @Override
    public List <CartItem> getCartItemsByUserId(int userId) {
        Optional <Cart> cart = cartRepository.findByUserId(userId);
        return cart.map(value -> cartItemRepository.findByCartId(value.getId())).orElse(null);
    }

    @Override
    public CartItem addItemToCart(CartItem cartItem) {

        Cart cart = getOrCreateCart(cartItem.getCart().getUser());
        Optional <CartItem> optionalCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),cartItem.getProduct().getId());
        if(optionalCartItem.isPresent()){
            CartItem existingCartItem  = optionalCartItem.get();
            existingCartItem.setQuantity(existingCartItem .getQuantity() + cartItem.getQuantity());
            return cartItemRepository.save(existingCartItem);
        }else{
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(cartItem.getProduct());
            newCartItem.setQuantity(cartItem.getQuantity());
            return cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public void removeItemFromCart(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}

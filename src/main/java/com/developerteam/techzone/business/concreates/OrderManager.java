package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IOrderService;
import com.developerteam.techzone.dataAccess.abstracts.ICartItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICartRepository;
import com.developerteam.techzone.dataAccess.abstracts.IOrderItemRepository;
import com.developerteam.techzone.dataAccess.abstracts.IOrderRepository;
import com.developerteam.techzone.entities.concreates.*;
import com.developerteam.techzone.entities.dto.DtoOrder;
import com.developerteam.techzone.entities.dto.DtoOrderIU;
import com.developerteam.techzone.entities.dto.DtoOrderItem;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import com.developerteam.techzone.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderManager implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;

    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IAuthService authService;

    public User findUserOrThrow() {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not exist.")
            );
        }
        return optionalUser.get();
    }


    @Override
    public List<DtoOrder> getAllOrders() {
        User user = findUserOrThrow();
        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<DtoOrder> dtoOrders = new ArrayList<>();
        for (Order order : orders){
            DtoOrder dtoOrder = new DtoOrder();
            BeanUtils.copyProperties(order,dtoOrder);
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<DtoOrderItem> dtoOrderItems = new ArrayList<>();
            for (OrderItem orderItem : orderItems){
                DtoOrderItem dtoOrderItem = new DtoOrderItem();
                BeanUtils.copyProperties(orderItem,dtoOrderItem);
                dtoOrderItems.add(dtoOrderItem);
            }
            dtoOrder.setOrderItems(dtoOrderItems);
            dtoOrders.add(dtoOrder);
        }
        return dtoOrders;
    }

    @Override
    public DtoOrder getOrderById(int id) {
        User user = findUserOrThrow();
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.get().getUser().getId() != user.getId()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not authorize.")
            );
        }

        DtoOrder dtoOrder = new DtoOrder();
        BeanUtils.copyProperties(optionalOrder.get(),dtoOrder);
        return dtoOrder;

    }

    @Override
    @Transactional
    public DtoOrder createOrder(DtoOrderIU dtoOrderIU) {
        User user = findUserOrThrow();
        int cartId = dtoOrderIU.getCartId();
        Optional<Cart> orderCart = cartRepository.findById(cartId);
        if (orderCart.isEmpty()){
            new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(orderCart.get().getId())));
        }
        if(orderCart.get().getUser().getId()!=user.getId()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "Cart has not this user.")
            );
        }
        // `Order` nesnesini oluştur
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(0.0); // İlk başta 0, daha sonra hesaplanacak
        order.setStatus("CREATED");
        order.setCreatedDate(new Date());
        order.setModifiedDate(new Date());

        order = orderRepository.save(order);
        double totalPrice = 0.0;

        List <CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        for (CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice() * quantity);
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            totalPrice += orderItem.getPrice();

        }

        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);

        // DTO'ya dönüştürüp döndür
        DtoOrder dtoOrder = new DtoOrder();
        dtoOrder.setId(order.getId());
        dtoOrder.setTotalPrice(totalPrice);
        dtoOrder.setStatus(order.getStatus());
        dtoOrder.setCreatedDate(order.getCreatedDate());
        dtoOrder.setModifiedDate(order.getModifiedDate());
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        List<DtoOrderItem> dtoOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems){
            DtoOrderItem dtoOrderItem = new DtoOrderItem();
            BeanUtils.copyProperties(orderItem,dtoOrderItem);
            dtoOrderItems.add(dtoOrderItem);
        }
        dtoOrder.setOrderItems(dtoOrderItems);
        cartItemRepository.deleteByCartId(cartId);
        cartRepository.deleteById(cartId);
        return dtoOrder;
    }

    @Override
    public List<DtoOrder> getAllCustomersOrder(){
        List<Order> orderList = orderRepository.findAll();
        List<DtoOrder> dtoOrders = new ArrayList<>();
        for (Order order : orderList){
            DtoOrder dtoOrder = new DtoOrder();
            BeanUtils.copyProperties(order,dtoOrder);
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            List<DtoOrderItem> dtoOrderItems = new ArrayList<>();
            for (OrderItem orderItem : orderItems){
                DtoOrderItem dtoOrderItem = new DtoOrderItem();
                BeanUtils.copyProperties(orderItem,dtoOrderItem);
                dtoOrderItems.add(dtoOrderItem);
            }
            dtoOrder.setOrderItems(dtoOrderItems);
            dtoOrders.add(dtoOrder);
        }
        return dtoOrders;
    }

    @Override
    public DtoOrder getByIdCustomersOrder(int id){
        Order order =orderRepository.findById(id).get();
        DtoOrder dtoOrder = new DtoOrder();
        BeanUtils.copyProperties(order,dtoOrder);
        return dtoOrder;
    }




}

package com.quickserve.Order_Service.Service;

import com.quickserve.Order_Service.Client.RestaurantClient;
import com.quickserve.Order_Service.Dto.OrderRequestDto;
import com.quickserve.Order_Service.Entity.OrderItem;
import com.quickserve.Order_Service.Entity.Orders;
import com.quickserve.Order_Service.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private RestaurantClient restaurantClient; 


    public Orders placeOrder(OrderRequestDto requestDto) {
        Orders order = new Orders();
        order.setCustomerName(requestDto.getCustomerName());
        order.setPhoneNumber(requestDto.getPhoneNumber());
        order.setTableId(requestDto.getTableId());
        order.setStatus("PENDING"); 
        order.setCreatedAt(LocalDateTime.now());

        double calculatedTotalAmount = 0.0;

      
        var orderItems = requestDto.getItems().stream().map(itemDto -> {
         
            Map<String, Object> productResponse = (Map<String, Object>) restaurantClient.getProductById(itemDto.getProductId());
            
            Double price = Double.valueOf(productResponse.get("price").toString());

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDto.getProductId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(price); // Real price set ho gaya
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toList());

      
        for (OrderItem item : orderItems) {
            calculatedTotalAmount += item.getPrice() * item.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(calculatedTotalAmount);

        return orderRepository.save(order);
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }
}
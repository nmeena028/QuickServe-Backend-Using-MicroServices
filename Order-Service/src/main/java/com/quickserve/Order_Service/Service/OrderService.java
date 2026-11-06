package com.quickserve.Order_Service.Service;

import com.quickserve.Order_Service.Client.RestaurantClient;
import com.quickserve.Order_Service.Dto.OrderRequestDto;
import com.quickserve.Order_Service.Entity.OrderItem;
import com.quickserve.Order_Service.Entity.Orders;
import com.quickserve.Order_Service.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private RestaurantClient restaurantClient; 

    public Orders updateOrderStatus(Long orderId, String status) {
      
        Orders order = getOrderById(orderId);
      order.setStatus(status);

       return orderRepository.save(order);
    }

    @Transactional
    public Orders placeOrder(OrderRequestDto requestDto) {
        
        // --- DUPLICATE ORDER CHECK ---
        boolean activeOrderExists = orderRepository.existsByTableIdAndStatus(requestDto.getTableId(), "PENDING");
        if (activeOrderExists) {
            throw new RuntimeException("An active pending order already exists for Table ID: " + requestDto.getTableId());
        }
        // -----------------------------

        Orders order = new Orders();
        order.setCustomerName(requestDto.getCustomerName());
        order.setPhoneNumber(requestDto.getPhoneNumber());
        order.setTableId(requestDto.getTableId());
        order.setStatus("PENDING"); 
        order.setCreatedAt(LocalDateTime.now());

        double calculatedTotalAmount = 0.0;
 
        var orderItems = requestDto.getItems().stream().map(itemDto -> {
            @SuppressWarnings("unchecked")
            Map<String, Object> productResponse = (Map<String, Object>) restaurantClient.getProductById(itemDto.getProductId());
            
            if (productResponse == null) {
                throw new RuntimeException("Product not found with ID: " + itemDto.getProductId());
            }

            // --- COUNTER AVAILABILITY (OUT OF STOCK) CHECK ---
            Boolean isAvailable = true;
            if (productResponse.get("isAvailable") != null) {
                isAvailable = Boolean.valueOf(productResponse.get("isAvailable").toString());
            }

            if (!isAvailable) {
                throw new RuntimeException("Sorry, Product ID " + itemDto.getProductId() + " is currently Out of Stock!");
            }
            // ------------------------------------------------

            Double price = 0.0;
            if (productResponse.get("price") != null) {
                price = Double.valueOf(productResponse.get("price").toString());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDto.getProductId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(price); 
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
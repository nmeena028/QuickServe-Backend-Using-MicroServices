package com.quickserve.Order_Service.Controller;

import com.quickserve.Order_Service.Dto.OrderRequestDto;
import com.quickserve.Order_Service.Entity.Orders;
import com.quickserve.Order_Service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Customer jab table se order place karega
    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(@RequestBody OrderRequestDto requestDto) {
        Orders savedOrder = orderService.placeOrder(requestDto);
        return ResponseEntity.ok(savedOrder);
    }

    // 2. Order ID se status ya details check karne ke liye
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("id") Long id) {
        // Yahan order fetch karne ka logic aayega
        return ResponseEntity.ok().build();
    }
}

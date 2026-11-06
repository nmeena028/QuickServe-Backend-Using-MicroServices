package com.quickserve.Order_Service.Controller;

import com.quickserve.Order_Service.Dto.OrderRequestDto;
import com.quickserve.Order_Service.Entity.Orders;
import com.quickserve.Order_Service.Service.OrderService;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(@RequestBody OrderRequestDto requestDto) {
        Orders savedOrder = orderService.placeOrder(requestDto);
        return ResponseEntity.ok(savedOrder);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("id") Long id) {
         return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Orders> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Orders updatedOrder = orderService.updateOrderStatus(id, status);
        
        return ResponseEntity.ok(updatedOrder);
    }
}

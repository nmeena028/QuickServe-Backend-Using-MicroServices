package com.quickserve.Order_Service.Controller;

import com.quickserve.Order_Service.Dto.OrderRequestDto;
import com.quickserve.Order_Service.Entity.Orders;
import com.quickserve.Order_Service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping("/status")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@RequestParam String status) {
        List<Orders> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Orders>> getOrdersList() {
        List<Orders> orders = orderService.getOrdersList();
        return ResponseEntity.ok(orders);
    }
}

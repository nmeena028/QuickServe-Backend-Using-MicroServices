package com.quickserve.payment_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderClient {

    @PutMapping("/api/orders/{id}/status")
    void updateOrderStatus(@PathVariable("id") Long orderId, @RequestParam("status") String status);
}
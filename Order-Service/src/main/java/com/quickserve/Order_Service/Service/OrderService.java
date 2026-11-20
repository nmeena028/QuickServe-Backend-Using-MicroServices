
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private RestaurantClient restaurantClient;

    // UPDATE ORDER STATUS
    public Orders updateOrderStatus(Long orderId, String status) {

        Orders order = getOrderById(orderId);

        order.setStatus(status);

        return orderRepository.save(order);
    }

    // PLACE ORDER
    @Transactional
    public Orders placeOrder(OrderRequestDto requestDto) {

        // 1. Validate request
        if (requestDto == null || requestDto.getItems() == null
                || requestDto.getItems().isEmpty()) {
            throw new RuntimeException("Order must contain at least one item!");
        }

        // 2. Duplicate pending order check
        boolean activeOrderExists =
                orderRepository.existsByTableIdAndStatus(
                        requestDto.getTableId(), "PENDING"
                );

        if (activeOrderExists) {
            throw new RuntimeException(
                    "An active pending order already exists for Table ID: "
                            + requestDto.getTableId()
            );
        }

        // 3. Create order
        Orders order = new Orders();

        order.setCustomerName(requestDto.getCustomerName());
        order.setPhoneNumber(requestDto.getPhoneNumber());
        order.setTableId(requestDto.getTableId());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        // 4. Fetch products and validate
        List<OrderItem> orderItems = requestDto.getItems()
                .stream()
                .map(itemDto -> {

                    // Validate quantity
                    if (itemDto.getQuantity() <= 0) {
                        throw new RuntimeException(
                                "Product quantity must be greater than zero!"
                        );
                    }

                    // Fetch product from Restaurant Service
                    Object response = restaurantClient.getProductById(
                            itemDto.getProductId()
                    );

                    if (response == null) {
                        throw new RuntimeException(
                                "Product not found with ID: "
                                        + itemDto.getProductId()
                        );
                    }

                    if (!(response instanceof Map)) {
                        throw new RuntimeException(
                                "Invalid product response from Restaurant Service!"
                        );
                    }

                    @SuppressWarnings("unchecked")
                    Map<String, Object> productResponse =
                            (Map<String, Object>) response;

                    // 5. Check product availability
                    Object availability =
                            productResponse.get("isAvailable");

                    // Missing availability means product cannot be ordered
                    if (availability == null) {
                        throw new RuntimeException(
                                "Product availability information missing OR Product is not available !!! Product ID: "
                                        + itemDto.getProductId()
                        );
                    }

                    boolean isAvailable =
                            Boolean.parseBoolean(availability.toString());

                    if (!isAvailable) {
                        throw new RuntimeException(
                                "Sorry, Product ID "
                                        + itemDto.getProductId()
                                        + " is currently Out of Stock!"
                        );
                    }

                    // 6. Get product price
                    Object priceObject = productResponse.get("price");

                    if (priceObject == null) {
                        throw new RuntimeException(
                                "Product price not found for Product ID: "
                                        + itemDto.getProductId()
                        );
                    }

                    double price;

                    try {
                        price = Double.parseDouble(priceObject.toString());
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException(
                                "Invalid product price for Product ID: "
                                        + itemDto.getProductId()
                        );
                    }

                    if (!Double.isFinite(price) || price < 0) {
                        throw new RuntimeException(
                                "Invalid product price for Product ID: "
                                        + itemDto.getProductId()
                        );
                    }

                    // 7. Create order item
                    OrderItem orderItem = new OrderItem();

                    orderItem.setProductId(itemDto.getProductId());
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setPrice(price);
                    orderItem.setOrder(order);

                    return orderItem;

                })
                .collect(Collectors.toList());

        // 8. Calculate total amount
        double calculatedTotalAmount = 0.0;

        for (OrderItem item : orderItems) {

            calculatedTotalAmount +=
                    item.getPrice() * item.getQuantity();
        }

        if (!Double.isFinite(calculatedTotalAmount)) {
            throw new RuntimeException("Invalid order total amount!");
        }

        // 9. Save order details
        order.setOrderItems(orderItems);
        order.setTotalAmount(calculatedTotalAmount);

        return orderRepository.save(order);
    }

    // GET ORDER BY ID
    public Orders getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found with ID: " + id
                        )
                );
    }

    // GET ALL ORDERS
    public List<Orders> getOrdersList() {

        return orderRepository.findAll();
    }

    // GET ORDERS BY STATUS
    public List<Orders> getOrdersByStatus(String status) {

        return orderRepository.findByStatus(status);
    }
}

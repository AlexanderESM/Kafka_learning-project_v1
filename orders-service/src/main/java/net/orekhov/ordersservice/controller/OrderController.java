package net.orekhov.ordersservice.controller;

import net.orekhov.ordersservice.model.Order;
import net.orekhov.ordersservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // Constructor injection for OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST request to create an order
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        // Call the service to create the order
        orderService.createOrder(order);
        return ResponseEntity.ok("Order created and sent to Kafka: " + order.getOrderId());
    }

    // GET request to get order status
    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) {
        // Get order details (mocked in this case)
        Order order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok("Order Status: " + order.getStatus());
    }

    // Additional endpoints can be added here (e.g., updating orders)
}

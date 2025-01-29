package net.orekhov.ordersservice.service;

import net.orekhov.ordersservice.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Constructor injection of KafkaTemplate for producing messages
    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Create an order and send order details to Kafka
    public void createOrder(Order order) {
        // Convert Order object to JSON or String format (you can use libraries like Jackson for JSON)
        String orderDetails = "OrderID: " + order.getOrderId() +
                ", Product: " + order.getProduct() +
                ", Quantity: " + order.getQuantity() +
                ", Price: " + order.getPrice() +
                ", Status: " + order.getStatus();

        // Send the order details to Kafka topic
        kafkaTemplate.send("orders", orderDetails);

        // You can add additional logic here like saving the order to a database if needed
        System.out.println("Order created and sent to Kafka: " + orderDetails);
    }

    // Example method to update the order status
    public void updateOrderStatus(String orderId, String status) {
        // Here you would typically interact with a database to update the status
        // But for this example, we're just printing out the status update.
        System.out.println("Updating order " + orderId + " status to " + status);
    }

    // Example method to retrieve order details (this could interact with a database)
    public Order getOrderDetails(String orderId) {
        // This is just a mock implementation, you would replace it with real database logic
        return new Order(orderId, "customer123", "Laptop", 2, 1500.00, "Created");
    }
}

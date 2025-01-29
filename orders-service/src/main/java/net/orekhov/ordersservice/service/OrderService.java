package net.orekhov.ordersservice.service;

import net.orekhov.ordersservice.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки заказов. Этот сервис отвечает за создание заказов, обновление статусов заказов
 * и отправку данных о заказах в Kafka.
 */
@Service
public class OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate; // KafkaTemplate для отправки сообщений в Kafka

    /**
     * Конструктор для внедрения зависимости KafkaTemplate.
     *
     * @param kafkaTemplate KafkaTemplate для отправки сообщений в Kafka
     */
    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Метод для создания заказа и отправки его данных в Kafka.
     * Преобразует объект заказа в строку и отправляет его в Kafka topic.
     *
     * @param order Объект заказа, который нужно создать
     */
    public void createOrder(Order order) {
        // Преобразование объекта заказа в строку (можно использовать Jackson для преобразования в JSON)
        String orderDetails = "OrderID: " + order.getOrderId() +
                ", Product: " + order.getProduct() +
                ", Quantity: " + order.getQuantity() +
                ", Price: " + order.getPrice() +
                ", Status: " + order.getStatus();

        // Отправка данных о заказе в Kafka в topic "orders"
        kafkaTemplate.send("orders", orderDetails);

        // Логирование информации о созданном заказе
        System.out.println("Order created and sent to Kafka: " + orderDetails);

        // Дополнительно можно добавить логику для сохранения заказа в базе данных
    }

    /**
     * Метод для обновления статуса заказа.
     * В реальном приложении этот метод должен взаимодействовать с базой данных.
     *
     * @param orderId Идентификатор заказа
     * @param status Новый статус заказа
     */
    public void updateOrderStatus(String orderId, String status) {
        // В реальном приложении здесь будет логика для обновления статуса в базе данных
        // Для этого примера выводим сообщение в консоль
        System.out.println("Updating order " + orderId + " status to " + status);
    }

    /**
     * Метод для получения данных о заказе. В реальном приложении этот метод должен взаимодействовать с базой данных.
     *
     * @param orderId Идентификатор заказа
     * @return Объект заказа с мокированными данными
     */
    public Order getOrderDetails(String orderId) {
        // Мокированная реализация, в реальном приложении нужно будет получать данные из базы данных
        return new Order(orderId, "customer123", "Laptop", 2, 1500.00, "Created");
    }
}

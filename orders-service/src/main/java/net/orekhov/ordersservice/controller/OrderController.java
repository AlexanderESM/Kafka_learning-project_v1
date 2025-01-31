package net.orekhov.ordersservice.controller;

import net.orekhov.ordersservice.model.Order;
import net.orekhov.ordersservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов, связанных с заказами.
 * Этот контроллер предоставляет API для создания заказов и получения статуса заказов.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    /**
     * Конструктор для внедрения зависимостей (OrderService).
     *
     * @param orderService Сервис для обработки логики заказов.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Обработчик POST запроса для создания нового заказа.
     * Принимает объект заказа в теле запроса, вызывает сервис для его создания и отправки в Kafka.
     *
     * @param order Объект заказа, который будет создан.
     * @return Ответ с сообщением, подтверждающим создание заказа и его отправку в Kafka.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        logger.info("Creating order with ID: {}", order.getOrderId());

        // Вызов метода сервиса для создания заказа
        orderService.createOrder(order);

        logger.info("Order with ID: {} created and sent to Kafka.", order.getOrderId());

        // Возвращаем ответ с сообщением о успешном создании заказа
        return ResponseEntity.ok("Order created and sent to Kafka: " + order.getOrderId());
    }

    /**
     * Обработчик GET запроса для получения статуса заказа по его ID.
     *
     * @param orderId Идентификатор заказа, статус которого нужно получить.
     * @return Ответ с текущим статусом заказа.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String orderId) {
        logger.info("Fetching status for order with ID: {}", orderId);

        // Получаем данные о заказе через сервис
        Order order = orderService.getOrderDetails(orderId);

        logger.info("Order ID: {} has status: {}", orderId, order.getStatus());

        // Возвращаем ответ с статусом заказа
        return ResponseEntity.ok("Order Status: " + order.getStatus());
    }

    // В будущем можно добавлять дополнительные эндпоинты, например, для обновления заказа
}

package net.orekhov.usersimulator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserSimulationService {
    private static final Logger logger = LoggerFactory.getLogger(UserSimulationService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String ORDER_TOPIC = "order-topic";
    private static final String PAYMENT_TOPIC = "payment-topic";
    private static final String SHIPPING_TOPIC = "shipping-topic";
    private static final String NOTIFICATIONS_TOPIC = "notifications-topic";

    /**
     * Симуляция пользовательского потока.
     * Вместо HTTP вызовов, отправляем сообщения в Kafka.
     */
    public void simulateUserFlow() {
        // Этап 1: Создание заказа
        Long orderId = createOrder();
        if (orderId == null) {
            logger.error("Order creation failed.");
            return;
        }
        sendMessageToKafka(ORDER_TOPIC, "Order created with ID: " + orderId);
        logger.info("Order creation successful. Order ID: {}", orderId);

        // Этап 2: Обработка платежа
        boolean paymentSuccess = processPayment(orderId);
        if (!paymentSuccess) {
            logger.error("Payment failed for Order ID: " + orderId);
            return;
        }
        sendMessageToKafka(PAYMENT_TOPIC, "Payment processed for Order ID: " + orderId);
        logger.info("Payment processed successfully for Order ID: {}", orderId);

        // Этап 3: Отслеживание доставки
        String trackingNumber = trackShipment(orderId);
        if (trackingNumber == null) {
            logger.error("Shipment tracking failed for Order ID: " + orderId);
            return;
        }
        sendMessageToKafka(SHIPPING_TOPIC, "Shipment tracked for Order ID: " + orderId + ", Tracking Number: " + trackingNumber);
        logger.info("Shipment successfully tracked for Order ID: {}. Tracking Number: {}", orderId, trackingNumber);

        // Этап 4: Получение уведомлений
        receiveNotification(orderId, trackingNumber);
        logger.info("Notification sent for Order ID: {}. Tracking Number: {}", orderId, trackingNumber);
    }

    /**
     * Отправка сообщения в Kafka с использованием KafkaTemplate.
     * Логирование успешных и неуспешных отправок.
     *
     * @param topic Тема Kafka, куда отправляется сообщение.
     * @param message Сообщение для отправки.
     */
    private void sendMessageToKafka(String topic, String message) {
        kafkaTemplate.send(topic, message);
        logger.debug("Message is being sent to Kafka topic {}: {}", topic, message); // Логирование отправки
    }

    /**
     * Метод для создания заказа (например, с использованием UUID).
     * Примерная логика создания заказа.
     * @return ID заказа.
     */
    private Long createOrder() {
        // Пример успешного создания заказа с уникальным ID
        Long orderId = System.currentTimeMillis(); // Используем текущий timestamp для простоты
        logger.info("Order created successfully with ID: {}", orderId);
        return orderId;
    }

    /**
     * Метод для обработки платежа.
     * В реальной системе логика будет взаимодействовать с платёжной системой.
     * @param orderId ID заказа.
     * @return true, если платеж был успешен.
     */
    private boolean processPayment(Long orderId) {
        // Пример успешного платежа
        boolean paymentSuccess = true; // В реальной системе будет запрос к платёжной системе
        if (paymentSuccess) {
            logger.info("Payment processed successfully for Order ID: {}", orderId);
        } else {
            logger.error("Payment failed for Order ID: {}", orderId);
        }
        return paymentSuccess;
    }

    /**
     * Метод для отслеживания доставки.
     * В реальной системе будет запрос к курьерской службе.
     * @param orderId ID заказа.
     * @return Номер отслеживания (tracking number).
     */
    private String trackShipment(Long orderId) {
        // Пример успешного отслеживания доставки
        String trackingNumber = "TRK" + orderId; // Для упрощения добавляем ID заказа в номер отслеживания
        logger.info("Shipment tracked successfully for Order ID: {}. Tracking Number: {}", orderId, trackingNumber);
        return trackingNumber;
    }

    /**
     * Метод для получения уведомлений.
     * В реальной системе это может быть отправка уведомлений пользователю или другой бизнес-логикой.
     * @param orderId ID заказа.
     * @param trackingNumber Номер отслеживания.
     */
    private void receiveNotification(Long orderId, String trackingNumber) {
        // Пример уведомления пользователя
        String message = "Notification sent for Order ID: " + orderId + ", Tracking Number: " + trackingNumber;
        logger.info("Sending notification: {}", message);
        sendMessageToKafka(NOTIFICATIONS_TOPIC, message);
    }
}

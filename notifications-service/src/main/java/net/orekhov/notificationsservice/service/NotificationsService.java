package net.orekhov.notificationsservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки уведомлений.
 * Этот сервис слушает сообщения из Kafka и отправляет уведомления пользователям.
 */
@Service
public class NotificationsService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Конструктор для инициализации сервиса с передачей KafkaTemplate.
     * KafkaTemplate используется для отправки сообщений в Kafka, если это необходимо.
     *
     * @param kafkaTemplate Шаблон Kafka для отправки сообщений.
     */
    public NotificationsService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Метод, который слушает сообщения на Kafka топике "orders".
     * Этот метод будет автоматически вызван, когда появится новое сообщение в топике.
     *
     * @param message Сообщение, полученное из Kafka.
     */
    @KafkaListener(topics = "orders", groupId = "notifications-group")
    public void listen(String message) {
        // Логируем сообщение, полученное из Kafka
        logger.info("Received order notification: {}", message);

        // Логика обработки уведомления
        // Например, отправка уведомления пользователю по email, SMS и т.д.
        sendNotificationToUser(message);
    }

    /**
     * Приватный метод для отправки уведомления пользователю.
     * Здесь можно интегрировать различные способы отправки уведомлений, такие как email, SMS и другие.
     *
     * @param message Сообщение для отправки пользователю.
     */
    private void sendNotificationToUser(String message) {
        // Логируем процесс отправки уведомления
        logger.info("Sending notification to user: {}", message);

        // Реализация отправки уведомлений (например, email, SMS и т.д.)
        // В реальной ситуации, вы можете интегрировать сервисы отправки.
        // Здесь будет вывод на консоль для примера.
        System.out.println("Notification sent to user: " + message);
    }
}

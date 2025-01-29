package net.orekhov.notificationsservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки уведомлений.
 * Этот сервис слушает сообщения из Kafka и отправляет уведомления пользователям.
 */
@Service
public class NotificationsService {

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
        System.out.println("Received order notification: " + message);

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
        // В реальном приложении можно интегрировать сервисы для отправки email, SMS и т.д.
        System.out.println("Sending notification to user: " + message);
    }
}

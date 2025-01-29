package net.orekhov.notificationsservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationsService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Метод для получения уведомлений из Kafka
    @KafkaListener(topics = "orders", groupId = "notifications-group")
    public void listen(String message) {
        // Пример обработки сообщения
        System.out.println("Received order notification: " + message);

        // Логика обработки уведомления, например, отправка email, SMS и т.д.
        sendNotificationToUser(message);
    }

    // Пример метода для отправки уведомления
    private void sendNotificationToUser(String message) {
        // Здесь можно интегрировать отправку уведомлений (например, email, SMS)
        System.out.println("Sending notification to user: " + message);
    }
}

package net.orekhov.notificationsservice.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с уведомлениями.
 * Используется для отправки сообщений в Kafka на тему "notifications".
 */
@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Конструктор для инициализации контроллера с передачей KafkaTemplate.
     * KafkaTemplate используется для отправки сообщений в Kafka.
     *
     * @param kafkaTemplate Шаблон Kafka для отправки сообщений.
     */
    public NotificationsController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Endpoint для отправки уведомлений.
     * Получает сообщение как параметр и отправляет его в Kafka на тему "notifications".
     *
     * @param message Сообщение, которое будет отправлено в Kafka.
     * @return Строка, подтверждающая отправку сообщения.
     */
    @PostMapping("/send")
    public String sendNotification(@RequestParam String message) {
        // Отправляем сообщение в Kafka, в тему "notifications"
        kafkaTemplate.send("notifications", message);

        // Возвращаем подтверждение об отправке уведомления
        return "Notification sent: " + message;
    }
}

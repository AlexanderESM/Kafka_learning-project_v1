package net.orekhov.notificationsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с уведомлениями.
 * Используется для отправки сообщений в Kafka на тему "notifications".
 */
@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    // Логгер для логирования событий в этом контроллере
    private static final Logger logger = LoggerFactory.getLogger(NotificationsController.class);

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
        // Логируем начало отправки уведомления
        logger.info("Sending notification message: {}", message);

        try {
            // Отправляем сообщение в Kafka, в тему "notifications"
            kafkaTemplate.send("notifications", message);

            // Логируем успешную отправку
            logger.info("Notification successfully sent: {}", message);
        } catch (Exception e) {
            // Логируем ошибку при отправке сообщения
            logger.error("Failed to send notification message: {}", message, e);
        }

        // Возвращаем подтверждение об отправке уведомления
        return "Notification sent: " + message;
    }
}

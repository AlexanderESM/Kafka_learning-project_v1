package net.orekhov.notificationsservice.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationsController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String message) {
        kafkaTemplate.send("notifications", message);
        return "Notification sent: " + message;
    }
}

package net.orekhov.usersimulator.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис, который симулирует поток пользователя через несколько сервисов в системе электронной коммерции.
 * Включает создание заказа, обработку оплаты, отслеживание доставки и получение уведомлений.
 */
@Service
public class UserSimulationService {
    private static final Logger logger = LoggerFactory.getLogger(UserSimulationService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    // URL для взаимодействия с внешними сервисами
    private final String ORDERS_SERVICE_URL = "http://localhost:8081/orders";
    private final String PAYMENT_SERVICE_URL = "http://localhost:8082/payments";
    private final String SHIPPING_SERVICE_URL = "http://localhost:8083/shipping";
    private final String NOTIFICATIONS_SERVICE_URL = "http://localhost:8084/notifications";

    /**
     * Симулирует полный поток пользователя в системе электронной коммерции:
     * 1. Создание заказа
     * 2. Обработка оплаты
     * 3. Отслеживание доставки
     * 4. Получение уведомлений
     */
    public void simulateUserFlow() {
        Long orderId = createOrder();
        if (orderId == null) return; // Если создание заказа не удалось, завершаем поток

        boolean paymentSuccess = processPayment(orderId);
        if (!paymentSuccess) return; // Если оплата не прошла, завершаем поток

        String trackingNumber = trackShipment(orderId);
        if (trackingNumber == null) return; // Если отслеживание доставки не удалось, завершаем поток

        receiveNotification(orderId, trackingNumber);
    }

    /**
     * Создаёт заказ, отправляя POST-запрос в сервис заказов.
     * @return ID созданного заказа или null, если произошла ошибка.
     */
    private Long createOrder() {
        logger.info("Пользователь создаёт заказ...");
        Map<String, Object> request = new HashMap<>();
        request.put("userId", 1); // Хардкодированный ID пользователя (можно сделать динамичным)
        request.put("productId", 101); // Хардкодированный ID продукта (можно сделать динамичным)
        request.put("quantity", 1); // Хардкодированное количество (можно сделать динамичным)

        try {
            // Отправка POST-запроса для создания заказа
            Long orderId = restTemplate.postForObject(ORDERS_SERVICE_URL, request, Long.class);
            logger.info("Заказ создан, ID: {}", orderId);
            return orderId; // Возвращаем ID заказа, если создание прошло успешно
        } catch (Exception e) {
            logger.error("Ошибка при создании заказа: {}", e.getMessage());
            return null; // Возвращаем null, если возникла ошибка при создании заказа
        }
    }

    /**
     * Обрабатывает оплату для заданного заказа, отправляя POST-запрос в сервис оплаты.
     * @param orderId ID заказа, для которого необходимо провести оплату.
     * @return true, если оплата прошла успешно, false в противном случае.
     */
    private boolean processPayment(Long orderId) {
        logger.info("Пользователь оплачивает заказ ID: {}", orderId);
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", orderId);
        request.put("amount", 100.0); // Хардкодированная сумма (можно сделать динамичной)

        try {
            // Отправка POST-запроса для обработки оплаты
            restTemplate.postForObject(PAYMENT_SERVICE_URL, request, Void.class);
            logger.info("Заказ ID: {} успешно оплачен.", orderId);
            return true; // Возвращаем true, если оплата прошла успешно
        } catch (Exception e) {
            logger.error("Ошибка при оплате заказа ID {}: {}", orderId, e.getMessage());
            return false; // Возвращаем false, если возникла ошибка при оплате
        }
    }

    /**
     * Отслеживает доставку заказа, отправляя GET-запрос в сервис доставки.
     * @param orderId ID заказа, для которого необходимо отслеживать доставку.
     * @return номер отслеживания, если удалось получить информацию, или null в случае ошибки.
     */
    private String trackShipment(Long orderId) {
        logger.info("Пользователь отслеживает доставку заказа ID: {}", orderId);
        try {
            // Отправка GET-запроса для отслеживания доставки
            String trackingNumber = restTemplate.getForObject(SHIPPING_SERVICE_URL + "/track?orderId=" + orderId, String.class);
            logger.info("Заказ ID: {} отправлен, номер отслеживания: {}", orderId, trackingNumber);
            return trackingNumber; // Возвращаем номер отслеживания, если всё прошло успешно
        } catch (Exception e) {
            logger.error("Ошибка при отслеживании заказа ID {}: {}", orderId, e.getMessage());
            return null; // Возвращаем null, если отслеживание не удалось
        }
    }

    /**
     * Получает уведомление по заказу, отправляя GET-запрос в сервис уведомлений.
     * @param orderId ID заказа, для которого необходимо получить уведомление.
     * @param trackingNumber номер отслеживания, связанный с заказом.
     */
    private void receiveNotification(Long orderId, String trackingNumber) {
        logger.info("Пользователь получает уведомление по заказу ID: {}", orderId);
        try {
            // Отправка GET-запроса для получения уведомления
            String notification = restTemplate.getForObject(NOTIFICATIONS_SERVICE_URL + "/check?orderId=" + orderId, String.class);
            logger.info("Получено уведомление: {}", notification);
        } catch (Exception e) {
            logger.error("Ошибка при получении уведомления для заказа ID {}: {}", orderId, e.getMessage());
        }
    }
}

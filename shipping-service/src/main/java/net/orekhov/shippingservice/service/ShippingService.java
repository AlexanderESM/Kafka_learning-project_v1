package net.orekhov.shippingservice.service;

import net.orekhov.shippingservice.model.Shipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для обработки операций с отправками.
 * Включает методы для создания, получения, обновления и удаления отправок.
 */
@Service
public class ShippingService {

    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class); // Логгер для класса

    // Хранилище для отправок (заменяет базу данных)
    private final Map<Long, Shipment> shipments = new HashMap<>();
    private long shipmentIdCounter = 1; // Счётчик для генерации уникальных идентификаторов отправок

    /**
     * Создаёт новую отправку для заказа.
     *
     * @param orderId       Идентификатор заказа
     * @param shippingMethod Метод доставки (например, "экспресс", "стандарт")
     * @return Созданную отправку
     */
    public Shipment createShipment(Long orderId, String shippingMethod) {
        logger.info("Creating shipment for order ID: {} with shipping method: {}", orderId, shippingMethod); // Логируем начало создания отправки

        String trackingNumber = generateTrackingNumber(); // Генерация номера отслеживания
        Shipment shipment = new Shipment(
                shipmentIdCounter++, // Уникальный идентификатор для отправки
                orderId,
                trackingNumber,
                shippingMethod,
                "Pending", // Начальный статус
                LocalDate.now(), // Дата отправки — сегодняшняя
                null // Дата доставки ещё не установлена
        );
        shipments.put(shipment.getShipmentId(), shipment); // Сохранение отправки в хранилище

        logger.info("Shipment created successfully with ID: {} and tracking number: {}", shipment.getShipmentId(), trackingNumber); // Логируем успешное создание
        return shipment;
    }

    /**
     * Получает информацию о отправке по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return Объект Optional, содержащий информацию о отправке, если она существует
     */
    public Optional<Shipment> getShipmentDetails(Long shipmentId) {
        logger.debug("Fetching shipment details for shipment ID: {}", shipmentId); // Логируем запрос на получение данных о отправке
        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            logger.info("Shipment found: {}", shipment); // Логируем информацию о найденной отправке
        } else {
            logger.warn("Shipment with ID {} not found", shipmentId); // Логируем предупреждение, если отправка не найдена
        }
        return Optional.ofNullable(shipment); // Возвращаем Optional для предотвращения NullPointerException
    }

    /**
     * Получает статус отправки по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return Optional, содержащий статус отправки, если отправка найдена
     */
    public Optional<String> getShipmentStatus(Long shipmentId) {
        logger.debug("Fetching shipment status for shipment ID: {}", shipmentId); // Логируем запрос на получение статуса отправки
        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            logger.info("Shipment status: {}", shipment.getStatus()); // Логируем статус отправки
        } else {
            logger.warn("Shipment with ID {} not found", shipmentId); // Логируем предупреждение
        }
        return shipment != null ? Optional.of(shipment.getStatus()) : Optional.empty(); // Если отправка найдена, возвращаем статус
    }

    /**
     * Обновляет статус отправки. Если статус "Доставлено", также устанавливается дата доставки.
     *
     * @param shipmentId Идентификатор отправки
     * @param status     Новый статус отправки
     * @return Обновлённую отправку, если она найдена
     */
    public Optional<Shipment> updateShipmentStatus(Long shipmentId, String status) {
        logger.info("Updating status of shipment ID: {} to {}", shipmentId, status); // Логируем изменение статуса

        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            shipment.setStatus(status);
            if ("Delivered".equals(status)) {
                shipment.setDeliveryDate(LocalDate.now()); // Устанавливаем дату доставки, если статус "Доставлено"
                logger.info("Shipment ID: {} marked as delivered on {}", shipmentId, shipment.getDeliveryDate()); // Логируем дату доставки
            }
            return Optional.of(shipment); // Возвращаем обновлённую отправку
        }

        logger.warn("Shipment with ID {} not found for status update", shipmentId); // Логируем предупреждение
        return Optional.empty(); // Если отправка не найдена, возвращаем пустой Optional
    }

    /**
     * Удаляет отправку по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return true, если отправка была удалена, иначе false
     */
    public boolean deleteShipment(Long shipmentId) {
        logger.info("Deleting shipment with ID: {}", shipmentId); // Логируем удаление отправки
        boolean isDeleted = shipments.remove(shipmentId) != null;
        if (isDeleted) {
            logger.info("Shipment ID: {} deleted successfully", shipmentId); // Логируем успешное удаление
        } else {
            logger.warn("Shipment ID: {} not found for deletion", shipmentId); // Логируем предупреждение
        }
        return isDeleted; // Возвращаем результат удаления
    }

    /**
     * Генерирует случайный номер отслеживания для отправки.
     *
     * @return Сгенерированный номер отслеживания
     */
    private String generateTrackingNumber() {
        String trackingNumber = "TRK" + (100000 + (int)(Math.random() * 900000)); // Генерация случайного числа для номера отслеживания
        logger.debug("Generated tracking number: {}", trackingNumber); // Логируем номер отслеживания
        return trackingNumber;
    }
}

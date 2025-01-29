package net.orekhov.shippingservice.service;

import net.orekhov.shippingservice.model.Shipment;
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
        return shipment;
    }

    /**
     * Получает информацию о отправке по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return Объект Optional, содержащий информацию о отправке, если она существует
     */
    public Optional<Shipment> getShipmentDetails(Long shipmentId) {
        return Optional.ofNullable(shipments.get(shipmentId)); // Возвращает Optional для предотвращения NullPointerException
    }

    /**
     * Получает статус отправки по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return Optional, содержащий статус отправки, если отправка найдена
     */
    public Optional<String> getShipmentStatus(Long shipmentId) {
        Shipment shipment = shipments.get(shipmentId);
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
        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            shipment.setStatus(status);
            if (status.equals("Delivered")) {
                shipment.setDeliveryDate(LocalDate.now()); // Устанавливаем дату доставки, если статус "Доставлено"
            }
            return Optional.of(shipment); // Возвращаем обновлённую отправку
        }
        return Optional.empty(); // Если отправка не найдена, возвращаем пустой Optional
    }

    /**
     * Удаляет отправку по её идентификатору.
     *
     * @param shipmentId Идентификатор отправки
     * @return true, если отправка была удалена, иначе false
     */
    public boolean deleteShipment(Long shipmentId) {
        return shipments.remove(shipmentId) != null; // Удаляем отправку из хранилища
    }

    /**
     * Генерирует случайный номер отслеживания для отправки.
     *
     * @return Сгенерированный номер отслеживания
     */
    private String generateTrackingNumber() {
        return "TRK" + (100000 + (int)(Math.random() * 900000)); // Генерация случайного числа для номера отслеживания
    }
}

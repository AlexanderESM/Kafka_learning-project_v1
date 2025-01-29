package net.orekhov.shippingservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Модель для представления отправки.
 * Содержит информацию о доставке, такую как номер отслеживания, метод доставки, статус и даты.
 */
public class Shipment {

    private static final Logger logger = LoggerFactory.getLogger(Shipment.class); // Логгер для класса

    private Long shipmentId; // Уникальный идентификатор отправки
    private Long orderId; // Идентификатор заказа, связанного с отправкой
    private String trackingNumber; // Номер отслеживания для посылки
    private String shippingMethod; // Метод доставки (например, "экспресс", "стандарт")
    private String status; // Статус отправки (например, "отправлено", "в пути", "доставлено")
    private LocalDate shippingDate; // Дата отправки
    private LocalDate deliveryDate; // Дата доставки

    /**
     * Конструктор без параметров для создания пустого объекта Shipment.
     */
    public Shipment() {
        logger.debug("Creating an empty shipment object"); // Логируем создание пустого объекта
    }

    /**
     * Конструктор с полями для инициализации объекта Shipment.
     *
     * @param shipmentId    Уникальный идентификатор отправки
     * @param orderId       Идентификатор связанного заказа
     * @param trackingNumber Номер отслеживания
     * @param shippingMethod Метод доставки
     * @param status        Статус отправки
     * @param shippingDate  Дата отправки
     * @param deliveryDate  Дата доставки
     */
    public Shipment(Long shipmentId, Long orderId, String trackingNumber, String shippingMethod, String status, LocalDate shippingDate, LocalDate deliveryDate) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.shippingMethod = shippingMethod;
        this.status = status;
        this.shippingDate = shippingDate;
        this.deliveryDate = deliveryDate;

        logger.info("Shipment created with ID: {}, Order ID: {}, Tracking Number: {}, Shipping Method: {}, Status: {}, Shipping Date: {}, Delivery Date: {}",
                shipmentId, orderId, trackingNumber, shippingMethod, status, shippingDate, deliveryDate); // Логируем создание отправки
    }

    // Getters и setters для всех полей

    /**
     * Получить уникальный идентификатор отправки.
     *
     * @return Идентификатор отправки
     */
    public Long getShipmentId() {
        return shipmentId;
    }

    /**
     * Установить уникальный идентификатор отправки.
     *
     * @param shipmentId Идентификатор отправки
     */
    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
        logger.debug("Shipment ID set to: {}", shipmentId); // Логируем установку ID отправки
    }

    /**
     * Получить идентификатор заказа, связанного с отправкой.
     *
     * @return Идентификатор заказа
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Установить идентификатор заказа, связанного с отправкой.
     *
     * @param orderId Идентификатор заказа
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
        logger.debug("Order ID set to: {}", orderId); // Логируем установку ID заказа
    }

    /**
     * Получить номер отслеживания отправки.
     *
     * @return Номер отслеживания
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Установить номер отслеживания отправки.
     *
     * @param trackingNumber Номер отслеживания
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        logger.debug("Tracking number set to: {}", trackingNumber); // Логируем установку номера отслеживания
    }

    /**
     * Получить метод доставки.
     *
     * @return Метод доставки
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Установить метод доставки.
     *
     * @param shippingMethod Метод доставки
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
        logger.debug("Shipping method set to: {}", shippingMethod); // Логируем установку метода доставки
    }

    /**
     * Получить статус отправки.
     *
     * @return Статус отправки
     */
    public String getStatus() {
        return status;
    }

    /**
     * Установить статус отправки.
     *
     * @param status Статус отправки
     */
    public void setStatus(String status) {
        this.status = status;
        logger.debug("Status set to: {}", status); // Логируем установку статуса
    }

    /**
     * Получить дату отправки.
     *
     * @return Дата отправки
     */
    public LocalDate getShippingDate() {
        return shippingDate;
    }

    /**
     * Установить дату отправки.
     *
     * @param shippingDate Дата отправки
     */
    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        logger.debug("Shipping date set to: {}", shippingDate); // Логируем установку даты отправки
    }

    /**
     * Получить дату доставки.
     *
     * @return Дата доставки
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Установить дату доставки.
     *
     * @param deliveryDate Дата доставки
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        logger.debug("Delivery date set to: {}", deliveryDate); // Логируем установку даты доставки
    }

    /**
     * Переопределение метода toString для вывода информации об отправке.
     *
     * @return Строковое представление объекта Shipment
     */
    @Override
    public String toString() {
        String shipmentDetails = "Shipment{" +
                "shipmentId=" + shipmentId +
                ", orderId=" + orderId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", status='" + status + '\'' +
                ", shippingDate=" + shippingDate +
                ", deliveryDate=" + deliveryDate +
                '}';

        logger.info("Shipment toString: {}", shipmentDetails); // Логируем строковое представление отправки
        return shipmentDetails;
    }
}

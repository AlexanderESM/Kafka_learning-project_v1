package net.orekhov.shippingservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Модель для представления заказа на доставку.
 * Содержит информацию о заказе и методе доставки.
 */
public class ShippingOrder {

    private static final Logger logger = LoggerFactory.getLogger(ShippingOrder.class); // Логгер для класса

    private Long orderId; // Идентификатор заказа
    private String shippingMethod; // Метод доставки (например, "экспресс", "стандарт")

    /**
     * Конструктор для создания нового заказа на доставку.
     *
     * @param orderId       Идентификатор заказа
     * @param shippingMethod Метод доставки (например, "экспресс", "стандарт")
     */
    public ShippingOrder(Long orderId, String shippingMethod) {
        this.orderId = orderId;
        this.shippingMethod = shippingMethod;
        logger.info("ShippingOrder created: OrderId = {}, ShippingMethod = {}", orderId, shippingMethod); // Логируем создание объекта
    }

    /**
     * Получить идентификатор заказа.
     *
     * @return Идентификатор заказа
     */
    public Long getOrderId() {
        logger.debug("Getting orderId: {}", orderId); // Логируем получение значения
        return orderId;
    }

    /**
     * Установить идентификатор заказа.
     *
     * @param orderId Идентификатор заказа
     */
    public void setOrderId(Long orderId) {
        logger.info("Setting orderId to: {}", orderId); // Логируем изменение поля
        this.orderId = orderId;
    }

    /**
     * Получить метод доставки.
     *
     * @return Метод доставки
     */
    public String getShippingMethod() {
        logger.debug("Getting shippingMethod: {}", shippingMethod); // Логируем получение значения
        return shippingMethod;
    }

    /**
     * Установить метод доставки.
     *
     * @param shippingMethod Метод доставки
     */
    public void setShippingMethod(String shippingMethod) {
        logger.info("Setting shippingMethod to: {}", shippingMethod); // Логируем изменение поля
        this.shippingMethod = shippingMethod;
    }
}

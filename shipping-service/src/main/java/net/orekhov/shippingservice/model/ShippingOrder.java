package net.orekhov.shippingservice.model;

/**
 * Модель для представления заказа на доставку.
 * Содержит информацию о заказе и методе доставки.
 */
public class ShippingOrder {

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
    }

    /**
     * Получить идентификатор заказа.
     *
     * @return Идентификатор заказа
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Установить идентификатор заказа.
     *
     * @param orderId Идентификатор заказа
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    }
}

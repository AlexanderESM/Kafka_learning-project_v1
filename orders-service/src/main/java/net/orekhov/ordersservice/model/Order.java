package net.orekhov.ordersservice.model;

/**
 * Класс, представляющий заказ.
 * Этот класс содержит информацию о заказе, такую как идентификатор заказа, идентификатор клиента, товар,
 * количество, цена и статус заказа.
 */
public class Order {

    private String orderId; // Идентификатор заказа
    private String customerId; // Идентификатор клиента
    private String product; // Название товара
    private int quantity; // Количество товара
    private double price; // Цена товара
    private String status; // Статус заказа (например, "в обработке", "отправлен", "доставлен")

    /**
     * Конструктор для создания нового объекта заказа.
     *
     * @param orderId    Идентификатор заказа
     * @param customerId Идентификатор клиента
     * @param product    Название товара
     * @param quantity   Количество товара
     * @param price      Цена товара
     * @param status     Статус заказа
     */
    public Order(String orderId, String customerId, String product, int quantity, double price, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    /**
     * Получить идентификатор заказа.
     *
     * @return Идентификатор заказа
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Установить идентификатор заказа.
     *
     * @param orderId Идентификатор заказа
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Получить идентификатор клиента.
     *
     * @return Идентификатор клиента
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Установить идентификатор клиента.
     *
     * @param customerId Идентификатор клиента
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Получить название товара.
     *
     * @return Название товара
     */
    public String getProduct() {
        return product;
    }

    /**
     * Установить название товара.
     *
     * @param product Название товара
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Получить количество товара.
     *
     * @return Количество товара
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Установить количество товара.
     *
     * @param quantity Количество товара
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Получить цену товара.
     *
     * @return Цена товара
     */
    public double getPrice() {
        return price;
    }

    /**
     * Установить цену товара.
     *
     * @param price Цена товара
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Получить статус заказа.
     *
     * @return Статус заказа
     */
    public String getStatus() {
        return status;
    }

    /**
     * Установить статус заказа.
     *
     * @param status Статус заказа
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Переопределение метода toString для удобного вывода информации о заказе.
     *
     * @return Строковое представление заказа
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}

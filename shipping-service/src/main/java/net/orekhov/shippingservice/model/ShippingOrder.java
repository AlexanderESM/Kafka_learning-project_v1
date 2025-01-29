package net.orekhov.shippingservice.model;

public class ShippingOrder {

    private Long orderId;
    private String shippingMethod;

    // Constructor
    public ShippingOrder(Long orderId, String shippingMethod) {
        this.orderId = orderId;
        this.shippingMethod = shippingMethod;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}

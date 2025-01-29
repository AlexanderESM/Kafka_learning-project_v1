package net.orekhov.shippingservice.model;

import java.time.LocalDate;

public class Shipment {

    private Long shipmentId;
    private Long orderId;
    private String trackingNumber;
    private String shippingMethod;
    private String status;
    private LocalDate shippingDate;
    private LocalDate deliveryDate;

    // Default constructor
    public Shipment() {
    }

    // Constructor with all fields
    public Shipment(Long shipmentId, Long orderId, String trackingNumber, String shippingMethod, String status, LocalDate shippingDate, LocalDate deliveryDate) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.shippingMethod = shippingMethod;
        this.status = status;
        this.shippingDate = shippingDate;
        this.deliveryDate = deliveryDate;
    }

    // Getters and setters
    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId=" + shipmentId +
                ", orderId=" + orderId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", status='" + status + '\'' +
                ", shippingDate=" + shippingDate +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}

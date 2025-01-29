package net.orekhov.shippingservice.service;

import net.orekhov.shippingservice.model.Shipment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShippingService {

    private final Map<Long, Shipment> shipments = new HashMap<>();
    private long shipmentIdCounter = 1;

    // Create a new shipment
    public Shipment createShipment(Long orderId, String shippingMethod) {
        String trackingNumber = generateTrackingNumber();
        Shipment shipment = new Shipment(
                shipmentIdCounter++,
                orderId,
                trackingNumber,
                shippingMethod,
                "Pending", // Initial status
                LocalDate.now(), // Shipping date is today's date
                null // Delivery date is not set initially
        );
        shipments.put(shipment.getShipmentId(), shipment);
        return shipment;
    }

    // Get shipment details by shipmentId
    public Optional<Shipment> getShipmentDetails(Long shipmentId) {
        return Optional.ofNullable(shipments.get(shipmentId));
    }

    // Get the status of a shipment by shipmentId
    public Optional<String> getShipmentStatus(Long shipmentId) {
        Shipment shipment = shipments.get(shipmentId);
        return shipment != null ? Optional.of(shipment.getStatus()) : Optional.empty();
    }

    // Update shipment status
    public Optional<Shipment> updateShipmentStatus(Long shipmentId, String status) {
        Shipment shipment = shipments.get(shipmentId);
        if (shipment != null) {
            shipment.setStatus(status);
            if (status.equals("Delivered")) {
                shipment.setDeliveryDate(LocalDate.now()); // Set delivery date when status is "Delivered"
            }
            return Optional.of(shipment);
        }
        return Optional.empty();
    }

    // Delete shipment by shipmentId
    public boolean deleteShipment(Long shipmentId) {
        return shipments.remove(shipmentId) != null;
    }

    // Helper method to generate a tracking number (could be more sophisticated)
    private String generateTrackingNumber() {
        return "TRK" + (100000 + (int)(Math.random() * 900000));
    }
}

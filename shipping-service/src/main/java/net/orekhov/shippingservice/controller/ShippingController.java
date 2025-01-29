package net.orekhov.shippingservice.controller;

import net.orekhov.shippingservice.model.Shipment;
import net.orekhov.shippingservice.model.ShippingOrder;
import net.orekhov.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/shipments")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody ShippingOrder shippingOrder) {
        // Создаём новую отправку, используя данные из ShippingOrder
        Shipment createdShipment = shippingService.createShipment(shippingOrder.getOrderId(), shippingOrder.getShippingMethod());
        return new ResponseEntity<>(createdShipment, HttpStatus.CREATED);
    }

    @GetMapping("/{shipmentId}")
    public ResponseEntity<Shipment> getShipmentDetails(@PathVariable Long shipmentId) {
        Optional<Shipment> shipment = shippingService.getShipmentDetails(shipmentId);
        return shipment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/{shipmentId}/status")
    public ResponseEntity<String> getShipmentStatus(@PathVariable Long shipmentId) {
        Optional<String> status = shippingService.getShipmentStatus(shipmentId);
        return status.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment not found"));
    }

    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable Long shipmentId, @RequestParam String status) {
        Optional<Shipment> updatedShipment = shippingService.updateShipmentStatus(shipmentId, status);
        return updatedShipment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long shipmentId) {
        boolean isDeleted = shippingService.deleteShipment(shipmentId);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

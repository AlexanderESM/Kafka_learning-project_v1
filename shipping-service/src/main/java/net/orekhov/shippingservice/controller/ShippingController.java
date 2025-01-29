package net.orekhov.shippingservice.controller;

import net.orekhov.shippingservice.model.Shipment;
import net.orekhov.shippingservice.model.ShippingOrder;
import net.orekhov.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Контроллер для управления отправками в сервисе доставки.
 * Обрабатывает запросы для создания, получения, обновления и удаления отправок.
 */
@RestController
@RequestMapping("/api/shipments")
public class ShippingController {

    private final ShippingService shippingService;

    /**
     * Конструктор контроллера, выполняющий инъекцию зависимости ShippingService.
     *
     * @param shippingService Сервис для работы с отправками.
     */
    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * Создает новую отправку на основе данных заказа.
     *
     * @param shippingOrder Заказ для создания отправки.
     * @return Ответ с созданной отправкой и статусом 201 CREATED.
     */
    @PostMapping("/create")
    public ResponseEntity<Shipment> createShipment(@RequestBody ShippingOrder shippingOrder) {
        // Создаем новую отправку с использованием данных из заказа
        Shipment createdShipment = shippingService.createShipment(shippingOrder.getOrderId(), shippingOrder.getShippingMethod());
        return new ResponseEntity<>(createdShipment, HttpStatus.CREATED);
    }

    /**
     * Получает подробности о конкретной отправке по ее ID.
     *
     * @param shipmentId ID отправки.
     * @return Ответ с деталями отправки или статусом 404 NOT FOUND, если отправка не найдена.
     */
    @GetMapping("/{shipmentId}")
    public ResponseEntity<Shipment> getShipmentDetails(@PathVariable Long shipmentId) {
        Optional<Shipment> shipment = shippingService.getShipmentDetails(shipmentId);
        // Если отправка найдена, возвращаем ее, иначе статус 404
        return shipment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Получает статус отправки по ее ID.
     *
     * @param shipmentId ID отправки.
     * @return Ответ с текущим статусом отправки или статусом 404 NOT FOUND, если отправка не найдена.
     */
    @GetMapping("/{shipmentId}/status")
    public ResponseEntity<String> getShipmentStatus(@PathVariable Long shipmentId) {
        Optional<String> status = shippingService.getShipmentStatus(shipmentId);
        // Если статус отправки найден, возвращаем его, иначе статус 404
        return status.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shipment not found"));
    }

    /**
     * Обновляет статус отправки по ее ID.
     *
     * @param shipmentId ID отправки.
     * @param status Новый статус отправки.
     * @return Ответ с обновленной отправкой или статусом 404 NOT FOUND, если отправка не найдена.
     */
    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable Long shipmentId, @RequestParam String status) {
        Optional<Shipment> updatedShipment = shippingService.updateShipmentStatus(shipmentId, status);
        // Если отправка найдена и статус обновлен, возвращаем ее, иначе статус 404
        return updatedShipment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    /**
     * Удаляет отправку по ее ID.
     *
     * @param shipmentId ID отправки.
     * @return Ответ с статусом 204 NO CONTENT, если удаление успешно, или статусом 404 NOT FOUND, если отправка не найдена.
     */
    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long shipmentId) {
        boolean isDeleted = shippingService.deleteShipment(shipmentId);
        // Если удаление прошло успешно, возвращаем статус 204, иначе статус 404
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

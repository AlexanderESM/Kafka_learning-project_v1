package net.orekhov.paymentservice.controller;

import net.orekhov.paymentservice.model.Payment;
import net.orekhov.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов, связанных с платежами.
 * Этот класс предоставляет эндпоинты для создания, получения, обновления и удаления платежей.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class); // Логгер для контроллера
    private final PaymentService paymentService;

    /**
     * Конструктор для внедрения зависимости PaymentService.
     *
     * @param paymentService Сервис для обработки операций с платежами.
     */
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Эндпоинт для создания нового платежа.
     * Принимает данные платежа в теле запроса и возвращает созданный платеж.
     *
     * @param payment Платеж для создания.
     * @return Ответ с созданным платежом и HTTP статусом CREATED (201).
     */
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        logger.info("Creating new payment: {}", payment); // Логирование создания платежа
        Payment createdPayment = paymentService.createPayment(payment);
        logger.info("Payment created successfully: {}", createdPayment); // Логирование успешного создания
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    /**
     * Эндпоинт для получения информации о платеже по его ID.
     * Если платеж с данным ID найден, возвращает информацию о нем.
     * Если платеж не найден, возвращает статус NOT_FOUND (404).
     *
     * @param paymentId ID платежа.
     * @return Ответ с данными платежа и HTTP статусом OK (200), если найден.
     *         Если платеж не найден, возвращается статус NOT_FOUND (404).
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long paymentId) {
        logger.info("Fetching payment with ID: {}", paymentId); // Логирование запроса на получение платежа
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment == null) {
            logger.warn("Payment not found for ID: {}", paymentId); // Логирование, если платеж не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Payment found: {}", payment); // Логирование найденного платежа
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    /**
     * Эндпоинт для обновления статуса платежа.
     * Пример: можно обновить статус на "completed" (завершен).
     * Если платеж с данным ID не найден, возвращает статус NOT_FOUND (404).
     *
     * @param paymentId ID платежа.
     * @param status Новый статус для обновления.
     * @return Ответ с обновленными данными платежа и HTTP статусом OK (200), если обновление успешно.
     *         Если платеж не найден, возвращается статус NOT_FOUND (404).
     */
    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) {
        logger.info("Updating status of payment with ID: {} to {}", paymentId, status); // Логирование запроса на обновление
        Payment updatedPayment = paymentService.updatePaymentStatus(paymentId, status);
        if (updatedPayment == null) {
            logger.warn("Payment not found for ID: {}", paymentId); // Логирование, если платеж не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Payment status updated successfully: {}", updatedPayment); // Логирование успешного обновления статуса
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    /**
     * Эндпоинт для удаления платежа по его ID.
     * Если платеж с данным ID не найден, возвращает статус NOT_FOUND (404).
     * Если удаление прошло успешно, возвращает статус NO_CONTENT (204).
     *
     * @param paymentId ID платежа для удаления.
     * @return Ответ с HTTP статусом NO_CONTENT (204), если платеж удален.
     *         Если платеж не найден, возвращается статус NOT_FOUND (404).
     */
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        logger.info("Deleting payment with ID: {}", paymentId); // Логирование запроса на удаление
        boolean isDeleted = paymentService.deletePayment(paymentId);
        if (!isDeleted) {
            logger.warn("Payment not found for ID: {}", paymentId); // Логирование, если платеж не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Payment deleted successfully with ID: {}", paymentId); // Логирование успешного удаления
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

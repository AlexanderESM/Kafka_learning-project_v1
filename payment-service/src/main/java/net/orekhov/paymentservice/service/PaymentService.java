package net.orekhov.paymentservice.service;

import net.orekhov.paymentservice.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для работы с платежами.
 * Обеспечивает создание, получение, обновление статуса и удаление платежей.
 */
@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class); // Логгер для PaymentService

    // Хранилище для платежей (используется для имитации базы данных)
    private final Map<Long, Payment> paymentStore = new HashMap<>();

    // Счётчик для генерации уникальных идентификаторов платежей
    private long paymentIdCounter = 1;

    /**
     * Создает новый платеж и сохраняет его в хранилище.
     * Устанавливает уникальный идентификатор для нового платежа.
     *
     * @param payment Платеж, который необходимо создать.
     * @return Созданный платеж с установленным уникальным идентификатором.
     */
    public Payment createPayment(Payment payment) {
        logger.debug("Creating payment: {}", payment); // Логируем создание платежа

        // Устанавливаем уникальный идентификатор платежа
        payment.setId(paymentIdCounter++);

        // Сохраняем платеж в хранилище
        paymentStore.put(payment.getId(), payment);

        logger.info("Payment created with ID: {}", payment.getId()); // Логируем успешное создание платежа
        return payment;
    }

    /**
     * Получает платеж по его идентификатору.
     *
     * @param paymentId Идентификатор платежа, который нужно найти.
     * @return Платеж с указанным идентификатором или null, если такой платеж не найден.
     */
    public Payment getPaymentById(Long paymentId) {
        logger.debug("Fetching payment with ID: {}", paymentId); // Логируем попытку получить платеж

        Payment payment = paymentStore.get(paymentId);

        if (payment == null) {
            logger.warn("Payment with ID: {} not found", paymentId); // Логируем предупреждение, если платеж не найден
        } else {
            logger.info("Payment found: {}", payment); // Логируем успешный поиск платежа
        }

        return payment;
    }

    /**
     * Обновляет статус существующего платежа.
     *
     * @param paymentId Идентификатор платежа, статус которого необходимо обновить.
     * @param status Новый статус платежа.
     * @return Обновленный платеж, если он был найден, или null, если платеж с таким идентификатором не существует.
     */
    public Payment updatePaymentStatus(Long paymentId, String status) {
        logger.debug("Updating status of payment with ID: {} to status: {}", paymentId, status); // Логируем обновление статуса

        Payment payment = paymentStore.get(paymentId);

        if (payment != null) {
            payment.setStatus(status);
            logger.info("Payment status updated: {}", payment); // Логируем успешное обновление
            return payment;
        } else {
            logger.warn("Payment with ID: {} not found for status update", paymentId); // Логируем, если платеж не найден
            return null;
        }
    }

    /**
     * Удаляет платеж по его идентификатору.
     *
     * @param paymentId Идентификатор платежа, который нужно удалить.
     * @return true, если платеж был успешно удален, или false, если такого платежа не было.
     */
    public boolean deletePayment(Long paymentId) {
        logger.debug("Deleting payment with ID: {}", paymentId); // Логируем попытку удаления

        boolean isDeleted = paymentStore.remove(paymentId) != null;

        if (isDeleted) {
            logger.info("Payment with ID: {} successfully deleted", paymentId); // Логируем успешное удаление
        } else {
            logger.warn("Payment with ID: {} not found for deletion", paymentId); // Логируем, если платеж не найден
        }

        return isDeleted;
    }
}

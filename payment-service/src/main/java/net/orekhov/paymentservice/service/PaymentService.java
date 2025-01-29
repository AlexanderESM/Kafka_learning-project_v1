package net.orekhov.paymentservice.service;

import net.orekhov.paymentservice.model.Payment;
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
        // Устанавливаем уникальный идентификатор платежа
        payment.setId(paymentIdCounter++);

        // Сохраняем платеж в хранилище
        paymentStore.put(payment.getId(), payment);

        return payment;
    }

    /**
     * Получает платеж по его идентификатору.
     *
     * @param paymentId Идентификатор платежа, который нужно найти.
     * @return Платеж с указанным идентификатором или null, если такой платеж не найден.
     */
    public Payment getPaymentById(Long paymentId) {
        // Возвращаем платеж из хранилища по его идентификатору
        return paymentStore.get(paymentId);
    }

    /**
     * Обновляет статус существующего платежа.
     *
     * @param paymentId Идентификатор платежа, статус которого необходимо обновить.
     * @param status Новый статус платежа.
     * @return Обновленный платеж, если он был найден, или null, если платеж с таким идентификатором не существует.
     */
    public Payment updatePaymentStatus(Long paymentId, String status) {
        // Находим платеж по идентификатору
        Payment payment = paymentStore.get(paymentId);

        // Если платеж найден, обновляем его статус
        if (payment != null) {
            payment.setStatus(status);
            return payment;
        }

        // Если платеж не найден, возвращаем null
        return null;
    }

    /**
     * Удаляет платеж по его идентификатору.
     *
     * @param paymentId Идентификатор платежа, который нужно удалить.
     * @return true, если платеж был успешно удален, или false, если такого платежа не было.
     */
    public boolean deletePayment(Long paymentId) {
        // Удаляем платеж из хранилища и возвращаем результат
        return paymentStore.remove(paymentId) != null;
    }

    // Дополнительные методы могут быть добавлены для расширения бизнес-логики
    // Например, можно добавить проверку на валидность платежа или логику обработки платежа.
}

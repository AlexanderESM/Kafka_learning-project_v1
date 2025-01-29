package net.orekhov.paymentservice.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Модель платежа, представляющая информацию о платеже.
 * Содержит идентификатор платежа, статус и сумму.
 */
public class Payment {

    private static final Logger logger = LoggerFactory.getLogger(Payment.class); // Логгер для модели Payment

    // Идентификатор платежа
    private Long id;

    // Статус платежа (например, "pending", "completed", "failed")
    private String status;

    // Сумма платежа
    private Double amount;

    /**
     * Возвращает идентификатор платежа.
     *
     * @return Идентификатор платежа.
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор платежа.
     *
     * @param id Идентификатор платежа.
     */
    public void setId(Long id) {
        logger.debug("Setting payment ID: {}", id); // Логируем установку ID
        this.id = id;
    }

    /**
     * Возвращает статус платежа.
     *
     * @return Статус платежа.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Устанавливает статус платежа.
     *
     * @param status Статус платежа (например, "pending", "completed").
     */
    public void setStatus(String status) {
        logger.debug("Setting payment status: {}", status); // Логируем установку статуса
        this.status = status;
    }

    /**
     * Возвращает сумму платежа.
     *
     * @return Сумма платежа.
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Устанавливает сумму платежа.
     *
     * @param amount Сумма платежа.
     */
    public void setAmount(Double amount) {
        logger.debug("Setting payment amount: {}", amount); // Логируем установку суммы
        this.amount = amount;
    }

    /**
     * Переопределенный метод toString для представления объекта Payment в строковом формате.
     * Это поможет в отладке и выводе данных объекта.
     *
     * @return Строковое представление платежа.
     */
    @Override
    public String toString() {
        String result = "Payment{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
        logger.debug("Payment object toString: {}", result); // Логируем вызов toString
        return result;
    }
}

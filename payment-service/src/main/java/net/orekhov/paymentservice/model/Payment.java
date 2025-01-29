package net.orekhov.paymentservice.model;

/**
 * Модель платежа, представляющая информацию о платеже.
 * Содержит идентификатор платежа, статус и сумму.
 */
public class Payment {

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
        return "Payment{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}

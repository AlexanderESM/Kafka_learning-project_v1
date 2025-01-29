package net.orekhov.notificationsservice.model;

/**
 * Модель уведомления, которая содержит информацию о сообщении, получателе и идентификаторе.
 * Этот класс используется для представления уведомлений в сервисе уведомлений.
 */
public class Notification {

    private String id;         // Идентификатор уведомления
    private String message;    // Текст сообщения уведомления
    private String recipient;  // Получатель уведомления

    /**
     * Конструктор без параметров.
     * Используется для создания пустого объекта уведомления.
     */
    public Notification() {
    }

    /**
     * Конструктор с параметрами для инициализации всех полей уведомления.
     *
     * @param id Идентификатор уведомления.
     * @param message Сообщение уведомления.
     * @param recipient Получатель уведомления.
     */
    public Notification(String id, String message, String recipient) {
        this.id = id;
        this.message = message;
        this.recipient = recipient;
    }

    /**
     * Получение идентификатора уведомления.
     *
     * @return Идентификатор уведомления.
     */
    public String getId() {
        return id;
    }

    /**
     * Установка идентификатора уведомления.
     *
     * @param id Идентификатор уведомления.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Получение сообщения уведомления.
     *
     * @return Текст сообщения уведомления.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Установка сообщения уведомления.
     *
     * @param message Сообщение уведомления.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Получение получателя уведомления.
     *
     * @return Получатель уведомления.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Установка получателя уведомления.
     *
     * @param recipient Получатель уведомления.
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Переопределение метода toString для представления объекта уведомления в виде строки.
     *
     * @return Строковое представление объекта уведомления.
     */
    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}

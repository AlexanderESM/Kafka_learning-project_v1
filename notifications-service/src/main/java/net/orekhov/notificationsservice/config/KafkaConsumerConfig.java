package net.orekhov.notificationsservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka Consumer для прослушивания сообщений в сервисе уведомлений.
 * Настроены фабрики для потребителей и контейнеров, а также метод для прослушивания сообщений.
 */
@Configuration
public class KafkaConsumerConfig {

    // Логгер для логирования событий в этом классе
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    /**
     * Создаёт фабрику потребителей для работы с Kafka.
     * Настроены параметры для подключения к Kafka, включая сереализаторы для ключей и значений.
     *
     * @return ConsumerFactory, настроенный для обработки строковых данных.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        // Создаём карту с конфигурацией для потребителя
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Адрес Kafka брокера
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notifications-group");  // Уникальная группа для обработки сообщений
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Десериализация ключей сообщений
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Десериализация значений сообщений

        // Логируем конфигурацию потребителя
        logger.info("Configuring Kafka consumer with bootstrap servers: localhost:9092 and group ID: notifications-group");

        // Возвращаем фабрику для создания потребителей с данной конфигурацией
        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Конфигурирует фабрику для контейнера Kafka Listener.
     * Использует переданную фабрику потребителей для создания контейнера, который будет прослушивать сообщения.
     *
     * @param consumerFactory Фабрика потребителей, которая создаёт потребителей с нужной конфигурацией.
     * @return KafkaListenerContainerFactory, конфигурируемый для прослушивания сообщений.
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(
            ConsumerFactory<String, String> consumerFactory) {
        // Создаём фабрику контейнера для многопоточного прослушивания
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);  // Устанавливаем фабрику потребителей

        // Логируем создание фабрики контейнера
        logger.info("Configuring KafkaListenerContainerFactory with consumer factory: {}", consumerFactory.getClass().getName());

        return factory;
    }

    /**
     * Метод прослушивания сообщений из Kafka.
     * Когда новое сообщение появляется в теме "notifications", этот метод будет вызван.
     *
     * @param record Запись сообщения, содержащее ключ и значение.
     */
    @KafkaListener(topics = "notifications", groupId = "notifications-group")
    public void listen(ConsumerRecord<String, String> record) {
        // Логируем полученное сообщение для отладки
        logger.info("Received message: {}", record.value());

        // Здесь можно добавить логику обработки полученного сообщения (например, уведомление пользователя)
        try {
            // Пример обработки сообщения
            logger.debug("Processing message with key: {} and value: {}", record.key(), record.value());
            // Ваш код обработки...
        } catch (Exception e) {
            // Логируем ошибку, если обработка сообщения не удалась
            logger.error("Error while processing message with key: {}", record.key(), e);
        }
    }
}

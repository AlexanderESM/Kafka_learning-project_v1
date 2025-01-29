package net.orekhov.shippingservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для Kafka Consumer в сервисе доставки.
 * Настраивает потребителей Kafka для обработки сообщений с темой доставки.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class); // Логгер для KafkaConsumerConfig

    // Адрес Kafka брокера (замените на реальный адрес вашего Kafka сервера)
    private final String bootstrapServers = "localhost:9092";

    /**
     * Конфигурации для Kafka Consumer.
     * Настраиваются параметры подключения, десериализаторы и политики обработки оффсетов.
     *
     * @return Map с настройками для Kafka Consumer.
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        logger.debug("Configuring Kafka Consumer with bootstrap servers: {}", bootstrapServers); // Логируем конфигурацию

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Адрес Kafka сервера
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "shipping-service-group"); // Идентификатор группы потребителей
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Десериализатор ключа
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Десериализатор значения
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Обработка оффсетов ("earliest" для всех сообщений)

        logger.info("Kafka Consumer configs successfully created."); // Логируем успешную настройку конфигурации
        return props;
    }

    /**
     * Фабрика потребителей Kafka.
     * Создает потребителя с использованием указанных конфигураций.
     *
     * @return ConsumerFactory для создания экземпляров Kafka Consumer.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        logger.debug("Creating Kafka ConsumerFactory."); // Логируем создание фабрики потребителей
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * Создает контейнер для прослушивания сообщений из Kafka.
     * Контейнер обрабатывает сообщения с определенной темы и назначает слушателя.
     *
     * @return MessageListenerContainer для обработки сообщений.
     */
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        logger.debug("Creating MessageListenerContainer for shipping-topic."); // Логируем создание контейнера

        // Настройка параметров контейнера (например, указание темы)
        ContainerProperties containerProps = new ContainerProperties("shipping-topic"); // Укажите тему Kafka

        // Установка слушателя сообщений
        containerProps.setMessageListener(new ShippingMessageListener());

        // Создание контейнера с фабрикой потребителей и настройками контейнера
        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);

        // Установка уровня параллелизма (количество потоков для обработки сообщений)
        container.setConcurrency(3);

        logger.info("MessageListenerContainer created and concurrency set to 3."); // Логируем успешное создание контейнера
        return container;
    }

    /**
     * Реализация слушателя сообщений для обработки сообщений о доставке.
     */
    private static class ShippingMessageListener implements MessageListener<String, String> {

        private static final Logger logger = LoggerFactory.getLogger(ShippingMessageListener.class); // Логгер для ShippingMessageListener

        /**
         * Метод, который вызывается при получении сообщения.
         * Обрабатывает сообщение и вызывает логику обработки доставки.
         *
         * @param record Сообщение, полученное из Kafka.
         */
        @Override
        public void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record) {
            // Извлекаем сообщение из записи
            String message = record.value();

            logger.debug("Received shipping message: {}", message); // Логируем получение сообщения

            // Логика обработки сообщения о доставке
            processShippingMessage(message);
        }

        /**
         * Пример метода для обработки сообщения о доставке.
         * В этом методе можно распарсить сообщение и инициировать процесс доставки.
         *
         * @param message Сообщение о доставке для обработки.
         */
        private void processShippingMessage(String message) {
            logger.info("Processing shipping for message: {}", message); // Логируем начало обработки сообщения

            // Логика для обработки сообщения (например, создание заказа на доставку)
            // Можно добавить дополнительные шаги логирования здесь для отслеживания ошибок и прогресса
        }
    }
}

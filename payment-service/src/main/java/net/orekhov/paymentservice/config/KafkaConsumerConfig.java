package net.orekhov.paymentservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
 * Конфигурация потребителя Kafka для службы платежей.
 * Этот класс конфигурирует потребителя, который получает сообщения из Kafka topic
 * и обрабатывает их в соответствии с бизнес-логикой.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class); // Логгер для конфигурации
    private final String bootstrapServers = "localhost:9093"; // Адрес Kafka сервера, замените на ваш

    /**
     * Конфигурация для Kafka Consumer.
     * Этот метод задает настройки для подключения к Kafka.
     *
     * @return Карта с настройками для Consumer.
     */
    @Bean
    public Map<String, Object> consumerConfigs() {
        logger.info("Configuring Kafka Consumer with bootstrap servers: {}", bootstrapServers); // Логирование конфигурации
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // Адрес Kafka сервера
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-service-group"); // ID группы потребителей
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Сериализатор ключей
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Сериализатор значений
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // "latest" для получения новых сообщений, "earliest" для всех сообщений
        return props;
    }

    /**
     * Создает ConsumerFactory для Kafka Consumer.
     * Используется для создания экземпляров потребителей, которые будут обрабатывать сообщения.
     *
     * @return ConsumerFactory для создания потребителей Kafka
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        logger.info("Creating Kafka ConsumerFactory with the provided configurations"); // Логирование создания фабрики
        return new DefaultKafkaConsumerFactory<>(consumerConfigs()); // Создает новый Consumer с заданными конфигурациями
    }

    /**
     * Создает контейнер для слушателя сообщений Kafka.
     * Этот метод настраивает контейнер, который будет прослушивать сообщения из заданного Kafka topic.
     *
     * @return MessageListenerContainer, который будет слушать сообщения из Kafka
     */
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        logger.info("Creating Kafka message listener container for topic: payment-topic"); // Логирование создания контейнера
        // Настройка параметров контейнера для слушателя
        ContainerProperties containerProps = new ContainerProperties("payment-topic"); // Замените на ваш Kafka topic

        // Устанавливаем слушателя сообщений
        containerProps.setMessageListener(new MyMessageListener());

        // Создаем контейнер для слушателя с использованием ConsumerFactory и настроек контейнера
        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);

        // Устанавливаем уровень параллелизма (количество потоков для обработки сообщений)
        container.setConcurrency(3);

        return container;
    }

    /**
     * Реализация слушателя сообщений Kafka.
     * Этот класс будет обрабатывать полученные сообщения.
     */
    private static class MyMessageListener implements MessageListener<String, String> {

        private static final Logger logger = LoggerFactory.getLogger(MyMessageListener.class); // Логгер для слушателя

        /**
         * Метод обработки сообщений.
         * Здесь можно реализовать бизнес-логику для обработки полученных сообщений.
         *
         * @param record Сообщение, полученное из Kafka topic.
         */
        @Override
        public void onMessage(ConsumerRecord<String, String> record) {
            // Получаем сообщение из Kafka
            String message = record.value();

            // Логируем полученное сообщение
            logger.info("Received message from Kafka topic 'payment-topic': {}", message);

            // Здесь должна быть логика обработки сообщения, например, обработка платежей
            try {
                // Пример обработки
                // processPayment(message);
                logger.info("Processed payment message successfully: {}", message);
            } catch (Exception e) {
                logger.error("Error processing payment message: {}", message, e);
            }
        }
    }
}

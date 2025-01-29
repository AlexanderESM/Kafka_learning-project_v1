package net.orekhov.ordersservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация Kafka продюсера.
 * Этот класс настраивает KafkaTemplate, который используется для отправки сообщений в Kafka.
 */
@Configuration
public class KafkaProducerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    /**
     * Создание и настройка KafkaTemplate, который используется для отправки сообщений в Kafka.
     * KafkaTemplate использует ProducerFactory для создания продюсера.
     *
     * @return Конфигурированный экземпляр KafkaTemplate.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        logger.info("Creating KafkaTemplate...");
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        logger.info("KafkaTemplate created successfully.");
        return kafkaTemplate;
    }

    /**
     * Создание ProducerFactory, который используется для создания Kafka продюсера.
     * ProducerFactory настраивается с помощью параметров конфигурации.
     *
     * @return Конфигурированный экземпляр ProducerFactory.
     */
    private ProducerFactory<String, String> producerFactory() {
        logger.info("Creating ProducerFactory...");
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        logger.info("ProducerFactory created successfully.");
        return producerFactory;
    }

    /**
     * Конфигурация параметров продюсера Kafka.
     * Настройка для подключения к Kafka брокеру, сериализации сообщений, а также параметров подтверждения.
     *
     * @return Карта конфигураций для продюсера.
     */
    private Map<String, Object> producerConfigs() {
        logger.info("Setting producer configurations...");
        Map<String, Object> props = new HashMap<>();

        // Адрес Kafka брокера. Убедитесь, что указанный адрес соответствует вашему Kafka серверу.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "INSIDE-KAFKA:9092"); // Укажите адрес вашего Kafka сервера
        logger.debug("Bootstrap server: INSIDE-KAFKA:9092");

        // Сериализаторы для ключей и значений сообщений. В данном случае используем StringSerializer.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Сериализатор для ключей
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Сериализатор для значений
        logger.debug("Using StringSerializer for both key and value.");

        // Параметры подтверждения. "all" гарантирует, что сообщение будет подтверждено всеми брокерами.
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // Подтверждение от всех брокеров
        logger.debug("Acks set to 'all'.");

        // Количество попыток отправки сообщения в случае ошибки.
        props.put(ProducerConfig.RETRIES_CONFIG, 3); // Количество попыток при ошибке
        logger.debug("Retries set to 3.");

        logger.info("Producer configurations set successfully.");
        return props;
    }
}

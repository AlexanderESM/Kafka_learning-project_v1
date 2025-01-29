package net.orekhov.ordersservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
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

    /**
     * Создание и настройка KafkaTemplate, который используется для отправки сообщений в Kafka.
     * KafkaTemplate использует ProducerFactory для создания продюсера.
     *
     * @return Конфигурированный экземпляр KafkaTemplate.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Создание ProducerFactory, который используется для создания Kafka продюсера.
     * ProducerFactory настраивается с помощью параметров конфигурации.
     *
     * @return Конфигурированный экземпляр ProducerFactory.
     */
    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Конфигурация параметров продюсера Kafka.
     * Настройка для подключения к Kafka брокеру, сериализации сообщений, а также параметров подтверждения.
     *
     * @return Карта конфигураций для продюсера.
     */
    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        // Адрес Kafka брокера. Убедитесь, что указанный адрес соответствует вашему Kafka серверу.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "INSIDE-KAFKA:9093"); // Укажите адрес вашего Kafka сервера

        // Сериализаторы для ключей и значений сообщений. В данном случае используем StringSerializer.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Сериализатор для ключей
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Сериализатор для значений

        // Параметры подтверждения. "all" гарантирует, что сообщение будет подтверждено всеми брокерами.
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // Подтверждение от всех брокеров

        // Количество попыток отправки сообщения в случае ошибки.
        props.put(ProducerConfig.RETRIES_CONFIG, 3); // Количество попыток при ошибке

        return props;
    }
}

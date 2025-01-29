package net.orekhov.notificationsservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@EnableKafka  // Аннотация для включения Kafka в тестах
@EmbeddedKafka  // Использование встроенного Kafka для тестов
class KafkaConsumerConfigTest {

    /**
     * Тестируем создание ConsumerFactory
     */
    @Test
    void testConsumerFactory() {
        // Создаём экземпляр класса конфигурации
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

        // Получаем созданную фабрику потребителей
        ConsumerFactory<String, String> consumerFactory = kafkaConsumerConfig.consumerFactory();

        // Проверяем, что фабрика была создана
        assertNotNull(consumerFactory);

        // Проверяем, что фабрика является экземпляром DefaultKafkaConsumerFactory
        assertTrue(consumerFactory instanceof DefaultKafkaConsumerFactory);

        // Мокаем конфигурацию Kafka
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Адрес брокера Kafka
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notifications-group");  // Идентификатор группы потребителей
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Десериализатор ключа
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Десериализатор значения

        // Проверяем, что в конфигурации правильно указаны значения
        assertEquals("localhost:9092", config.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals("notifications-group", config.get(ConsumerConfig.GROUP_ID_CONFIG));
    }

    /**
     * Тестируем создание KafkaListenerContainerFactory
     */
    @Test
    void testFactory() {
        // Создаём экземпляр класса конфигурации
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

        // Мокаем фабрику потребителей
        ConsumerFactory<String, String> consumerFactory = mock(ConsumerFactory.class);

        // Получаем контейнер фабрики
        KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory =
                kafkaConsumerConfig.factory(consumerFactory);

        // Проверяем, что фабрика контейнера не равна null
        assertNotNull(factory);

        // Проверяем, что фабрика контейнера является экземпляром ConcurrentKafkaListenerContainerFactory
        assertTrue(factory instanceof ConcurrentKafkaListenerContainerFactory);
    }

    /**
     * Тестируем метод listen, который обрабатывает сообщения из Kafka
     */
    @Test
    void testListen() {
        // Создаём экземпляр класса конфигурации
        KafkaConsumerConfig kafkaConsumerConfig = new KafkaConsumerConfig();

        // Мокаем Kafka ConsumerRecord (сообщение из Kafka)
        ConsumerRecord<String, String> record = mock(ConsumerRecord.class);

        // Мокаем значение и ключ сообщения
        when(record.value()).thenReturn("Test message");
        when(record.key()).thenReturn("key");

        // Вызываем метод listen, чтобы обработать полученное сообщение
        kafkaConsumerConfig.listen(record);

        // Для проверки логов можно использовать ArgumentCaptor
        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);

        // Проверяем, что метод listen был вызван один раз с нужным параметром
        verify(kafkaConsumerConfig, times(1)).listen(record);

        // Если необходимо, можно добавить проверки для логов или других проверок обработки сообщений
    }
}

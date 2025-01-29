package net.orekhov.shippingservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private final String bootstrapServers = "localhost:9093"; // Replace with your Kafka server address

    // Kafka Consumer Configurations
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "shipping-service-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // "latest" for new messages, "earliest" for all
        return props;
    }

    // Consumer Factory
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    // Create Kafka listener for messages
    @Bean
    public MessageListenerContainer messageListenerContainer() {
        // Configure the ContainerProperties
        ContainerProperties containerProps = new ContainerProperties("shipping-topic"); // Replace with your topic name

        // Set the message listener
        containerProps.setMessageListener(new ShippingMessageListener());

        // Create the listener container with the consumer factory and the container properties
        ConcurrentMessageListenerContainer<String, String> container =
                new ConcurrentMessageListenerContainer<>(consumerFactory(), containerProps);

        // Set concurrency level (number of threads to handle messages)
        container.setConcurrency(3);

        return container;
    }

    // Message Listener implementation
    private static class ShippingMessageListener implements MessageListener<String, String> {
        @Override
        public void onMessage(org.apache.kafka.clients.consumer.ConsumerRecord<String, String> record) {
            // Process the message here
            String message = record.value();
            System.out.println("Received shipping message: " + message);
            // Implement the shipping processing logic here
            processShippingMessage(message);
        }

        // Example method to handle the shipping message
        private void processShippingMessage(String message) {
            // Implement logic to process the shipping message (e.g., create shipping order)
            System.out.println("Processing shipping for message: " + message);
            // For instance, parse the message and process the shipping order based on it
        }
    }
}

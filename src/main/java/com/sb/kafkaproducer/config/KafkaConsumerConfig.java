package com.sb.kafkaproducer.config;

import com.sb.kafkaproducer.entity.Bid;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    private ConsumerFactory<String, Bid> bidConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "bid-all");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Bid.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Bid> bidKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Bid> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bidConsumerFactory());
        return factory;
    }

}

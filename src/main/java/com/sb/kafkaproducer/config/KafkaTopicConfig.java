package com.sb.kafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.bid-topic-zu}")
    private String topicZu;

    @Value("${kafka.bid-topic-aq}")
    private String topicAq;

    @Value("${kafka.bid-topic-all}")
    private String topicAll;

    @Bean
    NewTopic topic1() {
        return TopicBuilder.name(topicZu).build();
    }

    @Bean
    NewTopic topic2() {
        return TopicBuilder.name(topicAq).build();
    }

    @Bean
    NewTopic topic3() {
        return TopicBuilder.name(topicAll).build();
    }
}

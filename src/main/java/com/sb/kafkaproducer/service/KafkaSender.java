package com.sb.kafkaproducer.service;

import com.sb.kafkaproducer.entity.Bid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class KafkaSender {

    private final Logger LOG = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, Bid> bidKafkaTemplate;

    void sendCustomMessage(Bid bid, String topicName) {
        LOG.info("Sending Json Serializer : {}", bid);
        LOG.info("--------------------------------");

        bidKafkaTemplate.send(topicName, bid);
    }

}
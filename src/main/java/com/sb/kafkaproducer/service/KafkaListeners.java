package com.sb.kafkaproducer.service;

import com.sb.kafkaproducer.entity.Bid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Slf4j
class KafkaListeners {

    @KafkaListener(topics = "bid-zu", groupId = "bid-all", containerFactory = "bidKafkaListenerContainerFactory")
    void listenerForBidZu(Bid bid) {
        logBid(bid);
    }

    @KafkaListener(topics = "bid-aq", groupId = "bid-all", containerFactory = "bidKafkaListenerContainerFactory")
    void listenerForBidAq(Bid bid) {
        logBid(bid);
    }

    @KafkaListener(topics = "bid-all", groupId = "bid-all", containerFactory = "bidKafkaListenerContainerFactory")
    void listenerForOtherBidTypes(Bid bid) {
        logBid(bid);
    }

    private void logBid(final Bid bid) {
        log.info("Bid with id {}, timestamp {}, queue \"name\" {}, and payload \"{}\" was successfully received and processed",
                bid.getId(), bid.getTimestamp(), bid.getType(), new String(Base64.getDecoder().decode(bid.getPayload())));
    }
}

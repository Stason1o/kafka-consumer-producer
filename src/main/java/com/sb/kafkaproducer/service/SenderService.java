package com.sb.kafkaproducer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.kafkaproducer.entity.Bid;
import com.sb.kafkaproducer.entity.BidWrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import static com.sb.kafkaproducer.entity.BidType.AQ;
import static com.sb.kafkaproducer.entity.BidType.ZU;

@Slf4j
@Component
@RequiredArgsConstructor
public class SenderService {

    private final KafkaSender kafkaSender;

    private final ObjectMapper objectMapper;

    @Value("${kafka.bid-topic-zu}")
    private String topicZu;

    @Value("${kafka.bid-topic-aq}")
    private String topicAq;

    @Value("${kafka.bid-topic-all}")
    private String topicOther;

    @Scheduled(fixedDelay = 60000, initialDelay = 5000)
    @SneakyThrows
    void initiateSendingMessage() {

        File resource = new ClassPathResource("bids.json").getFile();

        List<BidWrapper> bids = objectMapper.readValue(resource, new TypeReference<List<BidWrapper>>() {
            @Override
            public Type getType() {
                return super.getType();
            }

            @Override
            public int compareTo(TypeReference<List<BidWrapper>> o) {
                return super.compareTo(o);
            }
        });

        log.info("Bids received from file {}", bids);

        bids.stream()
                .map(BidWrapper::getBid)
                .forEach(bid -> {
                    if (AQ.name().equals(bid.getType())) {
                        kafkaSender.sendCustomMessage(bid, topicAq);
                    } else if (ZU.name().equals(bid.getType())) {
                        kafkaSender.sendCustomMessage(bid, topicZu);
                    } else {
                        kafkaSender.sendCustomMessage(bid, topicOther);
                    }
                });
    }
}

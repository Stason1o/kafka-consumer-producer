package com.sb.kafkaproducer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Base64;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Bid {

    private String id;

    @JsonProperty("ts")
    private String timestamp;

    @JsonProperty("ty") // queue name
    private String type;

    @JsonProperty("pl") // base64 encoded
    private String payload;

    public static void main(String[] args) {
        System.out.println(new String(Base64.getDecoder().decode("Q29uZmlybWVkOiAzOTI")));
    }
}

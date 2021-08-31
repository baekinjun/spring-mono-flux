package com.injun.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    //repository  template 버전 !
    private final KafkaTemplate<String, String> kafkaTemplate;
    String kafkaTopic = "kafka-topic";

    public void send(String data) {
        kafkaTemplate.send(kafkaTopic, data);
    }
}

package com.injun.quiz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    @KafkaListener(topics = "kafka-test", groupId = "MyGroup")
    public void listenGroup(String message) {
        System.out.println("Consumer Message Test: " + message);
    }
}

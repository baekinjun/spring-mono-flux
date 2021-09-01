package com.injun.quiz.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class Consumers {
    @KafkaListener(topics = "kafka-test")
    public void listenGroup(String message) {
        System.out.println("Received Message Test: " + message);
    }
}

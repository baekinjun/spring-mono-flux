package com.injun.quiz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaProducer producers;


    @GetMapping
    public String hello() {
        return "hello kafka";
    }

    @PostMapping
    public String sendMessage(@RequestParam("message") String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Producer 진입");
        this.producers.sendMessage(message);
        return "Message send to Kafka Successfully";

    }


}

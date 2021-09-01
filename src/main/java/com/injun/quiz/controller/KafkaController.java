package com.injun.quiz.controller;
/*
import com.injun.quiz.domain.Producers;
import com.injun.quiz.service.KafkaSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final KafkaSender kafkaSender;
    private final Producers producers;


    @GetMapping
    public String hello() {
        return "hello kafka";
    }

    @GetMapping("/producer")
    public String producer(@RequestParam("message") String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Producer 진입");
        kafkaSender.send(message);
        return "Message send to Kafka Successfully";

    }

    @GetMapping("/receiver")
    public void receiver() {
        producers.sendMessage("kafka-test", "GoodLuck!!");
    }

}
*/
package com.injun.quiz.controller;

import com.injun.quiz.domain.User;
import com.injun.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    //flux 떄문에 리액티브 몽고 디비를 사용한다.
    @GetMapping
    public Flux<User> getAllPerson() {
        return userRepository.findAll();
    }

    // 스트림으로 요청할경우 이렇게 보내야 한다.!
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamAllPerson() {
        return userRepository.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<User>> createPerson(@RequestBody User user) {
        //있을떄 세이브후  mono로 넘기기
        return userRepository.save(user).map(p -> {
            return ResponseEntity.ok(user);
            //없을경우 디폴트 값 주기 404
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updatePerson(@PathVariable(value = "id") String id, @RequestBody User user) {
        //flatmap 은 일대다 대응 매핑된 데이터를 여러개의 일로 사용!
        return userRepository.findById(id).flatMap(i -> {
            i.setName(user.getName());
            i.setEmail(user.getEmail());
            return userRepository.save(i);
        }).map(j -> {
            return ResponseEntity.ok(j);
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable(value = "id") String id) {
        return userRepository.findById(id)
                .flatMap(i -> userRepository.delete(i).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

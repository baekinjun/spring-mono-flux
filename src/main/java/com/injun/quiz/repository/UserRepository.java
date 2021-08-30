package com.injun.quiz.repository;

import com.injun.quiz.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
//반응형인 리액티브
//flux 복수 mono 단수
public interface UserRepository extends ReactiveMongoRepository<User, Long> {
    Optional<User> findByAlias(String alias);
    Flux<User> findAll();
    Mono<User> findByUserid();
}

package com.injun.quiz.repository;

import com.injun.quiz.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Optional;

@Repository
//반응형인 리액티브
//flux 복수 mono 단수
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Optional<User> findByAlias(String alias);

    Flux<User> findAll();

    Mono<User> findByid();

    //rdbms 쿼리랑 모양이 다르다.
    @Query("{'alias' : {$regex: ?0}}")
    Flux<User> findRegexByAlias(String alias); // select * from users where alias like "%alias%"

    //mongo db 는 라우터 쿼리가 있다.!
    @Query("{'alias' : {$regex: ?0}}")
    Flux<User> findByAliasWithPage(String alias, Pageable page); // pagination
}

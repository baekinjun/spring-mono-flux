package com.injun.quiz.repository;

import com.injun.quiz.domain.Attempt;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AttemptRepository extends ReactiveMongoRepository<Attempt, Long> {
    Flux<Attempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}

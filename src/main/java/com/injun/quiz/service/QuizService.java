package com.injun.quiz.service;


import com.injun.quiz.domain.Attempt;
import com.injun.quiz.domain.Quiz;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuizService {
    Mono<Quiz> createQuiz();

    boolean checkAttempt(Attempt attempt);

    Flux<Attempt> getStaticsForUser(String alias);

    Mono<Attempt> getResultById(long id);
}

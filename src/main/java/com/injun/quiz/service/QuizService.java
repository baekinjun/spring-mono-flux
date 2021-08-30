package com.injun.quiz.service;


import com.injun.quiz.domain.Quiz;
import reactor.core.publisher.Mono;

public interface QuizService {
    Mono<Quiz> createQuiz();
}

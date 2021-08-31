package com.injun.quiz.service;

import com.injun.quiz.domain.Attempt;
import com.injun.quiz.domain.Quiz;
import com.injun.quiz.domain.User;
import com.injun.quiz.repository.AttemptRepository;
import com.injun.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final GeneratorService generatorService;
    private final UserRepository userRepository;
    private final AttemptRepository attemptRepository;

    @Override
    public Mono<Quiz> createQuiz() {
        int factorA = generatorService.randomFactor();
        int factorB = generatorService.randomFactor();
        Quiz quiz = new Quiz(factorA, factorB);
        return Mono.just(quiz);
    }

    @Override
    public boolean checkAttempt(Attempt attempt) {
        //유저가 존재하는가?
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());
        Assert.isTrue(!attempt.isCorrect(), "Unable to send in graded state");

        //스코어
        boolean isCorrect = attempt.getResultAttempt() == attempt.getQuiz().getFactorA() * attempt.getQuiz().getFactorB();

        //복사 카프카 복사 가용성 최소 3건이상 해야한다.
        Attempt checkAttempt = new Attempt(attempt.getUser(), attempt.getQuiz(), attempt.getResultAttempt(), isCorrect);

        //복사 한것을 저장한다.
        attemptRepository.save(checkAttempt);
        return isCorrect;
    }

    @Override
    public Flux<Attempt> getStaticsForUser(String alias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(alias);
    }

    @Override
    public Mono<Attempt> getResultById(long id) {
        return null;
    }
}

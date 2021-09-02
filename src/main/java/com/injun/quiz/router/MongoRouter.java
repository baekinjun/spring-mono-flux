package com.injun.quiz.router;

import com.injun.quiz.domain.User;
import com.injun.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//router 가있다.
@RequiredArgsConstructor
@Configuration
public class MongoRouter {
    private final UserRepository db;

    @Bean
    public RouterFunction<ServerResponse> findAll() {
        final RequestPredicate predicate = RequestPredicates
                .GET("/find")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));
        RouterFunction<ServerResponse> response = RouterFunctions.route(predicate, request -> {
            Flux<User> mapper = db.findAll();
            Pageable page = PageRequest.of(0, 5);
            db.findRegexByAlias("happy-john").collectList().subscribe(System.out::println);
            db.findByAliasWithPage("john", page).collectList().subscribe(System.out::println);
            User john = new User("john", "happy-john", "john", "john@test.com");
            db.insert(john).subscribe(System.out::println);
            User tom = new User("tom", "happy-tom", "tom", "tom@test.com");
            db.insert(tom).subscribe(System.out::println);

            Mono<ServerResponse> res = ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromProducer(mapper, User.class));
            return res;
        });
        return response;
    }
}

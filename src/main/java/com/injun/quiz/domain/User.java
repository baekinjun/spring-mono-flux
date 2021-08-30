package com.injun.quiz.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

//카프카는 jpa 사용안한다 리액터사용
@Document(collection = "users")
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {
    @Id
    private final String userid;

    private final String alias;
}

package com.injun.quiz.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

//카프카는 jpa 사용안한다 리액터사용
@Document(collection = "users")
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private String id;
    private String alias;
    private String name;
    private String email;
}

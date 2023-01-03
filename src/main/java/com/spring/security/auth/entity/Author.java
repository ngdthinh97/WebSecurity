package com.spring.security.auth.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "blog")
public class Author {

    private String name;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}

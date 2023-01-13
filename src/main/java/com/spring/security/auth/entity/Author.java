package com.spring.security.auth.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "blog")
public class Author {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;

    private String address;

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }
}

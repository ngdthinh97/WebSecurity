package com.spring.security.auth.entity.Es;

import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private int age;

    private String alphaName;

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

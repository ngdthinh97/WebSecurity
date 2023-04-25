package com.spring.security.auth.entity.Es;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "vehicles")
public class Vehicle {

    private Long price;
    private String color;
    private String make;
    private String sold;
    private String condition;
}

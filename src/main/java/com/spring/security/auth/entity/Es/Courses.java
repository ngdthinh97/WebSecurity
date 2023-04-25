package com.spring.security.auth.entity.Es;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "courses")
public class Courses {

    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String name;
    private String room;

    private Professor professor;

    @Field(name = "students_enrolled", type = FieldType.Text)
    private String studentsEnrolled;
    @Field(name = "course_publish_date", type = FieldType.Text)
    private String coursePublishDate;
    @Field(name = "course_description", type = FieldType.Text)
    private String courseDescription;

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Professor{

        private String name;
        private String department;

        @Field(name = "facutly_type", type = FieldType.Text)
        private String facutlyType;

        private String email;
    }

}

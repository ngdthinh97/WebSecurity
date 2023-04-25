package com.spring.security.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseModel extends Paging {

    private String name;
    private String room;
    private StudentEnronlled studentEnronlled;

    @Getter
    @Setter
    public static class StudentEnronlled{
        private int gte;
        private int lte;
    }
}

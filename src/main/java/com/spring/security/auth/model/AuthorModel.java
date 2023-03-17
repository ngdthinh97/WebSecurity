package com.spring.security.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
public class AuthorModel {

    @Nullable
    private String id;
    private String name;
    private String title;
    private String address;
    private Paging paging;
    private int age;
    private String alphaName;
}

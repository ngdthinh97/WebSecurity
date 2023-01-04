package com.spring.security.auth.controller;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/list")
    public ResponseEntity<Page<Article>> findAuthors(@RequestBody AuthorModel authorModel) {

        return new ResponseEntity<>(authorService.findByAuthorsName(authorModel), HttpStatus.OK);
    }
}

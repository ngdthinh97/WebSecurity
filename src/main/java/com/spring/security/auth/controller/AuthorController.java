package com.spring.security.auth.controller;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    ArticleService authorService;

    @GetMapping("/list")
    public ResponseEntity<Page<Article>> findAuthors(@RequestBody AuthorModel authorModel) {

        return new ResponseEntity<>(authorService.findByAuthorsName(authorModel), HttpStatus.OK);
    }

    @GetMapping("/list_article")
    public ResponseEntity<Page<Article>> findAll(@RequestBody Paging paging) {

        return new ResponseEntity<>(authorService.findAll(paging), HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<Article> findAuthorByName(@RequestBody AuthorModel authorModel) {

        return new ResponseEntity<>(authorService.findByAuthorByName(authorModel), HttpStatus.OK);
    }

    @PostMapping("/update/{id}/id")
    public ResponseEntity<Article> updateAuthorById(@RequestBody AuthorModel authorModel, @RequestParam String id) {

        return new ResponseEntity<>(authorService.updateAuthorById(authorModel,id ), HttpStatus.OK);
    }

    @PostMapping("/update/{name}/name")
    public ResponseEntity<Article> updateAuthorByName(@RequestBody AuthorModel authorModel, @PathVariable String name) {

        return new ResponseEntity<>(authorService.updateAuthorByName(authorModel, name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Author> createAuthorByName(@RequestBody AuthorModel authorModel) {

        return new ResponseEntity<>(authorService.createAuthor(authorModel), HttpStatus.OK);
    }

}

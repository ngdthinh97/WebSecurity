package com.spring.security.auth.service;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import org.springframework.data.domain.Page;

public interface ArticleService {

    //Article-------------------------------------------------
    Page<Article> findByAuthorsName(AuthorModel authorModel);

    Page<Article> findAll(Paging paging);

    Article findByAuthorByName(AuthorModel authorModel);

    Article updateAuthorById(AuthorModel authorModel, String authorId);

    Article updateAuthorByName(AuthorModel authorModel, String name);


    //Author-------------------------------------------------
    Author createAuthor(AuthorModel authorModel);
}

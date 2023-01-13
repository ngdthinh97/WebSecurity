package com.spring.security.auth.service;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Page<Article> findByAuthorsName(AuthorModel authorModel);

    Article findByAuthorByName(AuthorModel authorModel);

    Article updateAuthorById(AuthorModel authorModel, String authorId);

    Article updateAuthorByName(AuthorModel authorModel, String name);

    Article createAuthor(AuthorModel authorModel);
}

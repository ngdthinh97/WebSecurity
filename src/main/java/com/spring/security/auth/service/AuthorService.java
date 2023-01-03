package com.spring.security.auth.service;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {

    Page<Article> findByAuthorsName(AuthorModel authorModel);
}

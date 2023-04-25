package com.spring.security.auth.service.es;

import com.spring.security.auth.entity.Es.Author;
import com.spring.security.auth.model.AuthorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface AuthorService {

    // Author-------------------------------------------------
    Page<Author> findByAuthorsName(AuthorModel authorModel);
    SearchHits<Author> findByAuthorName(AuthorModel authorModel);
    Author createAuthor(AuthorModel authorModel);
    Author updateAuthor(String id, AuthorModel authorModel);
}

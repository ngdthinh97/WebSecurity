package com.spring.security.auth.common.es.api;

import com.spring.security.auth.entity.Es.Article;
import com.spring.security.auth.entity.Es.Author;
import com.spring.security.auth.model.AuthorModel;

public interface ArAuEsApi extends CommonEsApi{

    Article save(Article article,  Author authorModel);
    Author createAuthor(AuthorModel authorModel);
    void deleteArticleById(String id);
    Author saveAuthor(Author author);
}

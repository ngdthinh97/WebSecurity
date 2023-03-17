package com.spring.security.auth.service.es;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;

public interface ArticleService {

    //Article-------------------------------------------------

    Page<Article> findAll(Paging paging);

    Article findByAuthorByName(AuthorModel authorModel);

    Article createArticle (ArticleModel articleModel);
    
    Article updateAuthorById(AuthorModel authorModel, String authorId);

    Article updateAuthorByName(AuthorModel authorModel, String name);

    void deleteArticleById(String id);

    ResponseEntity<String> test();
}

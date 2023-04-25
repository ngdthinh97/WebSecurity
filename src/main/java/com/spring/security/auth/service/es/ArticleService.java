package com.spring.security.auth.service.es;

import com.spring.security.auth.entity.Es.Article;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ArticleService {

    //Article-------------------------------------------------

    Page<Article> findAll(Paging paging);

    List<SearchHit<Article>> matchAll(Paging paging);

    List<SearchHit<Article>> matchFieldArticle(ArticleModel articleModel);

    List<SearchHit<Article>> boolTitleAndId(String id, ArticleModel articleModel);

    Article findByAuthorByName(AuthorModel authorModel);

    Article createArticle (ArticleModel articleModel);
    
    Article updateAuthorById(AuthorModel authorModel, String authorId);

    Article updateAuthorByName(AuthorModel authorModel, String name);

    void deleteArticleById(String id);

    ResponseEntity<String> test();
}

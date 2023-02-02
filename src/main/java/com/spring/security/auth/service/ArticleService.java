package com.spring.security.auth.service;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface ArticleService {

    //Article-------------------------------------------------
    Page<Article> findByAuthorsName(AuthorModel authorModel);

    Page<Article> findAll(Paging paging);

    Article findByAuthorByName(AuthorModel authorModel);

    Article createArticle (ArticleModel articleModel);
    
    Article updateAuthorById(AuthorModel authorModel, String authorId);

    Article updateAuthorByName(AuthorModel authorModel, String name);

    void deleteArticleById(String id);

	// Author-------------------------------------------------

    SearchHits<Author> findByAuthorName(AuthorModel authorModel);

	Author createAuthor(AuthorModel authorModel);

}

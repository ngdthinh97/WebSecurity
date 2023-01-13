package com.spring.security.auth.common.es.api;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

public interface EsApi {

    SearchHits<?> searchByKey(Query query, Class<?> classType);

    SearchHits<?> searchAll(Query query, Class<?> classType);

    Article save(Article article, AuthorModel authorModel);

    Author createAuthor(AuthorModel authorModel);
}

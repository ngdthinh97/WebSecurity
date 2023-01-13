package com.spring.security.auth.common.es.api.impl;

import com.spring.security.auth.common.es.api.EsApi;
import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.repository.es.ArticleRepository;
import com.spring.security.auth.repository.es.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class EsApiImpl implements EsApi {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public SearchHits<?> searchByKey(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("blog"));
    }

    @Override
    public SearchHits<?> searchAll(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("blog"));
    }

    @Transactional
    @Override
    public Article save(Article article, AuthorModel authorModel) {

        Author author = Author.builder()
                .id(authorModel.getId())
                .name(authorModel.getName())
                .address(authorModel.getAddress())
                .build();
        article.setAuthors(Arrays.asList(author));

        // save by jpa
        Optional<Article> saved = Optional.of(articleRepository.save(article));

        if (saved.isPresent()) {
            log.debug("---------- Save Article {}", saved);
            return saved.get();
        }
        log.debug("---------- Data saved not present {}", saved);
        return null;
    }

    @Override
    public Author createAuthor(AuthorModel authorModel) {

        Author author = Author.builder()
                .name(authorModel.getName())
                .address(authorModel.getAddress())
                .build();

        Optional<Author> saved = Optional.of(authorRepository.save(author));
        if (saved.isPresent()) {
            log.debug("---------- Save Author {}", saved);
            return saved.get();
        }

        return null;
    }
}

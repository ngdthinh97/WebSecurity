package com.spring.security.auth.service.es.impl;

import com.spring.security.auth.common.es.EScommon;
import com.spring.security.auth.common.es.api.EsApi;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.repository.es.AuthorRepository;
import com.spring.security.auth.service.es.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private EsApi esApi;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public SearchHits<Author> findByAuthorName(AuthorModel authorModel) {

        Query byKeyRequestBuilder = EScommon.findAuthorByName(authorModel.getName());
        @SuppressWarnings("unchecked")
        SearchHits<Author> searchHits = (SearchHits<Author>) esApi.searchByKey(byKeyRequestBuilder, Author.class);
        return searchHits;
    }

    @Override
    public Page<Author> findByAuthorsName(AuthorModel authorModel) {

        Paging page = authorModel.getPaging();
        Pageable pageable = PageRequest.of(page.getPageNum(), page.getPageSize());
//        PageRequest.of(page.getPageNum(), page.getPageSize(), Sort.Direction.ASC);
        Page<Author> authors = authorRepository.findByAuthorsNameUsingCustomQuery(authorModel.getName(), pageable);
        return authors;
    }

    @Override
    public Author createAuthor(AuthorModel authorModel) {
        log.debug("--------- Create Article by name ---------");
        Author created = esApi.createAuthor(authorModel);
        log.debug("--------- Create Article Success ---------");
        return created;
    }

    @Override
    public Author updateAuthor(String id, AuthorModel authorModel) {
//        Query byKeyRequestBuilder2 = EScommon.findAuthorByName("thinh2");
        Query byKeyRequestBuilder = EScommon.findAuthorById(id);
        SearchHits<Author> searchHits = (SearchHits<Author>) esApi.searchByKey(byKeyRequestBuilder, Author.class);
        SearchHit<Author> first = searchHits.stream().findFirst().orElseGet(null);
        if (ObjectUtils.isNotEmpty(first)) {
            Author author = first.getContent();
            author.setName(authorModel.getName());
            author.setAddress(authorModel.getAddress());
            return esApi.saveAuthor(author);
        } else {
            return null;
        }
    }
}

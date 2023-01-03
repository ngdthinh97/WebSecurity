package com.spring.security.auth.service.impl;

import com.spring.security.auth.entity.Article;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.repository.es.ArticleRepository;
import com.spring.security.auth.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Page<Article> findByAuthorsName(AuthorModel authorModel) {

        Paging page = authorModel.getPaging();

        Pageable pageable = PageRequest.of(page.getPageNum(), page.getPageSize());

//        PageRequest.of(page.getPageNum(), page.getPageSize(), Sort.Direction.ASC);
        Page<Article> articles = articleRepository.findByAuthorsNameUsingCustomQuery(authorModel.getName(), pageable);

        return articles;
    }
}

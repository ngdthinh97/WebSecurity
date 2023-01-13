package com.spring.security.auth.service.impl;

import com.spring.security.auth.common.es.EScommon;
import com.spring.security.auth.common.es.api.EsApi;
import com.spring.security.auth.entity.Article;
import com.spring.security.auth.entity.Author;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.repository.es.ArticleRepository;
import com.spring.security.auth.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EsApi esApi;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    private final static Map<String, String> ES_ARTICLE_AUTHOR_KEY = new HashMap<>();
    static {
        ES_ARTICLE_AUTHOR_KEY.put("author_name", "authors.name");
        ES_ARTICLE_AUTHOR_KEY.put("author_id", "authors.id");
    }

    @Override
    public Page<Article> findByAuthorsName(AuthorModel authorModel) {


        Paging page = authorModel.getPaging();
        Pageable pageable = PageRequest.of(page.getPageNum(), page.getPageSize());

//        PageRequest.of(page.getPageNum(), page.getPageSize(), Sort.Direction.ASC);
        Page<Article> articles = articleRepository.findByAuthorsNameUsingCustomQuery(authorModel.getName(), pageable);

        return articles;
    }

    @Override
    public Page<Article> findAll(Paging paging) {

        log.debug("--------- Find All Article ---------");
        Paging page = paging;
        Pageable pageable = PageRequest.of(page.getPageNum(), page.getPageSize());
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles;
    }

    @Override
    public Article findByAuthorByName(AuthorModel authorModel) {
        String articleTitle = "Spring Data Elasticsearch";
        QueryBuilder allQueries = new BoolQueryBuilder()
                .must(boolQuery()
                        .should(matchPhraseQuery("title", articleTitle))
                        .must(nestedQuery("authors", boolQuery()
                                .should(matchPhraseQuery("authors.name", authorModel.getName())), ScoreMode.None)));

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(allQueries)
                .build();

//        debug
//        elasticsearchTemplate.search(new NativeSearchQueryBuilder()
//                .withQuery(new BoolQueryBuilder()
//                        .must(boolQuery()
//                                .should(matchPhraseQuery("title", articleTitle))
//                                .must(nestedQuery("authors", boolQuery()
//                                        .should(matchPhraseQuery("authors.name", "thinh")), ScoreMode.None))))
//                .build(), Article.class, IndexCoordinates.of("blog"));

        SearchHits<Article> articles = elasticsearchTemplate.search(searchQuery, Article.class, IndexCoordinates.of("blog"));
        Article article = articles.getSearchHit(0).getContent();
        return article;
    }

    @Override
    public Article updateAuthorById(AuthorModel authorModel, String authorId) {

        log.debug("--------- Update Article by Id ---------");
        Query byKeyRequestBuilder = EScommon.findAuthorByKeyRequestBuilder("authors.id", authorId, authorModel);
        SearchHits<Article> searchHits = (SearchHits<Article>) esApi.searchByKey(byKeyRequestBuilder, Article.class);
        Article saved = esApi.save(searchHits.getSearchHit(0).getContent(), authorModel);
        log.debug("--------- Update Article Success ---------");
        return saved;
    }

    @Override
    public Article updateAuthorByName(AuthorModel authorModel, String name) {

        log.debug("--------- Update Article by name ---------");
        Query byKeyRequestBuilder = EScommon.findAuthorByKeyRequestBuilder("authors.name", name, authorModel);
        SearchHits<Article> searchHits = (SearchHits<Article>) esApi.searchByKey(byKeyRequestBuilder, Article.class);
        Article saved = esApi.save(searchHits.getSearchHit(0).getContent(), authorModel);
        log.debug("--------- Update Article Success ---------");
        return saved;
    }

    @Override
    public Author createAuthor(AuthorModel authorModel) {
        log.debug("--------- Create Article by name ---------");
        Author created = esApi.createAuthor(authorModel);
        log.debug("--------- Create Article Success ---------");
        return created;
    }

    @Override
    public void deleteArticleById(String id) {
        log.debug("--------- Delete Article by id ---------");
        Query byKeyRequestBuilder = EScommon.findAuthorByKeyRequestBuilder("id", id, authorModel);
        SearchHits<Article> searchHits = (SearchHits<Article>) esApi.searchByKey(byKeyRequestBuilder, Article.class);

        esApi.deleteArticleById(id);
    }
}
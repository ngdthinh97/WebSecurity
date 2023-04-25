package com.spring.security.auth.service.es.impl;

import com.spring.security.auth.common.es.EScommon;
import com.spring.security.auth.common.es.api.ArAuEsApi;
import com.spring.security.auth.entity.Es.Article;
import com.spring.security.auth.entity.Es.Author;
import com.spring.security.auth.model.ArticleModel;
import com.spring.security.auth.model.AuthorModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.repository.es.ArticleRepository;
import com.spring.security.auth.service.es.ArticleService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	@Qualifier("arAuEsApiImpl")
	private ArAuEsApi arAuEsApi;

	@Autowired
	private ElasticsearchOperations elasticsearchTemplate;

	private final static Map<String, String> ES_ARTICLE_AUTHOR_KEY = new HashMap<>();
	static {
		ES_ARTICLE_AUTHOR_KEY.put("author_name", "authors.name");
		ES_ARTICLE_AUTHOR_KEY.put("author_id", "authors.id");
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
	public List<SearchHit<Article>> matchAll(Paging paging) {

		log.debug("--------- Match All  ---------");
		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery()).build();
		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.matchAll(nativeSearchQuery, Article.class);
		return searchHits.getSearchHits();
	}

	@Override
	public List<SearchHit<Article>> matchFieldArticle(ArticleModel articleModel) {

		log.debug("--------- Match Field  ---------");

		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQuery("title", articleModel.getTitle())).build();

		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.searchByKey(nativeSearchQuery, Article.class);
		return searchHits.getSearchHits();
	}

	@Override
	public List<SearchHit<Article>> boolTitleAndId(String id, ArticleModel articleModel) {

		log.debug("--------- Bool Must Title and Id  ---------");

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("title", articleModel.getTitle()))
				.must(QueryBuilders.matchQuery("id",id));

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.searchByKey(searchQuery, Article.class);

		return searchHits.getSearchHits();
	}

	@Override
	public Article findByAuthorByName(AuthorModel authorModel) {
		String articleTitle = "Spring Data Elasticsearch";
		QueryBuilder allQueries = new BoolQueryBuilder()
				.must(boolQuery().should(matchPhraseQuery("title", articleTitle)).must(nestedQuery("authors",
						boolQuery().should(matchPhraseQuery("authors.name", authorModel.getName())), ScoreMode.None)));

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(allQueries).build();

//        debug
//        elasticsearchTemplate.search(new NativeSearchQueryBuilder()
//                .withQuery(new BoolQueryBuilder()
//                        .must(boolQuery()
//                                .should(matchPhraseQuery("title", articleTitle))
//                                .must(nestedQuery("authors", boolQuery()
//                                        .should(matchPhraseQuery("authors.name", "thinh")), ScoreMode.None))))
//                .build(), Article.class, IndexCoordinates.of("blog"));

		SearchHits<Article> articles = elasticsearchTemplate.search(searchQuery, Article.class,
				IndexCoordinates.of("blog"));
		Article article = articles.getSearchHit(0).getContent();
		return article;
	}

	@Override
	public Article updateAuthorById(AuthorModel authorModel, String authorId) {

		log.debug("--------- Update Article by Id ---------");
		Author author = Author.builder()
				.address(authorModel.getAddress())
				.name(authorModel.getName())
					.build();

		Query byKeyRequestBuilder = EScommon.findAuthorByKeyRequestBuilder("authors.id", authorId, authorModel);
		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.searchByKey(byKeyRequestBuilder, Article.class);
		Article saved = arAuEsApi.save(searchHits.getSearchHit(0).getContent(), author);
		log.debug("--------- Update Article Success ---------");
		return saved;
	}

	@Override
	public Article updateAuthorByName(AuthorModel authorModel, String name) {

		log.debug("--------- Update Article by name ---------");
		Author author = Author.builder()
				.address(authorModel.getAddress())
				.name(authorModel.getName())
					.build();

		Query byKeyRequestBuilder = EScommon.findAuthorByKeyRequestBuilder("authors.name", name, authorModel);
		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.searchByKey(byKeyRequestBuilder, Article.class);
		Article saved = arAuEsApi.save(searchHits.getSearchHit(0).getContent(), author);
		log.debug("--------- Update Article Success ---------");
		return saved;
	}

	@Override
	public void deleteArticleById(String id) {
		log.debug("--------- Delete Article by id ---------");
		Query byKeyRequestBuilder = EScommon.findArticleByIdRequestBuilder(id);
		SearchHits<Article> searchHits = (SearchHits<Article>) arAuEsApi.searchByKey(byKeyRequestBuilder, Article.class);
		arAuEsApi.deleteArticleById(id);
	}

	@Override
	public Article createArticle(ArticleModel articleModel) {

		log.debug("--------- Create Article ---------");
		ArticleModel.Author author = articleModel.getAuthor();
		Author authorIndex = new Author();
		if (StringUtils.isNotEmpty(author.getName())) {
			Query byKeyRequestBuilder = EScommon.findAuthorByName(author.getName());
			@SuppressWarnings("unchecked")
			SearchHits<Author> authorHit = (SearchHits<Author>) arAuEsApi.searchByKey(byKeyRequestBuilder, Author.class);
			authorIndex = authorHit.getSearchHit(0).getContent();
		}

		Article artile = Article.builder().title(articleModel.getTitle())
					.authors(Arrays.asList(authorIndex)).build();
		Article saved = arAuEsApi.save(artile, authorIndex);

		return saved;
	}

	@Override
	public ResponseEntity<String> test(){
		return ResponseEntity.badRequest().body("Either 'id' or 'name' must be set");
	}
}
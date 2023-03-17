package com.spring.security.auth.common.es;

import com.spring.security.auth.model.AuthorModel;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class EScommon {

	// Article common --------------------------------------------------------->
    public static Query findAuthorByKeyRequestBuilder(String key, String nameValue , Object objectData) {

        if (!(key.equals("authors.name") || key.equals("authors.id"))) {
            throw new RuntimeException(key);
        }

        AuthorModel authorModel = (AuthorModel) objectData;
        String articleTitle = authorModel.getTitle();
        QueryBuilder allQueries = new BoolQueryBuilder()
                .must(boolQuery()
                        .should(matchPhraseQuery("title", articleTitle))
                        .must(nestedQuery("authors", boolQuery()
                                .should(matchPhraseQuery(key, nameValue)), ScoreMode.None)));

        // Ctrl + alt  + v -> auto generate data type
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(allQueries)
                .build();

        return build;
    }

    public static Query findArticleByIdRequestBuilder(String id) {

//        String articleTitle = authorModel.getTitle();
//        QueryBuilder allQueries = new BoolQueryBuilder()
//                .must(boolQuery()
//                        .should(matchPhraseQuery("title", articleTitle))
//                        .must(nestedQuery("authors", boolQuery()
//                                .should(matchPhraseQuery(key, nameValue)), ScoreMode.None)));
//
//        NativeSearchQuery build = new NativeSearchQueryBuilder()
//                .withQuery(allQueries)
//                .build();

        return null;
    }
    
    // Author common --------------------------------------------------------->
    
	public static Query findAuthorByName(String name) {

        QueryBuilder boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("name",name));

        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(boolQuery).build();
		return build;
	}

    public static Query findAuthorById(String id) {

        QueryBuilder boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery("_id", id));

        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(boolQuery).build();
        return build;
    }

}

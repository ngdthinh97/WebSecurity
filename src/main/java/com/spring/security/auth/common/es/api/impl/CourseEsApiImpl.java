package com.spring.security.auth.common.es.api.impl;

import com.spring.security.auth.common.es.api.CourseEsApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseEsApiImpl implements CourseEsApi {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public SearchHits<?> searchByKey(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("courses"));
    }

    @Override
    public SearchHits<?> searchAll(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("courses"));
    }

    @Override
    public SearchHits<?> matchAll(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("courses"));
    }
}

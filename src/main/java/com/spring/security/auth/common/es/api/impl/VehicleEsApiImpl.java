package com.spring.security.auth.common.es.api.impl;

import com.spring.security.auth.common.es.api.VehicleEsApi;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleEsApiImpl implements VehicleEsApi {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public SearchHits<?> searchByKey(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("vehicles"));
    }

    @Override
    public SearchHits<?> searchAll(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("vehicles"));
    }

    @Override
    public SearchHits<?> matchAll(Query query, Class<?> classType) {
        return elasticsearchTemplate.search(query, classType, IndexCoordinates.of("vehicles"));
    }
}

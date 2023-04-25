package com.spring.security.auth.common.es.api;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

public interface CommonEsApi {

    //    GET ------------------------------------------------>
    SearchHits<?> searchByKey(Query query, Class<?> classType);
    SearchHits<?> searchAll(Query query, Class<?> classType);
    SearchHits<?> matchAll(Query query, Class<?> classType);
}

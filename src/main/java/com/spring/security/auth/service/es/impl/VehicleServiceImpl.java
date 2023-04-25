package com.spring.security.auth.service.es.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.security.auth.common.es.api.VehicleEsApi;
import com.spring.security.auth.entity.Es.Vehicle;
import com.spring.security.auth.model.CourseModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.model.out.AggregationResponse;
import com.spring.security.auth.service.es.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleEsApi vehicleEsApi;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public List<SearchHit<Vehicle>> matchAll(Paging paging) {
        log.debug("--------- Find All Courses ---------");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        SearchHits<Vehicle> searchHits = (SearchHits<Vehicle>) vehicleEsApi.searchAll(nativeSearchQuery, Vehicle.class);
        return searchHits.getSearchHits();
    }

    @Override
    public List<SearchHit<Vehicle>> boolCourseAndName(CourseModel courseModel) {
        return null;
    }

    @Override
    public Map<String, AggregationResponse.AggregationType> vehicleReport() throws JsonProcessingException {

        log.debug("--------- Report vehicle  ---------");

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("car_conditions")
                .field("condition.keyword");

        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withAggregations(aggregation).build();

        SearchHits<Vehicle> searchHits = (SearchHits<Vehicle>) vehicleEsApi.searchAll(queryBuilder, Vehicle.class);
        Map<String, AggregationResponse.AggregationType> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(BytesRef.class, new BytesRefSerializer());
//        module.addSerializer(Number.class, new NumberSerializer());
//        module.addSerializer(String.class, new StringSerializer());
//        module.addSerializer(ParsedStringTerms.ParsedBucket.class, new ParsedBucketSerializer());
//        objectMapper.registerModule(module);

        Aggregations aggregations = (Aggregations) searchHits.getAggregations().aggregations();
        Set<String> strings = aggregations.getAsMap().keySet();
        String name = strings.iterator().next();
        AggregationResponse agg = AggregationResponse.builder().build();
        AggregationResponse.AggregationType type = AggregationResponse.AggregationType.builder().build();

        List<AggregationResponse.AggregationType.Bucket> lstBuckets = new ArrayList<>();
        if (aggregations != null) {
            ParsedStringTerms termsAggregation = aggregations.get(name);
            if (termsAggregation != null) {
                List<ParsedStringTerms.ParsedBucket> buckets = ( List<ParsedStringTerms.ParsedBucket>) termsAggregation.getBuckets();
                buckets.forEach(s -> {
                    String key = s.getKeyAsString();
                    long docCount = s.getDocCount();
                    AggregationResponse.AggregationType.Bucket bucket = AggregationResponse.AggregationType.Bucket.builder().key(key).docCount(docCount).build();
                    lstBuckets.add(bucket);
                });
            }
            type.setBuckets(lstBuckets);
            map.put(name, type);
            agg.setAggregations(map);
        }
        return map;
    }
}

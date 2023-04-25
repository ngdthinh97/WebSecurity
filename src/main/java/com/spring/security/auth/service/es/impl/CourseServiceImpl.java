package com.spring.security.auth.service.es.impl;

import com.spring.security.auth.common.es.api.ArAuEsApi;
import com.spring.security.auth.common.es.api.CourseEsApi;
import com.spring.security.auth.entity.Es.Courses;
import com.spring.security.auth.model.CourseModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.service.es.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    @Qualifier("courseEsApiImpl")
    private CourseEsApi courseEsApi;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Override
    public List<SearchHit<Courses>> matchAll(Paging paging) {

        log.debug("--------- Find All Courses ---------");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        SearchHits<Courses> searchHits = (SearchHits<Courses>) courseEsApi.searchAll(nativeSearchQuery, Courses.class);
        return searchHits.getSearchHits();
    }

    @Override
    public List<SearchHit<Courses>> boolCourseAndName(CourseModel courseModel) {

        log.debug("--------- Find Courses in range base on name and room-name ---------");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name",courseModel.getName()))
                        .mustNot(QueryBuilders.matchQuery("room",courseModel.getRoom()))
                        .should(QueryBuilders.rangeQuery("students_enrolled")
                                .gte(courseModel.getStudentEnronlled().getGte())
                                .lte(courseModel.getStudentEnronlled().getLte())));
//                .minimumShouldMatch(1); turn should into must

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
        SearchHits<Courses> searchHits = (SearchHits<Courses>) courseEsApi.searchAll(nativeSearchQuery, Courses.class);
        return searchHits.getSearchHits();
    }
}

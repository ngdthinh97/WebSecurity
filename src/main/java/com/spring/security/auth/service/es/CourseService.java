package com.spring.security.auth.service.es;

import com.spring.security.auth.entity.Es.Courses;
import com.spring.security.auth.model.CourseModel;
import com.spring.security.auth.model.Paging;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

public interface CourseService {

    List<SearchHit<Courses>> matchAll(Paging paging);

    List<SearchHit<Courses>> boolCourseAndName(CourseModel courseModel);
}

package com.spring.security.auth.controller.es;

import com.spring.security.auth.entity.Es.Courses;
import com.spring.security.auth.model.CourseModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.service.es.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/match_all")
    public ResponseEntity<List<SearchHit<Courses>>> findAll(Paging paging) {
        return new ResponseEntity<>(courseService.matchAll(paging), HttpStatus.OK);
    }

    @GetMapping("/bool-Name-Range")
    public ResponseEntity<List<SearchHit<Courses>>> findCourseByNameAndRange(
            @RequestBody CourseModel courseModel) {

        return new ResponseEntity<>(courseService.boolCourseAndName(courseModel), HttpStatus.OK);
    }
}

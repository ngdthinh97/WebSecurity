package com.spring.security.auth.service.es;

import com.spring.security.auth.entity.Es.Vehicle;
import com.spring.security.auth.model.CourseModel;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.model.out.AggregationResponse;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VehicleService {

    List<SearchHit<Vehicle>> matchAll(Paging paging);

    List<SearchHit<Vehicle>> boolCourseAndName(CourseModel courseModel);

    Map<String, AggregationResponse.AggregationType> vehicleReport() throws IOException;
}

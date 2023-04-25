package com.spring.security.auth.controller.es;

import com.spring.security.auth.entity.Es.Vehicle;
import com.spring.security.auth.model.Paging;
import com.spring.security.auth.model.out.AggregationResponse;
import com.spring.security.auth.service.es.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/match_all")
    public ResponseEntity<List<SearchHit<Vehicle>>> findAll(Paging paging) {
        return new ResponseEntity<>(vehicleService.matchAll(paging), HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<Map<String, AggregationResponse.AggregationType>> vehicleReport() throws IOException {
        return new ResponseEntity<>(vehicleService.vehicleReport(), HttpStatus.OK);
    }
}

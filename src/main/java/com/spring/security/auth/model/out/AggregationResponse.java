package com.spring.security.auth.model.out;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class AggregationResponse {

    private Map<String, AggregationType> aggregations;

    @Getter
    @Setter
    @Builder
    public static class AggregationType {

        private List<Bucket> buckets;

        @Getter
        @Setter
        @Builder
        public static class Bucket {

            private String key;
            private long docCount;
        }
    }
}

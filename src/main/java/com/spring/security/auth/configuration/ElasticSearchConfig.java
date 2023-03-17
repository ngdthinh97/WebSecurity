package com.spring.security.auth.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.spring.security.auth.repository.es")
@ComponentScan(basePackages = {"com.spring.security.auth"})
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.server}")
    private String host;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient client() {
        final ClientConfiguration clientConfiguration =
                ClientConfiguration
                        .builder()
                        .connectedTo("localhost:9200")
                        .usingSsl()
                        .withBasicAuth(username,password)
                        .withDefaultHeaders(compatibilityHeaders())
                        .build();

        return RestClients.create(clientConfiguration).rest();
    }

    private HttpHeaders compatibilityHeaders() {
        MultiValueMap<String, String> multiValueMap = new HttpHeaders();
        multiValueMap.add(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7");
        multiValueMap.add(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7");

        HttpHeaders headers = new HttpHeaders();
        headers.addAll(multiValueMap);
        return headers;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
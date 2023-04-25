package com.spring.security.auth.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class RedisConfiguration {

    List<String> clusterNodes = Arrays.asList("127.0.0.1:6379");

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
        jedisConFactory.setHostName("0.0.0.0");
        jedisConFactory.setPort(6379);
        jedisConFactory.setTimeout(10000);
        jedisConFactory.getPoolConfig().setMaxIdle(30);
        jedisConFactory.getPoolConfig().setMinIdle(10);
        jedisConFactory.setUsePool(true);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setExposeConnection(true);
        // No serializer required all serialization done during impl
        //`redisTemplate.setHashKeySerializer(stringRedisSerializer());
//        template.afterPropertiesSet();
//        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public JedisCluster getRedisCluster() {
        Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
        jedisClusterNode.add(new HostAndPort("0.0.0.0", 6379));
        jedisClusterNode.add(new HostAndPort("localhost", 6379));
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setTestOnBorrow(true);
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 10000, 1, 10,  jedisPoolConfig);
        return jedisCluster;
    }
}

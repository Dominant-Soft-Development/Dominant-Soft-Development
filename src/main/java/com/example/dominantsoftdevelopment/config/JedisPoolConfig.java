package com.example.dominantsoftdevelopment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class JedisPoolConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration localhost = new RedisStandaloneConfiguration("redis-17299.c10.us-east-1-2.ec2.cloud.redislabs.com", 17299);
        localhost.setUsername("default");
        localhost.setPassword("cAC6xkpAasyWtSPpIHsX5z8Xq5p7OMG2");
        return new JedisConnectionFactory(localhost);
    }
}

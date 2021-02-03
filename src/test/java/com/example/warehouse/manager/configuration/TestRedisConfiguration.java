package com.example.warehouse.manager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.redis.support.collections.RedisProperties;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
@PropertySource("classpath:application.properties")
public class TestRedisConfiguration {

    private RedisServer redisServer;

    String port = "6370";

    public TestRedisConfiguration() {
        this.redisServer = new RedisServer(Integer.parseInt(port));
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}

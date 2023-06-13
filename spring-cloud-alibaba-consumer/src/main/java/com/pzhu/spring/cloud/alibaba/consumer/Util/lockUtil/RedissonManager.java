package com.pzhu.spring.cloud.alibaba.consumer.Util.lockUtil;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class RedissonManager {


    /**
     * redis的host
     */
    @Value("${spring.redis.host}")
    private String host;


    /**
     * redis的port
     */
    @Value("${spring.redis.port}")
    private String port;


    /**
     * redis的port
     */
    @Value("${spring.redis.password}")
    private String password;


    @Value("${spring.redis.database}")
    private Integer database;


    @Bean
    Redisson RedissionTemplate() {
        Config config = new Config();
        //声明redisson对象
        config.useSingleServer()
            .setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                    .setDatabase(database);
        //得到redisson对象
        return (Redisson) Redisson.create(config);
    }
}

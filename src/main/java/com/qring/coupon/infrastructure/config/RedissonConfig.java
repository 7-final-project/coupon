package com.qring.coupon.infrastructure.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://redis:6379")
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(2);

        config.setCodec(new JsonJacksonCodec());

        return Redisson.create(config);
    }
}

package com.sjiwon.wantedconcurrency.ticket.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisLettuceClientLockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(key, "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(String key) {
        return redisTemplate.delete(key);
    }
}

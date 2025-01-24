package com.qring.coupon.infrastructure.messaging.redis.repository;

import com.qring.coupon.domain.model.constraint.IssuanceCheck;
import com.qring.coupon.domain.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.qring.coupon.infrastructure.util.RedisKeyUtil.getCouponIssueKey;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "RedisRepositoryImpl")
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void issueRequest(Long userId, Long id, int totalQuantity) {
        String key = getCouponIssueKey(id);
        String code = redisTemplate.execute(
                issueRequestScript(),
                List.of(key),                 // KEY
                String.valueOf(userId),       // ARGV[1]
                String.valueOf(totalQuantity) // ARGV[2]
        );

        IssuanceCheck.checkResult(code);
    }

    private RedisScript<String> issueRequestScript() {
        String script = """
                if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                    redis.call('SADD', KEYS[1], ARGV[1])
                    return 'success'
                end
                
                return 'failure'
                """;

        return RedisScript.of(script, String.class);
    }
}

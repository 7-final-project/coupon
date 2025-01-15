package com.qring.coupon.domain.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AdvisoryLockRepository {

    private final JdbcTemplate jdbcTemplate;

    public void getLockByKey(Long key) {
        String sql = "SELECT pg_advisory_xact_lock(?)";
        jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
            ps.setLong(1, key);
            ps.execute();
            return null;
        });
        log.info("키 {}에 대한 락 획득 성공", key);
    }

    public boolean getLockWithTry(Long key, int maxAttempts, long retryIntervalMillis) {
        String sql = "SELECT pg_try_advisory_xact_lock(?)";
        int attempts = 0;

        while (attempts < maxAttempts) {
            attempts++;
            Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, key);

            if (Boolean.TRUE.equals(result)) {
                log.info("키 {}에 대한 락 획득 성공 (시도 횟수: {})", key, attempts);
                return true;
            }

            log.warn("키 {}에 대한 락 획득 실패 (시도 횟수: {}). 재시도 대기 중...", key, attempts);

            try {
                Thread.sleep(retryIntervalMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("락 재시도 중 인터럽트 발생", e);
            }
        }

        log.error("키 {}에 대한 락 획득 실패 (최대 시도 횟수: {})", key, maxAttempts);
        return false;
    }
}

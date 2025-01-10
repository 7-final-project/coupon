package com.qring.coupon.domain.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
@RequiredArgsConstructor
@Slf4j
public class AdvisoryLockRepository {

    private final DataSource dataSource;

    public boolean getLockByKey(Long key) {
        String sql = "SELECT pg_try_advisory_lock(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean acquired = rs.getBoolean(1);
                    if (acquired) {
                        log.info("키 {}에 대한 락 획득 성공", key);
                    } else {
                        log.warn("키 {}에 대한 락 획득 실패", key);
                    }
                    return acquired;
                }
            }
        } catch (SQLException e) {
            log.error("키 {}에 대한 락 획득 중 오류 발생: {}", key, e.getMessage(), e);
            throw new LockOperationException("락 획득 중 데이터베이스 오류 발생", e);
        }
        return false;
    }

    public boolean releaseLockByKey(Long key) {
        String sql = "SELECT pg_advisory_unlock(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean released = rs.getBoolean(1);
                    if (released) {
                        log.info("키 {}에 대한 락 해제 성공", key);
                    } else {
                        log.warn("키 {}에 대한 락 해제 실패", key);
                    }
                    return released;
                }
            }
        } catch (SQLException e) {
            log.error("키 {}에 대한 락 해제 중 오류 발생: {}", key, e.getMessage(), e);
            throw new LockOperationException("락 해제 중 데이터베이스 오류 발생", e);
        }
        return false;
    }
}

class LockOperationException extends RuntimeException {
    public LockOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}


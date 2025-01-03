package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCouponRepository extends JpaRepository<CouponEntity, Long> {

    Optional<CouponEntity> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByNameAndIdNotAndDeletedAtIsNull(String name, Long id);

}

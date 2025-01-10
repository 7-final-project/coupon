package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.CouponEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CouponRepository {

    Optional<CouponEntity> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByNameAndIdNotAndDeletedAtIsNull(String name, Long id);

    Page<CouponEntity> couponEntityPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, String name, String couponStatus, String issuanceStatus, String sort);

    CouponEntity save(CouponEntity couponEntity);

    void deleteAll();
}

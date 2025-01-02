package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;

    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return jpaCouponRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public CouponEntity save(CouponEntity couponEntity) {
        return jpaCouponRepository.save(couponEntity);
    }
}

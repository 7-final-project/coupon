package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;

    public Optional<CouponEntity> findByIdAndDeletedAtIsNull(Long id) {
        return jpaCouponRepository.findById(id);
    }

    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return jpaCouponRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public boolean existsByNameAndIdNotAndDeletedAtIsNull(String name, Long id) {
        return jpaCouponRepository.existsByNameAndIdNotAndDeletedAtIsNull(name, id);
    }

    public CouponEntity save(CouponEntity couponEntity) {
        return jpaCouponRepository.save(couponEntity);
    }

}

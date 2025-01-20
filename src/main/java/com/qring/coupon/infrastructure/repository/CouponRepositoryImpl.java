package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final CouponQueryRepository couponQueryRepository;

    @Override
    public Optional<CouponEntity> findByIdAndDeletedAtIsNull(Long id) {
        return jpaCouponRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Override
    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return jpaCouponRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Override
    public boolean existsByNameAndIdNotAndDeletedAtIsNull(String name, Long id) {
        return jpaCouponRepository.existsByNameAndIdNotAndDeletedAtIsNull(name, id);
    }

    @Override
    public Page<CouponEntity> couponEntityPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, String name, String couponStatus, String issuanceStatus, String sort) {
        return couponQueryRepository.couponEntityPageByDeletedAtIsNullWithConditions(pageable, userId, name, couponStatus, issuanceStatus, sort);
    }

    @Override
    public void deleteAll(){
        jpaCouponRepository.deleteAll();
    }

    @Override
    public CouponEntity save(CouponEntity couponEntity) {
        return jpaCouponRepository.save(couponEntity);
    }
}

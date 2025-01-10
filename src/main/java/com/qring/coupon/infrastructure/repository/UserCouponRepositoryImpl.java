package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.domain.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponRepository {

    private final JpaUserCouponRepository jpaUserCouponRepository;

    @Override
    public boolean existsByUserIdAndCouponEntity(Long userId, CouponEntity couponEntity) {
        return jpaUserCouponRepository.existsByUserIdAndCouponEntity(userId, couponEntity);
    }

    @Override
    public Set<UserCouponEntity> findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(Long userId) {
        return jpaUserCouponRepository.findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(userId);
    }

    @Override
    public Long countByCouponEntityId(Long id){
        return jpaUserCouponRepository.countByCouponEntityId(id);
    }


    @Override
    public UserCouponEntity save(UserCouponEntity userCouponEntity) {
        return jpaUserCouponRepository.save(userCouponEntity);
    }

    @Override
    public void deleteAll() {
        jpaUserCouponRepository.deleteAll();
    }

}
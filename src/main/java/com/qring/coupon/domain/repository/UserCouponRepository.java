package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.UserCouponEntity;

import java.util.Set;

public interface UserCouponRepository {

    boolean existsByUserIdAndCouponEntityId(Long userId, Long id);

    Set<UserCouponEntity> findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(Long userId);

    void deleteAll();

    UserCouponEntity save(UserCouponEntity userCouponEntity);

    Long countByCouponEntityId(Long couponId);
}

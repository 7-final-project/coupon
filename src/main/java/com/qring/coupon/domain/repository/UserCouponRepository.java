package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;

import java.util.Set;

public interface UserCouponRepository {

    boolean existsByUserIdAndCouponEntity(Long userId, CouponEntity couponEntity);

    Set<UserCouponEntity> findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(Long userId);

    void deleteAll();

    UserCouponEntity save(UserCouponEntity userCouponEntity);
}

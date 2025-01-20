package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.UserCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface JpaUserCouponRepository extends JpaRepository<UserCouponEntity, Long> {

    boolean existsByUserIdAndCouponEntityId(Long userId, Long id);

    @Query("select uc from UserCouponEntity uc " +
            "join fetch uc.couponEntity " +
            "where uc.userId = :userId " +
            "and uc.deletedAt is null ")
    Set<UserCouponEntity> findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(@Param("userId") Long userId);

    Long countByCouponEntityId(Long couponId);
}
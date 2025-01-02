package com.qring.coupon.domain.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_user_coupon")
public class UserCouponEntity {

    @Id @Tsid
    @Column(name = "user_coupon_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    @Builder
    public UserCouponEntity(Long userId, CouponEntity couponEntity) {
        this.userId = userId;
        this.couponEntity = couponEntity;
    }

}

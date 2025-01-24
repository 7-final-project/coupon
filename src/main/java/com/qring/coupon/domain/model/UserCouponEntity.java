package com.qring.coupon.domain.model;

import com.qring.coupon.domain.model.constraint.UseStatus;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @Column(name = "use_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UseStatus useStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Builder
    public UserCouponEntity(Long userId, CouponEntity couponEntity, String username) {
        this.userId = userId;
        this.couponEntity = couponEntity;
        this.useStatus = UseStatus.UNUSED;
        this.createdBy = username;
        this.modifiedBy = username;
    }

    public static UserCouponEntity createUserCouponEntity(CouponEntity couponEntity, Long userId, String username) {
        return UserCouponEntity.builder()
                .couponEntity(couponEntity)
                .userId(userId)
                .username(username)
                .build();
    }
}

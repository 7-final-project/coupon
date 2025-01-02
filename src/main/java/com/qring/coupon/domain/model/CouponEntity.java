package com.qring.coupon.domain.model;

import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.domain.model.constraint.StockStatus;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_coupon")
public class CouponEntity {

    @Id @Tsid
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "remain_quantity", nullable = false)
    private int remainQuantity;

    @Column(name = "open_at", nullable = false)
    private LocalDateTime openAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status", nullable = false)
    private CouponStatus couponStatus;

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
    public CouponEntity(String name, int discount, int totalQuantity, LocalDateTime openAt, LocalDateTime expiredAt) {
        this.name = name;
        this.discount = discount;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = totalQuantity;
        this.openAt = openAt;
        this.expiredAt = expiredAt;
        this.couponStatus = CouponStatus.INACTIVE;
    }
}

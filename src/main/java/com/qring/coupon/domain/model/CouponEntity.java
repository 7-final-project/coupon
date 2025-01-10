package com.qring.coupon.domain.model;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.domain.model.constraint.IssuanceStatus;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private int remainingQuantity;

    @Column(name = "open_at", nullable = false)
    private LocalDateTime openAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status", nullable = false)
    private CouponStatus couponStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "issuance_status", nullable = false)
    private IssuanceStatus issuanceStatus;

    @OneToMany(mappedBy = "couponEntity", cascade = CascadeType.REMOVE)
    List<UserCouponEntity> userCouponEntityList = new ArrayList<>();

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
    public CouponEntity(String name, int discount, int totalQuantity, int remainingQuantity, LocalDateTime openAt, LocalDateTime expiredAt, CouponStatus couponStatus, IssuanceStatus issuanceStatus, String username) {
        this.name = name;
        this.discount = discount;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = totalQuantity;
        this.openAt = openAt;
        this.expiredAt = expiredAt;
        this.couponStatus = couponStatus;
        this.issuanceStatus = issuanceStatus;
        this.createdBy = username;
        this.modifiedBy = username;
    }

    public static CouponEntity createCouponEntity(String name, int discount, int totalQuantity, LocalDateTime openAt, LocalDateTime expiredAt, String username) {
        return CouponEntity.builder()
                .name(name)
                .discount(discount)
                .totalQuantity(totalQuantity)
                .remainingQuantity(totalQuantity)
                .openAt(openAt)
                .expiredAt(expiredAt)
                .couponStatus(CouponStatus.INACTIVE)
                .issuanceStatus(IssuanceStatus.OPEN)
                .username(username)
                .build();
    }

    public void decreaseRemainingQuantity() {
        if(this.remainingQuantity <= 0){
            throw new BadRequestException("재고 수량이 부족합니다.");
        }

        this.remainingQuantity -= 1;
    }

    public void modifyCouponEntity(String name, int discount, int totalQuantity, int remainingQuantity, LocalDateTime openAt, LocalDateTime expiredAt, String couponStatus, String issuanceStatus, String username) {
        this.name = name;
        this.discount = discount;
        this.totalQuantity = totalQuantity;
        this.remainingQuantity = remainingQuantity;
        this.openAt = openAt;
        this.expiredAt = expiredAt;
        this.couponStatus = CouponStatus.fromString(couponStatus);
        this.issuanceStatus = IssuanceStatus.fromString(issuanceStatus);
        this.modifiedBy = username;
    }

    public void deletedCouponEntity(String username){
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }
}

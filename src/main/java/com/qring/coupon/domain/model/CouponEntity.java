package com.qring.coupon.domain.model;

import com.qring.coupon.domain.model.constraint.CouponStatus;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    private int remainQuantity;

    @Column(name = "open_at", nullable = false)
    private LocalDateTime openAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status", nullable = false)
    private CouponStatus couponStatus;

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
    public CouponEntity(String name, int discount, int totalQuantity, int remainQuantity, LocalDateTime openAt, LocalDateTime expiredAt, CouponStatus couponStatus, String username) {
        this.name = name;
        this.discount = discount;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = totalQuantity;
        this.openAt = openAt;
        this.expiredAt = expiredAt;
        this.couponStatus = couponStatus;
        this.createdBy = username;
        this.modifiedBy = username;
    }

    public static CouponEntity createCouponEntity(String name, int discount, int totalQuantity, LocalDateTime openAt, LocalDateTime expiredAt, String username) {
        return CouponEntity.builder()
                .name(name)
                .discount(discount)
                .totalQuantity(totalQuantity)
                .remainQuantity(totalQuantity)
                .openAt(openAt)
                .expiredAt(expiredAt)
                .couponStatus(CouponStatus.INACTIVE)
                .username(username)
                .build();
    }

    public void modifyCouponEntity(String name, int discount, int totalQuantity, LocalDateTime openAt, LocalDateTime expiredAt, String username) {
        this.name = name;
        this.discount = discount;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = totalQuantity;
        this.expiredAt = expiredAt;
        this.modifiedBy = username;
    }

    public void markAsDelete(String username){
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }
}

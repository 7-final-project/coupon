package com.qring.coupon.application.v1.res;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponSearchResDTOV1 {

    private CouponPage couponPage;

    public static CouponSearchResDTOV1 of(Page<CouponEntity> couponEntityPage) {
        return CouponSearchResDTOV1.builder()
                .couponPage(CouponPage.from(couponEntityPage))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponPage {

        private List<Coupon> content;
        private PageDetails page;

        public static CouponPage from(Page<CouponEntity> couponEntityPage) {
            return CouponPage.builder()
                    .content(Coupon.from(couponEntityPage.getContent()))
                    .page(PageDetails.from(couponEntityPage))
                    .build();
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Coupon {

            private Long id;
            private String name;
            private int discount;
            private int totalQuantity;
            private int remainQuantity;
            private LocalDateTime openAt;
            private LocalDateTime expiredAt;
            private CouponStatus couponStatus;

            public static List<Coupon> from(List<CouponEntity> couponEntityList) {
                return couponEntityList.stream()
                        .map(Coupon::from)
                        .toList();
            }

            public static Coupon from(CouponEntity couponEntity) {
                return Coupon.builder()
                        .id(couponEntity.getId())
                        .name(couponEntity.getName())
                        .discount(couponEntity.getDiscount())
                        .totalQuantity(couponEntity.getTotalQuantity())
                        .remainQuantity(couponEntity.getRemainQuantity())
                        .openAt(couponEntity.getOpenAt())
                        .expiredAt(couponEntity.getExpiredAt())
                        .couponStatus(couponEntity.getCouponStatus())
                        .build();
            }
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PageDetails {

            private int size;
            private int number;
            private long totalElements;
            private int totalPages;

            public static PageDetails from(Page<CouponEntity> couponEntityPage) {
                return PageDetails.builder()
                        .size(couponEntityPage.getSize())
                        .number(couponEntityPage.getNumber())
                        .totalElements(couponEntityPage.getTotalElements())
                        .totalPages(couponEntityPage.getTotalPages())
                        .build();
            }
        }
    }
}

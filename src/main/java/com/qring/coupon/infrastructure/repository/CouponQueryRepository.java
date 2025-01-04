package com.qring.coupon.infrastructure.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.domain.model.constraint.IssuanceStatus;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.qring.coupon.domain.model.QCouponEntity.couponEntity;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class CouponQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CouponEntity> couponEntityPageByDeletedAtIsNullWithConditions(Pageable pageable, Long userId, String name, String couponStatus, String issuanceStatus, String sort) {
        List<CouponEntity> results = queryFactory
                .selectFrom(couponEntity)
                .where(
                        couponEntity.deletedAt.isNull(),
                        userIdEq(userId),
                        nameLike(name),
                        couponStatusEq(couponStatus),
                        issuanceStatusEq(issuanceStatus)
                )
                .orderBy(
                        orderSpecifier(sort)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = queryFactory
                .select(couponEntity.count())
                .from(couponEntity)
                .where(
                        couponEntity.deletedAt.isNull(),
                        userIdEq(userId),
                        nameLike(name),
                        couponStatusEq(couponStatus),
                        issuanceStatusEq(issuanceStatus)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId != null ? couponEntity.id.eq(userId) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? couponEntity.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression couponStatusEq(String couponStatus) {
        return hasText(couponStatus) ? couponEntity.couponStatus.eq(CouponStatus.fromString(couponStatus)) : null;
    }

    private BooleanExpression issuanceStatusEq(String issuanceStatus) {
        return hasText(issuanceStatus) ? couponEntity.issuanceStatus.eq(IssuanceStatus.fromString(issuanceStatus)) : null;
    }

    private OrderSpecifier<?> orderSpecifier(String sort) {
        if (sort == null) {
            sort = "NEWEST";
        }

        return switch (sort) {
            case "OLDEST" -> couponEntity.createdAt.asc();
            case "NAME_ASC" -> couponEntity.name.asc();
            case "NAME_DESC" -> couponEntity.name.desc();
            default -> couponEntity.createdAt.desc();
        };
    }
}

package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.v1.res.UserCouponPostResDTOV1;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.infrastructure.docs.UserCouponControllerSwagger;
import com.qring.coupon.presentation.v1.req.PostUserCouponReqDTOV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usercoupons")
public class UserCouponControllerV1 implements UserCouponControllerSwagger {

    @PostMapping
    public ResponseEntity<ResDTO<UserCouponPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                                 @Valid @RequestBody PostUserCouponReqDTOV1 dto){
        /*
         * TODO :  더미데이터입니다.
         */
        CouponEntity dummyCouponEntity = CouponEntity.builder()
                .name("쿠폰1")
                .discount(1000)
                .totalQuantity(100)
                .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                .build();

        UserCouponEntity dummyUserCouponEntity = UserCouponEntity.builder()
                .couponEntity(dummyCouponEntity)
                .userId(userId)
                .build();

        return new ResponseEntity<>(
                ResDTO.<UserCouponPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 발급에 성공하였습니다.")
                        .data(UserCouponPostResDTOV1.of(dummyUserCouponEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

}

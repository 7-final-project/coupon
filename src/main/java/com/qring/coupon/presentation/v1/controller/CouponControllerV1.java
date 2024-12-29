package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.application.v1.res.ResDTO;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.infrastructure.docs.CouponControllerSwagger;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/coupons")
public class CouponControllerV1 implements CouponControllerSwagger {

    @PostMapping
    public ResponseEntity<ResDTO<CouponPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostCouponReqDTOV1 dto){

        /*
        * TODO :  더미데이터입니다.
        * */
        CouponEntity dummyCouponEntity = CouponEntity.builder()
                .id(1L)
                .name("쿠폰1")
                .totalQuantity(100)
                .remainQuantity(100)
                .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                .build();

        return new ResponseEntity<>(
                ResDTO.<CouponPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 생성에 성공하였습니다.")
                        .data(CouponPostResDTOV1.of(dummyCouponEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

}

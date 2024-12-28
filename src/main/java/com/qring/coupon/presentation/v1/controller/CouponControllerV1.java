package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.v1.res.CouponPostResDTOv1;
import com.qring.coupon.application.v1.res.ResDTO;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOv1;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/coupons")
public class CouponControllerV1 {

    @PostMapping
    public ResponseEntity<ResDTO<CouponPostResDTOv1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                             @Valid @RequestBody PostCouponReqDTOv1 dto){

        /*
        * TODO :  더미데이터입니다.
        * */
        CouponEntity dummyCouponEntity = CouponEntity.builder()
                .name("쿠폰1")
                .totalQuantity(100)
                .remainQuantity(100)
                .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                .build();

        return new ResponseEntity<>(
                ResDTO.<CouponPostResDTOv1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 생성에 성공하였습니다.")
                        .data(CouponPostResDTOv1.of(dummyCouponEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

}

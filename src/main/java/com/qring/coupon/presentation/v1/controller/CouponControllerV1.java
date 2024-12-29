package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.v1.res.*;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.infrastructure.docs.CouponControllerSwagger;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
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

    @GetMapping("/{couponId}")
    public ResponseEntity<ResDTO<CouponGetByIdResDTOV1>> getBy(@PathVariable Long couponId){

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
                ResDTO.<CouponGetByIdResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 단건 조회에 성공하였습니다.")
                        .data(CouponGetByIdResDTOV1.of(dummyCouponEntity))
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<ResDTO<CouponPutResDTOV1>> putBy(@RequestHeader("X-User-Id") Long userId,
                                                           @PathVariable Long couponId,
                                                           @Valid @RequestBody PutCouponReqDTOV1 dto){

        /*
         * TODO :  더미데이터입니다.
         * */
        CouponEntity dummyCouponEntity = CouponEntity.builder()
                .id(1L)
                .name("쿠폰2")
                .totalQuantity(100)
                .remainQuantity(100)
                .openAt(LocalDateTime.of(2025, 1, 1, 12, 0))
                .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                .build();

        return new ResponseEntity<>(
                ResDTO.<CouponPutResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 수정에 성공하였습니다.")
                        .data(CouponPutResDTOV1.of(dummyCouponEntity))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<ResDTO<CouponDeleteResDTOV1>> deleteBy(@RequestHeader("X-User-Id") Long userId,
                                                                 @PathVariable Long couponId){
        return new ResponseEntity<>(
                ResDTO.<CouponDeleteResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 삭제에 성공하였습니다.")
                        .data(CouponDeleteResDTOV1.of(1L))
                        .build(),
                HttpStatus.OK
        );
    }

}

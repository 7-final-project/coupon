package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.v1.res.CouponGetByIdResDTOV1;
import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.application.v1.res.CouponSearchResDTOV1;
import com.qring.coupon.application.v1.service.CouponServiceV1;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.infrastructure.docs.CouponControllerSwagger;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/coupons")
public class CouponControllerV1 implements CouponControllerSwagger {

    private final CouponServiceV1 couponServiceV1;

    @PostMapping
    public ResponseEntity<ResDTO<CouponPostResDTOV1>> postBy(@RequestHeader("X-Passport-Token") String token,
                                                             @Valid @RequestBody PostCouponReqDTOV1 dto){
        return new ResponseEntity<>(
                ResDTO.<CouponPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 생성에 성공하였습니다.")
                        .data(couponServiceV1.postBy(token, dto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/issue")
    public ResponseEntity<ResDTO<CouponPostByIdResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                                 @PathVariable Long id){
        /*
         * TODO :  더미데이터입니다.
         */
        CouponEntity dummyCouponEntity = CouponEntity.builder()
                .name("쿠폰1")
                .discount(1000)
                .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                .build();

        return new ResponseEntity<>(
                ResDTO.<CouponPostByIdResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 발급에 성공하였습니다.")
                        .data(CouponPostByIdResDTOV1.of(dummyCouponEntity))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<CouponSearchResDTOV1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(name = "id", required = false) Long couponId,
                                                                 @RequestParam(name = "name", required = false) String name,
                                                                 @RequestParam(name = "couponStatus", required = false) CouponStatus couponStatus,
                                                                 @RequestParam(name = "sort", required = false) String sort){
        /*
         * TODO :  더미데이터입니다.
         * */
        List<CouponEntity> dummyCouponList = List.of(
                CouponEntity.builder()
                        .name("쿠폰1")
                        .discount(1000)
                        .totalQuantity(100)
                        .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                        .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                        .build(),

                CouponEntity.builder()
                        .name("쿠폰2")
                        .discount(2000)
                        .totalQuantity(100)
                        .openAt(LocalDateTime.of(2024, 12, 31, 12, 0))
                        .expiredAt(LocalDateTime.of(2025, 1, 15, 12, 0))
                        .build()
        );

        Page<CouponEntity> dummyPage = new PageImpl<>(dummyCouponList, pageable, dummyCouponList.size());

        return new ResponseEntity<>(
                ResDTO.<CouponSearchResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 검색에 성공하였습니다.")
                        .data(CouponSearchResDTOV1.of(dummyPage))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<CouponGetByIdResDTOV1>> getBy(@PathVariable Long id){
        return new ResponseEntity<>(
                ResDTO.<CouponGetByIdResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 단건 조회에 성공하였습니다.")
                        .data(couponServiceV1.getBy(id))
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-Passport-Token") String token,
                                                @PathVariable Long id,
                                                @Valid @RequestBody PutCouponReqDTOV1 dto){

        couponServiceV1.putBy(token, id, dto);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-User-Id") Long userId,
                                                   @PathVariable Long id){
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}

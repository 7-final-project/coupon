package com.qring.coupon.presentation.v1.controller;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.v1.res.*;
import com.qring.coupon.application.v1.service.CouponServiceV1;
import com.qring.coupon.infrastructure.docs.CouponControllerSwagger;
import com.qring.coupon.infrastructure.lock.RedissonLockFacade;
import com.qring.coupon.infrastructure.util.PassportUtil;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/coupons")
public class CouponControllerV1 implements CouponControllerSwagger {

    private final CouponServiceV1 couponServiceV1;
    private final RedissonLockFacade redissonLockFacade;

    @PostMapping
    public ResponseEntity<ResDTO<CouponPostResDTOV1>> postBy(@RequestHeader("X-Passport-Token") String passport,
                                                             @Valid @RequestBody PostCouponReqDTOV1 dto) {
        return new ResponseEntity<>(
                ResDTO.<CouponPostResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 생성에 성공하였습니다.")
                        .data(couponServiceV1.postBy(passport, dto))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}/issue")
    public ResponseEntity<ResDTO<CouponPostByIdResDTOV1>> issueBy(@RequestHeader("X-Passport-Token") String passport,
                                                                  @PathVariable Long id) {
        Long userId = PassportUtil.getUserId(passport);
        String username = PassportUtil.getUsername(passport);
        return new ResponseEntity<>(
                ResDTO.<CouponPostByIdResDTOV1>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("쿠폰 발급에 성공하였습니다.")
                        .data(redissonLockFacade.issueCouponWithLockById(userId, id, username))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ResDTO<CouponSearchResDTOV1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(name = "userId", required = false) Long userId,
                                                                 @RequestParam(name = "name", required = false) String name,
                                                                 @RequestParam(name = "couponStatus", required = false) String couponStatus,
                                                                 @RequestParam(name = "issuanceStatus", required = false) String issuanceStatus,
                                                                 @RequestParam(name = "sort", required = false) String sort) {
        return new ResponseEntity<>(
                ResDTO.<CouponSearchResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 검색에 성공하였습니다.")
                        .data(couponServiceV1.searchBy(pageable, userId, name, couponStatus, issuanceStatus, sort))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/user-coupons")
    public ResponseEntity<ResDTO<CouponTableGetByUserIdResDTOV1>> getBy(@RequestHeader("X-Passport-Token") String passport) {
        return new ResponseEntity<>(
                ResDTO.<CouponTableGetByUserIdResDTOV1>builder()
                        .code(HttpStatus.OK.value())
                        .message("사용자 쿠폰 조회에 성공하였습니다.")
                        .data(couponServiceV1.getBy(passport))
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<CouponGetByIdResDTOV1>> getBy(@PathVariable Long id) {
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
    public ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-Passport-Token") String passport,
                                                @PathVariable Long id,
                                                @Valid @RequestBody PutCouponReqDTOV1 dto) {

        couponServiceV1.putBy(passport, id, dto);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 수정에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-Passport-Token") String passport,
                                                   @PathVariable Long id) {

        couponServiceV1.deleteBy(passport, id);

        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(HttpStatus.OK.value())
                        .message("쿠폰 삭제에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

}

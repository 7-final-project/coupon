package com.qring.coupon.infrastructure.docs;

import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.application.v1.res.ResDTO;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Coupon", description = "생성, 조회, 검색, 수정, 삭제 관련 쿠폰 API")
public interface CouponControllerSwagger {

    @Operation(summary = "쿠폰 생성", description = "사용자의 Id 를 기준으로 쿠폰을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "쿠폰 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 생성 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/v1/coupons")
    ResponseEntity<ResDTO<CouponPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody PostCouponReqDTOV1 dto);

}

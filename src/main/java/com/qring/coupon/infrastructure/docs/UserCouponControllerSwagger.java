package com.qring.coupon.infrastructure.docs;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.application.v1.res.UserCouponPostResDTOV1;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PostUserCouponReqDTOV1;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "UserCoupon", description = "발급 관련 유저쿠폰 API")
@RequestMapping("/v1/usercoupons")
public interface UserCouponControllerSwagger {

    @Operation(summary = "유저쿠폰 발급", description = "사용자의 Id 를 기준으로 유저쿠폰을 발급하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "유저쿠폰 발급 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "유저쿠폰 발급 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping
    ResponseEntity<ResDTO<UserCouponPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId,
                                                          @Valid @RequestBody PostUserCouponReqDTOV1 dto);

}

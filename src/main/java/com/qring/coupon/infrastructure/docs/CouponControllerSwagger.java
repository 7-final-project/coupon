package com.qring.coupon.infrastructure.docs;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.v1.res.*;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Coupon", description = "생성, 조회, 검색, 수정, 삭제 관련 쿠폰 API")
public interface CouponControllerSwagger {

    @Operation(summary = "쿠폰 생성", description = "사용자의 Id 를 기준으로 쿠폰을 생성하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "쿠폰 생성 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 생성 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PostMapping("/v1/coupons")
    ResponseEntity<ResDTO<CouponPostResDTOV1>> postBy(@RequestHeader("X-User-Id") Long userId, @Valid @RequestBody PostCouponReqDTOV1 dto);

    @Operation(summary = "쿠폰 검색", description = "쿠폰을 검색하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 검색 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 검색 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping("/v1/coupons")
    ResponseEntity<ResDTO<CouponSearchResDTOV1>> searchBy(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                          @RequestParam(name = "couponId", required = false) Long couponId,
                                                          @RequestParam(name = "name", required = false) String name,
                                                          @RequestParam(name = "sort", required = false) String sort);

    @Operation(summary = "쿠폰 단건 조회", description = "쿠폰 Id 를 기준으로 쿠폰을 단건 조회하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 단건 조회 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 단건 조회 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @GetMapping("/v1/coupons/{couponId}")
    ResponseEntity<ResDTO<CouponGetByIdResDTOV1>> getBy(@PathVariable Long couponId);

    @Operation(summary = "쿠폰 수정", description = "사용자의 Id 를 기준으로 쿠폰을 수정하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 수정 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 수정 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @PutMapping("/v1/coupons/{couponId}")
    ResponseEntity<ResDTO<Object>> putBy(@RequestHeader("X-User-Id") Long userId,
                                                    @PathVariable Long couponId,
                                                    @Valid @RequestBody PutCouponReqDTOV1 dto);

    @Operation(summary = "쿠폰 삭제", description = "사용자의 Id 를 기준으로 쿠폰을 삭제하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 삭제 성공", content = @Content(schema = @Schema(implementation = ResDTO.class))),
            @ApiResponse(responseCode = "400", description = "쿠폰 삭제 실패.", content = @Content(schema = @Schema(implementation = ResDTO.class)))
    })
    @DeleteMapping("/v1/coupons/{couponId}")
    ResponseEntity<ResDTO<Object>> deleteBy(@RequestHeader("X-User-Id") Long userId,
                                                       @PathVariable Long couponId);

}

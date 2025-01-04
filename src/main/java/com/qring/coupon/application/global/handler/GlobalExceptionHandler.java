package com.qring.coupon.application.global.handler;

import com.qring.coupon.application.global.dto.ResDTO;
import com.qring.coupon.application.global.exception.CouponException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CouponException.class})
    public ResponseEntity<ResDTO<Object>> authExceptionHandler(CouponException ex) {
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(ex.getErrorCode().getCode())
                        .message(ex.getMessage())
                        .build(),
                ex.getErrorCode().getHttpStatus()
        );
    }
}

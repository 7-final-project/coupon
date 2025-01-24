package com.qring.coupon.infrastructure.messaging.kafka;

import com.qring.coupon.application.service.CouponServiceV1;
import com.qring.coupon.infrastructure.messaging.kafka.dto.IssueCouponMessageDTOV1;
import com.qring.coupon.infrastructure.util.EventSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "KafkaMessageConsumerImplV1")
public class KafkaMessageConsumerImplV1 {

    private final CouponServiceV1 couponServiceV1;

    @KafkaListener(topics = "${spring.kafka.topic.issuance-coupon-event}", groupId = "${spring.kafka.consumer.group-id}")
    public void issueCouponEvent(String message) {
        log.info("=========메시지 리스너==========");
        couponServiceV1.saveUserCoupon(EventSerializer.deserialize(message, IssueCouponMessageDTOV1.class));
        log.info("메시지 내용 {}", message);
    }
}

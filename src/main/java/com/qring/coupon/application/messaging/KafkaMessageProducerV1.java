package com.qring.coupon.application.messaging;

import com.qring.coupon.infrastructure.messaging.kafka.dto.IssueCouponMessageDTOV1;

public interface KafkaMessageProducerV1 {

    void publishCouponIssuanceMessage(IssueCouponMessageDTOV1 message);

}

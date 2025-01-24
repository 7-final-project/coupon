package com.qring.coupon.infrastructure.messaging.kafka;

import com.qring.coupon.application.messaging.KafkaMessageProducerV1;
import com.qring.coupon.infrastructure.messaging.kafka.dto.IssueCouponMessageDTOV1;
import com.qring.coupon.infrastructure.util.EventSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducerImplV1 implements KafkaMessageProducerV1 {

    @Value("${spring.kafka.topic.issuance-coupon-event}")
    private String issuanceCouponEventTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishCouponIssuanceMessage(IssueCouponMessageDTOV1 message) {

        kafkaTemplate.send(issuanceCouponEventTopic, EventSerializer.serialize(message));

    }
}

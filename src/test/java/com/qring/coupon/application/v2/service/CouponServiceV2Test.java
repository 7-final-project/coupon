package com.qring.coupon.application.v2.service;

import com.qring.coupon.domain.repository.UserCouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@TestPropertySource(locations = "/application-dev.yml")
class CouponServiceV2Test {

    @Autowired
    private CouponServiceV2 couponServiceV2;

    @Autowired
    private UserCouponRepository userCouponRepository;

    List<Long> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        for (long i = 0; i < 1000; i++) {
            users.add(i);
        }
    }

    @Test
    @DisplayName("쿠폰 여러 명 발급")
    void 쿠폰_여러_명_발급() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNumber = i + 1;
            int key = i;
            executorService.submit(() -> {
                try {
                    couponServiceV2.issueBy(users.get(key), String.valueOf(key), 670107053425532958L);
                    System.out.println("Thread " + threadNumber + " - 성공");

                } catch (PessimisticLockingFailureException e) {
                    System.out.println("Thread " + threadNumber + " - 락 충돌 감지");

                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Long count = userCouponRepository.countByCouponEntityId(670107053425532958L);

        Assertions.assertThat(count).isEqualTo(100L);
    }
}
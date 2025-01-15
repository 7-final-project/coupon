package com.qring.coupon.application.v1.service;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.domain.repository.UserCouponRepository;
import com.qring.coupon.infrastructure.facade.AdvisoryLockFacade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TestPropertySource(locations = "/application-dev.yml")
@SpringBootTest
class CouponServiceV1Test {

    @Autowired
    private AdvisoryLockFacade advisoryLockFacade;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    List<Long> users = new ArrayList<>();
    private Long couponId;

    @BeforeEach
    void setUp() {
        for (long i = 0; i < 1000; i++) {
            users.add(i);
        }

        CouponEntity coupon = CouponEntity.createCouponEntity(
                "temp",
                2000,
                100,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10L),
                "tester"
        );

        CouponEntity save = couponRepository.save(coupon);
        couponId = save.getId();
    }

    @AfterEach
    void after() {
        userCouponRepository.deleteAll();
        couponRepository.deleteAll();
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
                    advisoryLockFacade.issueCouponWithLockById1(users.get(key), couponId, "유저");
                    System.out.println("Thread " + threadNumber + " - 성공");

                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());

                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        Long count = userCouponRepository.countByCouponEntityId(couponId);

        Assertions.assertThat(count).isEqualTo(100);
    }
}

package com.spring.boot;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.inmemory.request.InMemorySlidingWindowRequestRateLimiter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringboottestApplicationTests {
	
	private static Logger logger = LoggerFactory.getLogger(SpringboottestApplicationTests.class);
	
	RequestLimitRule limitRuleShotTerm = RequestLimitRule.of(Duration.ofSeconds(1), 15);
	RequestRateLimiter rateLimiterShotTerm = new InMemorySlidingWindowRequestRateLimiter(limitRuleShotTerm);
	
	RequestLimitRule limitRuleLongTerm = RequestLimitRule.of(Duration.ofMinutes(1), 100);
	RequestRateLimiter rateLimiterLongTerm = new InMemorySlidingWindowRequestRateLimiter(limitRuleLongTerm);

	@Test
	public void contextLoads() {
	}

//	@Test
	public void rateLimitJTest() throws Exception{
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				if (rateLimiterShotTerm.overLimitWhenIncremented("key")) {
					logger.warn("XX"); // 제한 초과로 실행하지 않음
				} else {
					logger.info("!! OK"); // 제한을 초과하지 않아 실행함
				}
			}
		}, 0, 100);

		Thread.sleep(1500);
	}
	
	@Test
	public void rateLimitLongTermTest() {
		for (int i = 0; i < 1000; i++) {
			try {
//				longTerm("Test", i);
				bucket(i);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	private void longTerm (String key, int i) throws Exception{
		logger.info("cnt === " + i);
		if (rateLimiterLongTerm.overLimitWhenIncremented(key)) {
			logger.warn(i + " : XX"); // 제한 초과로 실행하지 않음
			Thread.sleep(1000);
			longTerm(key, i);
		} else {
			logger.info(i + ": OK"); // 제한을 초과하지 않아 실행함
		}
	}
	
	private void bucket(int i) throws Exception{
		logger.info("cnt === " + i);
		// 1초에 5개 사용 제한
		Bandwidth limit = Bandwidth.simple(5, Duration.ofSeconds(1));
		// 버킷 생성
		Bucket bucket = Bucket4j.builder().addLimit(limit).build();

		if (bucket.tryConsume(1)) { // 1개 사용 요청
			logger.info(i + ": OK"); // 제한을 초과하지 않아 실행함
			
		} else {
			logger.warn(i + " : XX"); // 제한 초과로 실행하지 않음
			Thread.sleep(1000);
			bucket(i);
		}
	}

}

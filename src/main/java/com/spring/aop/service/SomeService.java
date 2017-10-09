package com.spring.aop.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.spring.aop.annotation.AuthChecker;

@Service
public class SomeService {
	// private static Logger logger = LoggerFactory.getLogger(SomeService.class);
	private static Random random = new Random(System.currentTimeMillis());

	@AuthChecker
	public static void someMethod() {
		System.out.println("---SomeService: someMethod invoked---");
		// logger.info("---SomeService: someMethod invoked---");
		try {
			// 模拟耗时任务
			Thread.sleep(random.nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("---SomeService: someMethod invoked---");
		someMethod();
	}
}
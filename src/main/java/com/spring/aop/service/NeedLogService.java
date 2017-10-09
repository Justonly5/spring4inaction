package com.spring.aop.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NeedLogService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Random random = new Random(System.currentTimeMillis());

	public int logMethod(String someParam) {
		logger.info("---NeedLogService: logMethod invoked, param: {}---", someParam);
		return random.nextInt();
	}

	public void exceptionMethod() throws Exception {
		logger.info("---NeedLogService: exceptionMethod invoked---");
		throw new Exception("Something bad happened!");
	}
}

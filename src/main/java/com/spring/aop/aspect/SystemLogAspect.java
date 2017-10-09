package com.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.spring.aop.service.SystemLogService;

@Aspect
@Component
@SuppressWarnings("unused")
public class SystemLogAspect {

	@Autowired
	private SystemLogService systemLogService;

	private static Logger log = LoggerFactory.getLogger(SystemLogAspect.class);

	@Pointcut("execution( * com.spring.aop.controller..*.*(..))")
	public void controllerAspect() {

	}

	@Before("controllerAspect()")
	public void doBefore(ProceedingJoinPoint joinpoint) {
		System.out.println("========== 开始执行 controller 前置通知 ===============");

	}

	@Around("controllerAspect()")
	public void around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println(">>>>>>>>>>>> 开始执行 Controller 环绕通知 <<<<<<<<<<<<<");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		pjp.proceed();
		stopWatch.stop();
		long totalTimeMillis = stopWatch.getTotalTimeMillis();
		System.out.println(">>>>>>>>>>>> 耗时:" + totalTimeMillis + " 毫秒. <<<<<<<<<<<<");
		System.out.println(">>>>>>>>>>>> 开始执行 Controller 环绕通知 <<<<<<<<<<<<<");
	}

	@After("controllerAspect()")
	public void after(ProceedingJoinPoint joinPoint) {
		System.out.println("========== 开始执行 Controller 后置通知 ===============");

	}
}

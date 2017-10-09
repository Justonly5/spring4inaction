package com.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAopAdviseDefine {
	private Logger logger = LoggerFactory.getLogger(getClass());

	// 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
	// 以 within 切点标志符来匹配类 NeedLogService 下的所有 joinpoint
	@Pointcut("within(NeedLogService)")
	public void pointcut() {
	}

	// 定义 advise
	@Before("pointcut()")
	public void logMethodInvokeParam(JoinPoint joinPoint) {
		logger.info("---Before method {} invoke, param: {}---", joinPoint.getSignature().toShortString(),
				joinPoint.getArgs());
	}

	@AfterReturning(pointcut = "pointcut()", returning = "retVal")
	public void logMethodInvokeResult(JoinPoint joinPoint, Object retVal) {
		logger.info("---After method {} invoke, result: {}---", joinPoint.getSignature().toShortString(),
				joinPoint.getArgs());
	}

	@AfterThrowing(pointcut = "pointcut()", throwing = "exception")
	public void logMethodInvokeException(JoinPoint joinPoint, Exception exception) {
		logger.info("---method {} invoke exception: {}---", joinPoint.getSignature().toShortString(),
				exception.getMessage());
	}
}
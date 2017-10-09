package com.spring.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ExpiredAopAdviseDefine {
	// private Logger logger = LoggerFactory.getLogger(getClass());

	// 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
	@Pointcut("@annotation(com.spring.aop.annotation.AuthChecker)")
	public void pointcut() {
	}

	// 定义 advise
	@Around("pointcut()")
	public Object methodInvokeExpiredTime(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// 开始
		Object retVal = pjp.proceed();
		stopWatch.stop();
		// 结束

		// 上报到公司监控平台
		reportToMonitorSystem(pjp.getSignature().toShortString(), stopWatch.getTotalTimeMillis());

		return retVal;
	}

	public void reportToMonitorSystem(String methodName, long expiredTime) {
		System.out.println("---method " + methodName + " invoked, expired time: " + expiredTime + " ms---");
		// logger.info("---method {} invoked, expired time: {} ms---", methodName,
		// expiredTime);
		//
	}
}
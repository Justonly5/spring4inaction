package com.spring.aop.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class HttpAopAdviseDefine {

	// 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
	// 以 @annotation 切点标志符来匹配有注解 AuthChecker 所标注的 joinpoint
	@Pointcut("@annotation(com.spring.aop.annotation.AuthChecker)")
	public void pointcut() {
	}

	// 定义 advise
	@Around("pointcut()")
	public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("------------ checkAuth -------------");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		// 检查用户所传递的 token 是否合法
		String token = getUserToken(request);
		if (!token.equalsIgnoreCase("123456")) {
			return "Error!Auth Is Ilegal!";
		}

		return joinPoint.proceed();
	}

	private String getUserToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equalsIgnoreCase("user_token")) {
				return cookie.getValue();
			}
		}
		return "";
	}
}
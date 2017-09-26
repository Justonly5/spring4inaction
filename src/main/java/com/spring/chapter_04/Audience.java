package com.spring.chapter_04;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Audience {

	/*
	 * @Pointcut注解能够在一个@AspectJ切面内定义可重用的切点
	 * performance()方法的实际内容并不重要，在这里它实际上应该是空的。其实该方法本身 只是一个标识，供@Pointcut注解依附
	 */
	@Pointcut("execution(** com.spring.chapter_04.Performance.performance(..))")
	public void performance() {

	}

	@Before("performance()")
	public void silenceCellPhones() {
		System.out.println("Silencing Cell Phones");
	}

	@Before("execution(** com.spring.chapter_04.Performance.performance(..))")
	public void takeSeats() {
		System.out.println("Taking Seats");
	}

	@AfterReturning("execution(** com.spring.chapter_04.Performance.performance(..))")
	public void applause() {
		System.out.println("CLAP CLAP CLAP!!!");
	}

	@AfterThrowing("execution(** com.spring.chapter_04.Performance.performance(..))")
	public void demandRefund() {
		System.out.println("Demanding a refund");
	}

	@Around("performance()")
	public void watchPerformance(ProceedingJoinPoint pjp) {
		try {
			System.out.println("Silencing Cell Phones From Around");
			System.out.println("Taking Seats From Around");
			pjp.proceed();
			System.out.println("CLAP CLAP CLAP!!! From Around");
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("Demanding a refund From Around");
		}
	}
}

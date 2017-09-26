package com.spring.chapter_04;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Pointcut;

public class TrackCounter {

	@SuppressWarnings("unused")
	private Map<Integer, Integer> trackCounts = new HashMap<>();

	@Pointcut("execution(* com.spring.chapter_04)")
	public void trackPlayed(int trackNumber) {

	}
}

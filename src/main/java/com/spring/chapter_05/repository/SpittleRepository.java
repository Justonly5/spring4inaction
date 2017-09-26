package com.spring.chapter_05.repository;

import java.util.List;

import com.spring.chapter_05.entity.Spittle;

public interface SpittleRepository {
	List<Spittle> findSpittles(Long max, int count);
}

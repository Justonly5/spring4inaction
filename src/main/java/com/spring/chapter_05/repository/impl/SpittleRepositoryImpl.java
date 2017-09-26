package com.spring.chapter_05.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.chapter_05.entity.Spittle;
import com.spring.chapter_05.repository.SpittleRepository;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

	@Override
	public List<Spittle> findSpittles(Long max, int count) {
		return null;
	}

}

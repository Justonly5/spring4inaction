package com.spring.chapter_05.repository.impl;

import org.springframework.stereotype.Repository;

import com.spring.chapter_05.entity.Spitter;
import com.spring.chapter_05.repository.SpitterRepository;

@Repository
public class SpitterRepositoryImpl implements SpitterRepository {

	@Override
	public Spitter save(Spitter spitter) {
		return null;
	}

	@Override
	public Spitter findByUsername(String username) {
		return null;
	}

}

package com.spring.chapter_05.repository;

import com.spring.chapter_05.entity.Spitter;

public interface SpitterRepository {
	Spitter save(Spitter spitter);

	Spitter findByUsername(String username);
}

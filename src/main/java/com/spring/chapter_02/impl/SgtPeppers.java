package com.spring.chapter_02.impl;

import org.springframework.stereotype.Component;

import com.spring.chapter_02.CompactDisc;

@Component
public class SgtPeppers implements CompactDisc {

	private String title = "Sgt. Pepper's Lonely Hearts Club Band";
	private String artist = "The Beatles";

	@Override
	public void play() {
		System.out.println("Playing: " + title + " by " + artist);
	}

}

package com.spring.chapter_02.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.chapter_02.CompactDisc;
import com.spring.chapter_02.MediaPlayer;

public class CDPlayer implements MediaPlayer {

	private CompactDisc cd;

	@Autowired
	public CDPlayer(CompactDisc cd) {
		this.cd = cd;
	}

	@Override
	public void play() {
		cd.play();
	}

}

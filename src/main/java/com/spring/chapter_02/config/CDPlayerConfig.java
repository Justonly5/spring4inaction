package com.spring.chapter_02.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.spring.chapter_02.CompactDisc;
import com.spring.chapter_02.impl.CDPlayer;
import com.spring.chapter_02.impl.SgtPeppers;

/*
 * @ComponentScan:设置组件扫描的基础包
 * value 属性用于指定扫描的包. 
 * basePackages={"package1","package2"}
 * basePackageClasses = {Class1.class,Class2.class}
 *
 */
@Configuration
@ComponentScan("com.spring.chapter_02.impl")
public class CDPlayerConfig {

	// @Bean
	public CompactDisc sgtPappers() {
		return new SgtPeppers();
	}

	/****** 借助JavaConfig实现注入 ******/
	// @Bean
	public CDPlayer cdPlayer() {
		return new CDPlayer(sgtPappers());
	}

	// @Bean
	public CDPlayer cdPlayer(CompactDisc cd) {
		return new CDPlayer(cd);
	}

}

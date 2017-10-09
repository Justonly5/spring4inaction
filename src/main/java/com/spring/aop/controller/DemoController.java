package com.spring.aop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.aop.annotation.AuthChecker;

@RestController
public class DemoController {
	@RequestMapping("/aop/http/alive")
	public String alive() {
		return "Server Everything Is OK!";
	}

	@AuthChecker
	@RequestMapping("/aop/http/user_info")
	public String callSomeInterface() {
		return "调用了 user_info 接口.";
	}
}
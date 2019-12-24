package com.yonghwa.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController2 {
	@GetMapping("/test2")
	public String test2() {
		return "hello";
	}
}

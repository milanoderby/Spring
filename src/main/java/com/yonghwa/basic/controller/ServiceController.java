package com.yonghwa.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceController {
	@GetMapping("kakao")
	public String serviceKakao() {
		return "kakao";
	}
	
	@GetMapping("papago")
	public String servicePapago() {
		return "papago";
	}
}
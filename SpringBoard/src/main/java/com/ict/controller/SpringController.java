package com.ict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/spring/")
public class SpringController {
	
	@RequestMapping("")
	public void basic() {
		System.out.println("�⺻ url �ּ��Դϴ�.");
	}
	
	@RequestMapping(value="/base",
			method = {RequestMethod.GET, RequestMethod.POST})
	public void baseGet() {
		System.out.println("base get");
	}
	
	@PostMapping("/basePost")
	public void baseOnlyPost() {
		System.out.println("base only post");
	}

}

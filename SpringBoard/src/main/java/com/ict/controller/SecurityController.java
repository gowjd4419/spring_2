package com.ict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/join")
public class SecurityController {
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@RequestMapping("/join")
	public {
		
	}

	
	return "/board/list";
}

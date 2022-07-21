package com.ict.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ChatController {
	
	@GetMapping("/chatting")
	public String chat(String userId, String room_id, HttpServletRequest request, HttpServletResponse reponse,Model model, HttpSession session)throws Exception {
		
		// 세션이 비었을 땐 로그인 페이지로
		if(userId == null) {
			return "/customLogin";
		}
		
		model.addAttribute("userId",userId);
		
		model.addAttribute("room_id", userId);
		
		model.addAttribute("room_id", room_id);
		
		return "/chat";
	}

}

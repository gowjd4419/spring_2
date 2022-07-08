package com.ict.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ict.domain.AuthVO;
import com.ict.domain.MemberVO;
import com.ict.service.SecurityService;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/secu/*")
@Controller
public class SecurityController {
	
	@Autowired
	private SecurityService service;
	
	@Autowired
	private PasswordEncoder pwen;
	

	@GetMapping("/all")
	public void doAll() {
		log.info("모든 사람이 접속 가능한 all 로직");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/member")
	public void doMember() {
		log.info("회원들이 접속 가능한 member 로직");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public void doAdmin(Principal principal) {//Principal 타입을 파라미터에 선언하면 컨트롤러에서 제어가능
		log.info("운영자 접속 : " + principal.getName());// 로그인한 사용자 아이디를 getName()으로 가져올수있음
		log.info("운영자만 접속 가능한 admin 로직");
	}
	
	@PreAuthorize("permitAll")
	@GetMapping("/join")
	public void joinForm() {
		log.info("회원가입창 접속");
	}

	@PreAuthorize("permitAll")
	@PostMapping("/join")
	public void join(MemberVO vo, String[] role)  {
		String beforeCrPw = vo.getUserpw();
		log.info("암호화 전 : " + beforeCrPw);
		vo.setUserpw(pwen.encode(beforeCrPw));
		log.info("암호화 후 : " + vo.getUserpw());

		// null 상태인 authList에 비어있는 ArrayList부터 배정
		vo.setAuthList(new ArrayList<AuthVO>());
		
		// 향상된 for문을 이용해 role 변수에 든 권한을 하나하나 순차적으로 뽑아서
		// ArrayList의 .add(자료) 기능을 이용해 넣어줍니다.
		for(String roleItem : role) {
			AuthVO authVO = new AuthVO();
			authVO.setAuth(roleItem);
			authVO.setUserid(vo.getUserid());
			// 체크가 되었다면 vo내부의 권한목록에 집어넣기
			vo.getAuthList().add(authVO);
		}
		// vo에 데이터가 전부 들어온것을 확인했으므로 회원가입 로직 실행되도록 처리
		service.insertMember(vo);
	}
	
}

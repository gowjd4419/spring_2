package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ict.persistence.ReplyVO;
import com.ict.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	@Autowired
	private ReplyService service;

	
	// consumes는 이 메서드의 파라미터를 넘겨줄 때 어떤 형식으로 넘겨줄지
	// 를 설정하는데, 기본적으로 rest방식에서는 json을 사용한다.
	// produces는 입력받은 데이터를 토대로 로직을 실행한 다음
	// 사용자에게 결과로 보여줄 데이터의 형식을 나타낸다.
	// jason-databind를 추가해야 정상적으로 작동한다.
	@PostMapping(value="", consumes="application/json",
			                produces= {MediaType.TEXT_PLAIN_VALUE})
	// produces에 TEXT_PLAIN_VALUE를 줬으므로, 결과코드와 문자열을 넘기도록 한다.
	public ResponseEntity<String> register(
			                            // rest컨트롤러에서 받는 파라미터 앞에
			                            // @RequestBody 어노테이션이 붙어야
			                            // consumes와 연결된다.
			                            @RequestBody ReplyVO vo){
		
		// 깡통 Entity를 먼저 생성
		ResponseEntity<String> entity = null;
		
		try {
			// 먼저 글쓰기 로직 실행 후 에러가 없다면...
			service.addReply(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			// catch로 넘어왔다는것은 글쓰기에 문제가 생겼다는 뜻이므로
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 위의 try블럭이나 catch블럭에서 생성된 entity를 리턴
		return entity;
		
	}
	
	@GetMapping(value="/all/{bno}",
			// 단일 숫자데이터 bno만 넣기 때문에,
			// 그리고 PathVariable 어노테이션으로 인해 이미 입력데이터가
			// 명시되었으므로 consumes는 따로 주지 않아도 된다.
			// produces는 댓글 목록이 XML로도, JSON으로도 표현될 수 있도록
			// 아래와 같이 2개를 모두 얹는다.
			// jackson-dataformat-xml을 추가해야 xml도 처리가능하다.
			produces = {MediaType.APPLICATION_XML_VALUE,
			   MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ReplyVO>> list(
			@PathVariable("bno")Long bno){
		// 깡통 Entity를 먼저 생성
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			entity = new ResponseEntity<>(
					service.listReply(bno), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	// 일반 방식이 아닌 rest방식에서는 삭제로직을 post가 아닌
	// delete 방식으로 요청하기 때문에 @DeleteMapping을 쓴다.
	@DeleteMapping(value="/{rno}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(
			@PathVariable("rno")Long rno){
		
		ResponseEntity<String> entity = null;
	
	try {
		service.removeReply(rno);
		entity = new ResponseEntity<String>(
				"SUCCESS", HttpStatus.OK);
	}catch(Exception e) {
		entity = new ResponseEntity<String>(
				e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	return entity;
   }
	
	@RequestMapping(method= {RequestMethod.PUT, RequestMethod.PATCH},
			         value="/{rno}",
			         consumes="application/json",
			         produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(
			// VO는 우선 payload에 적힌 데이터로 받아온다.
			// @RequestBody가 붙은 VO는
			// payload에 적힌 데이터를 VO로 환산해서 가져온다.
			@RequestBody ReplyVO vo,
			// 단, 댓글번호는 주소에 기입된 숫자를 자원으로 받아온다.
			@PathVariable("rno")Long rno){
		
		ResponseEntity<String> entity = null;
		
		try {
			vo.setRno(rno);
			service.modifyReply(vo);
			
			entity = new ResponseEntity<String>(
					"SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(
					e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}


package com.ict.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.persistence.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	
	// 이 테스트코드 내에서는 boardMapper의 테스트를 담당한다.
	// 그래서 먼저 선언하고 의존성 주입까지 마쳐야 해당 기능을 이 클래스 내에서 쓸 수있다.
	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		log.info(mapper.getList());
	}
	
	//@Test
	public void testInsert() {
		// 글 입력을 위해서 BoardVO 타입을 매개로 사용함
		// 따라서 BoardVO를 만들어놓고
		// setter로 글제목, 글본문, 글쓴이 만 저장해둔 채로
		// mapper.insert(vo);를 호출해서 실행여부를 확인하면 됨.
		// 위 설명을 토대로 아래 vo에 6번글에 대한 제목 본문 글쓴이를 넣고
		// 호출해주신 다음 실제로 DB에 글이 들어갔는지 확인해주세요.
		BoardVO vo = new BoardVO();
		
		// 입력할 글에 대한 제목, 글쓴이, 본문을 vo에 넣어줍니다.
		vo.setTitle("새로넣는글");
		vo.setContent("새로넣는본문");
		vo.setWriter("새로넣는글쓴이");
		
		// log.info(vo);
		mapper.insert(vo);
		
		
	}
	
	// 삭제 테스트코드를 작성해주기
	// Long bno 파라미터에 값을 전달할때는 정수 L과 같이
	// 뒤에 L을 붙여야 함
	//@Test
	public void testdelete() {
		mapper.delete(5L);
		
	}
	//@Test
	public void testupdate() {
		BoardVO vo = new BoardVO();
		
		vo.setBno(1L);
		vo.setTitle("고친제목");
		vo.setContent("고친 내용");
		vo.setWriter("고친 이름");
		
		mapper.update(vo);
	}
	

}

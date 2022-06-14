package com.ict.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.mapper.BoardMapper;
import com.ict.mapper.BoardMapperTests;
import com.ict.persistence.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	
	@Autowired
	private BoardService service;
	
	//@Test
	public void testgetList() {
		log.info(service.getList());
		
	}
	
	// insert 테스트하기
	
	//@Test
		public void testInsert() {
			BoardVO vo = new BoardVO();

			vo.setTitle("새로넣는글");
			vo.setContent("새로넣는본문");
			vo.setWriter("새로넣는글쓴이");
			
			service.insert(vo);
			
		}
		
		//@Test
		public void testdelete() {
			service.delete(8L);
			
		}
		
		//@Test
		public void testupdate() {
			BoardVO vo = new BoardVO();
			
			vo.setBno(10L);
			vo.setTitle("수정된제목");
			vo.setContent("수정된본문");
			vo.setWriter("수정된글쓴이");
			
			service.update(vo);
		}
		
		@Test
		public void getDetail() {
			service.getDetail(7L);
		}

}

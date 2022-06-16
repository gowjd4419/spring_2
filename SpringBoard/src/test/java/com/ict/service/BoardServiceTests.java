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
		log.info(service.getList(null));
		
	}
	
	// insert �׽�Ʈ�ϱ�
	
	//@Test
		public void testInsert() {
			BoardVO vo = new BoardVO();

			vo.setTitle("���γִ±�");
			vo.setContent("���γִº���");
			vo.setWriter("���γִ±۾���");
			
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
			vo.setTitle("����������");
			vo.setContent("�����Ⱥ���");
			vo.setWriter("�����ȱ۾���");
			
			service.update(vo);
		}
		
		@Test
		public void getDetail() {
			service.getDetail(7L);
		}

}

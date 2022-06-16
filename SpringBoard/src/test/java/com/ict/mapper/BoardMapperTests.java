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
	
	// �� �׽�Ʈ�ڵ� �������� boardMapper�� �׽�Ʈ�� ����Ѵ�.
	// �׷��� ���� �����ϰ� ������ ���Ա��� ���ľ� �ش� ����� �� Ŭ���� ������ �� ���ִ�.
	@Autowired
	private BoardMapper mapper;
	
	//@Test
	public void testGetList() {
		log.info(mapper.getList(null));
	}
	
	//@Test
	public void testInsert() {
		// �� �Է��� ���ؼ� BoardVO Ÿ���� �Ű��� �����
		// ���� BoardVO�� ��������
		// setter�� ������, �ۺ���, �۾��� �� �����ص� ä��
		// mapper.insert(vo);�� ȣ���ؼ� ���࿩�θ� Ȯ���ϸ� ��.
		// �� ������ ���� �Ʒ� vo�� 6���ۿ� ���� ���� ���� �۾��̸� �ְ�
		// ȣ�����ֽ� ���� ������ DB�� ���� ������ Ȯ�����ּ���.
		BoardVO vo = new BoardVO();
		
		// �Է��� �ۿ� ���� ����, �۾���, ������ vo�� �־��ݴϴ�.
		vo.setTitle("���γִ±�");
		vo.setContent("���γִº���");
		vo.setWriter("���γִ±۾���");
		
		// log.info(vo);
		mapper.insert(vo);
		
		
	}
	
	// ���� �׽�Ʈ�ڵ带 �ۼ����ֱ�
	// Long bno �Ķ���Ϳ� ���� �����Ҷ��� ���� L�� ����
	// �ڿ� L�� �ٿ��� ��
	//@Test
	public void testdelete() {
		mapper.delete(5L);
		
	}
	//@Test
	public void testupdate() {
		BoardVO vo = new BoardVO();
		
		vo.setBno(1L);
		vo.setTitle("��ģ����");
		vo.setContent("��ģ ����");
		vo.setWriter("��ģ �̸�");
		
		mapper.update(vo);
	}
	
	//@Test
	public void getDetailTest() {
		mapper.getDetail(1L);

	}
	

}

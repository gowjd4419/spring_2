package com.ict.service;

import java.util.List;

import com.ict.persistence.BoardAttachVO;
import com.ict.persistence.BoardVO;
import com.ict.persistence.Criteria;
import com.ict.persistence.PageMaker;
import com.ict.persistence.SearchCriteria;

public interface BoardService {

	// Service�� ���� �ϳ��� ����(����� ����)�� �����ϰ�
	// Mapper�� �ϳ��� ȣ��(������)�� �����ϴ� �뵵�̴�.
	// �׷��� �⺻���� ������ �ϳ��� ������ �ϳ��� �������̹Ƿ�
	// ����� �׳� �������� �ϳ��� �޼��带 ������ָ� �ȴ�.
	// ��, ���߿� ����ڿ��Դ� �ۻ��� ������, ����������� �۰� ����� ��� �����ȴٴ��� �ϴ� ������
	// ����� ������ �ϳ��� ���۰� ���������� �ϳ��� ������ ��ġ���� �������� ������ �����ؾ��Ѵ�.
	public List<BoardVO> getList(SearchCriteria cri);
	
	public void insert(BoardVO vo);
	
	public void delete(Long bno);
	
	public void update(BoardVO vo);
	
	public BoardVO getDetail(Long bno);
	
	public Long getBoardCount(SearchCriteria cri);
	
	// 특정 글 번호 입력시 글번호에 연동된 첨부파일 목록 가져오기
	public List<BoardAttachVO> getAttachList(Long bno);
	
}




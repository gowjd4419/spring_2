package com.ict.mapper;

import java.sql.Date;
import java.util.List;

import com.ict.persistence.BoardVO;

public interface BoardMapper {
	
	public List<BoardVO> getList();
	
	public void insert(BoardVO vo);
	
	// delete를 만들기
	// 글번호 (Long타입)을 입력받아 해당 글번호를 삭제해준다.
	public void delete(Long bno);
	
	// update
	public void update(BoardVO vo);
	

}

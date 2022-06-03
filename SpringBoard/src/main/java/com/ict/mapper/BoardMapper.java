package com.ict.mapper;

import java.sql.Date;
import java.util.List;

import com.ict.persistence.BoardVO;

public interface BoardMapper {
	
	public List<BoardVO> getList();
	
	public void insert(BoardVO vo);
	
	// delete�� �����
	// �۹�ȣ (LongŸ��)�� �Է¹޾� �ش� �۹�ȣ�� �������ش�.
	public void delete(Long bno);
	
	// update
	public void update(BoardVO vo);
	

}

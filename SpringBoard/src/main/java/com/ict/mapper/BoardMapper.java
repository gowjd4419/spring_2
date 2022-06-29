package com.ict.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ict.persistence.BoardVO;
import com.ict.persistence.Criteria;
import com.ict.persistence.PageMaker;
import com.ict.persistence.SearchCriteria;

public interface BoardMapper {
	
	public List<BoardVO> getList(SearchCriteria cri);
	
	public void insert(BoardVO vo);
	
	// delete�� �����
	// �۹�ȣ (LongŸ��)�� �Է¹޾� �ش� �۹�ȣ�� �������ش�.
	public void delete(Long bno);
	
	// update
	public void update(BoardVO vo);
	
	// detail
	public BoardVO getDetail(Long bno);
	
	public Long getBoardCount(SearchCriteria cri);
	
	// 댓글이 써질때마다 board_tbl의 댓글개수를 올려주는 메서드
	public void updateReplyCount(@Param("bno") Long bno,
			                     @Param("amount") int amount);

}

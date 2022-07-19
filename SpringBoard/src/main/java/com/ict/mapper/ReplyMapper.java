package com.ict.mapper;

import java.util.List;

import com.ict.persistence.ReplyVO;

public interface ReplyMapper {
	
	// 게시판 bno번 글의 전체 댓글 목록 가져오기
	public List<ReplyVO> getList(Long bno);
	
	public void create(ReplyVO vo);
	
	public void update(ReplyVO vo);
	
	public void delete(Long rno);
	
	public Long getBno(Long rno);
	
	public void deleteAll(Long bno);

}

package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.mapper.BoardMapper;
import com.ict.mapper.ReplyMapper;
import com.ict.persistence.ReplyVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService{

	
	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public void addReply(ReplyVO vo) {
		Long bno = vo.getBno();
		mapper.create(vo);
		// vo내부에 있는 글번호를 이용하기
		boardMapper.updateReplyCount(bno, 1);
		
	}

	@Override
	public List<ReplyVO> listReply(Long bno) {
		
		return mapper.getList(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) {
		mapper.update(vo);
		
	}

	@Override
	public void removeReply(Long rno) {
		// 댓글 카운팅을 하기 위해서는 해당 댓글이 달려있던 bno에 대한 정보가 필요하다.
		// 댓글이 삭제된 다음 bno를 얻어올 수는 없기때문에 제일 먼저 bno부터 얻어온다
		Long bno = mapper.getBno(rno);
		log.info("삭제할 글 번호 : " + bno);
		// 댓글 삭제
		mapper.delete(rno);
		// 댓글 삭제 후에 updateReplyCount를 실행해 해당 bno번 글 정보의 댓글개수를 1개 차감
		// 테스트삼아 댓글 삭제를 SQLDeveloper에서 했을 경우 , commit을 반드시 하고
		// 해당 로직을 테스트해야 정상적으로 서버가 처리된다. commit을 안하면
		// pending 상태가 유지된다.
		boardMapper.updateReplyCount(bno, -1);
	}
	
	

	
	

}

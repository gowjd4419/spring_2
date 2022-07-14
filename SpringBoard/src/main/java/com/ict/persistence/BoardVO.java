package com.ict.persistence;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regDate;
	private Date updateDate;
	private Long replycount;
	
	
	// 첨부파일이 여러개일 수 있으므로 List로 저장
	private List<BoardAttachVO> attachList;

}

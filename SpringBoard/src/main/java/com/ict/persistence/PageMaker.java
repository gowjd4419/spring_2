package com.ict.persistence;

import lombok.Data;

@Data
public class PageMaker {
		
		private Long totalBoard;
		private int startPage;
		private int endPage;
		private boolean prev;
		private boolean next;
		private int displayPageNum;
		// 현재 조회중인 페이지 정보를 획득하기 위해 선언
		private Criteria cri;
		
		public void calcData() {
			this.displayPageNum = 10;
			
			// 끝나는 페이지는 소속 번호를 실수 10으로 나눈다음 다시 올림처리후 10을 곱해 구할 수 있다.
			this.endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
			// 시작페이지는 끝나는페이지 - 페이지개수+1을 하면 된다.
			this.startPage = (endPage - displayPageNum) + 1;
			
			int tempEndPage = (int)(Math.ceil(totalBoard / (double)cri.getNumber()));
			
			if(endPage > tempEndPage) {
				endPage = tempEndPage;
			}
			
			this.prev = this.startPage == 1 ? false : true;
			this.next = endPage * cri.getNumber() >= totalBoard ? false : true;

      }
		
		public void setTotalBoard(Long totalBoard) {
			this.totalBoard = totalBoard;
			calcData();
		}
}

package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ict.persistence.BoardVO;
import com.ict.persistence.Criteria;
import com.ict.persistence.PageMaker;
import com.ict.persistence.SearchCriteria;
import com.ict.service.BoardService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
// bean container�� �ֱ�
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// PreAuthorize를 붙이면 로그인한 사람만 들어갈수있음 아래에 ROLE_ADMIN 권한이 주어진 사람이 로그인햇을경우에만 접근 가능
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	
	//@PreAuthorize("hasAnyRole('ROLE_ALL')")

	// /board/list �ּҷ� �Խù� ��ü�� ����� ǥ���ϴ� ��Ʈ�ѷ��� ������ֱ�
	// list.jsp�� ����Ǹ� �ǰ�, getList()�޼���� ������ ��ü �� �����
	// 포워딩해서 화면에 뿌려주면, 글번호, 글제목, 글쓴이, 날짜, 수정날짜를 화면에 출력해준다.
	
	@RequestMapping("/list")
	                          // @RequestParam의 defaultValue를 통해 값이 안들어올때 자동으로 배정할 값을 정할수있음
	public String getBoardList(SearchCriteria cri,Model model) {
		if(cri.getPage()== 0) {
			cri.setPage(1);
		}
		// �� ��ü ��� ��������
		List<BoardVO> boardList = service.getList(cri);
		log.info(boardList);
		// ���ε�
		model.addAttribute("boardList",boardList);

		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalBoard(service.getBoardCount(cri));
		model.addAttribute("pageMaker",pageMaker);
		// PageMaker 생성 및 cri 주입,
		
		
		// ���� ������ ��� ���ϴ� ���Ϸ� ������ ������
		return "/board/list";
	}
	
	// �� ��ȣ�� �Է¹޾Ƽ� (�ּ�â���� ?bno=��ȣ ��������)�ش� ���� ������ �������� �����ִ�
	// ������ �ϼ���Ű��
	// board/detail.jsp�̴�.
	// getBoardListó�� �������ؼ� ȭ�鿡 �ش� �� �ϳ��� ���� ������ �����ָ� ��
	// mapper�ʿ� ���� bno�� �̿��� Ư�� �� �ϳ��� VO�� ������ ������ �����
	// ���������� �����ϱ�
	
	@GetMapping("/detail")
	public String getDetail(Long bno,Model model) {
		BoardVO board = service.getDetail(bno);
		model.addAttribute("board", board);
		return"/board/detail";
	}
	
	// �۾���� �� �״�� ���� ���ִ� �����ε�
	// ������ ����Ǵ� �������� �ϳ� �־���ϰ�
	// �״��� ������ �����ִ� ������ ó�����ִ� �������� �ϳ� �� �־�� �Ѵ�.
	// /board/insert �� get������� ���ӽ�
	// boardForm.jsp�� ����ǵ��� ������ֱ�

	
	@GetMapping("/insert")
	public String insertForm() {
		return "/board/insertForm";
	}
	
	// post������� /insert�� ������ �ڷḦ �޾� �ֿܼ� ����ֱ�
	@PostMapping("/insert")
	public String insertBoard(BoardVO board) {
		log.info(board);
		service.insert(board);
		// redirect를 사용해야 전체 글 목록을 로딩해온 다음 화면을 열어준다.
		// 스프링 컨트롤러에서 리다이렉트를 할 때는
		// 목적주소 앞에 redirect:을 추가로 붙이기
		return "redirect:/board/list";
	}
	
	// 글삭제도 post방식으로 처리하도록 한다.
	@PostMapping("/delete")
	public String deleteBoard(Long bno,SearchCriteria cri,RedirectAttributes rttr) {
		// 삭제후 리스트로 돌아갈 수 있도록 내부 로직을 만들어주고
		service.delete(bno);
		
		 rttr.addAttribute("page",cri.getPage());
		 rttr.addAttribute("searchType",cri.getSearchType());
		 rttr.addAttribute("keyword",cri.getKeyword());
		// 디테일 페이지에 삭제 요청을 넣을 수 있는 폼을 만들어주기
		
	   return "redirect:/board/list";
	}
	
	@PostMapping("/updateForm")
	public String updateBoardForm(Long bno, Model model) {
		BoardVO board = service.getDetail(bno);
		model.addAttribute("board",board);
		return "/board/updateForm";
	}
	
	@PostMapping("/update")
		public String updateBoard(BoardVO board, SearchCriteria cri, RedirectAttributes rttr) {
		 service.update(board);
		 
		 // rttr.addAttribute()로 url뒤에 파라미터 정보를 붙여준다.
		 rttr.addAttribute("bno",board.getBno());
		 rttr.addAttribute("page",cri.getPage());
		 rttr.addAttribute("searchType",cri.getSearchType());
		 rttr.addAttribute("keyword",cri.getKeyword());
		 
		return "redirect:/board/detail?bno=" + board.getBno();
	 }
	
	
}

package com.ict.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ict.persistence.AttachFileDTO;
import com.ict.persistence.BoardAttachVO;
import com.ict.persistence.BoardVO;
import com.ict.persistence.Criteria;
import com.ict.persistence.PageMaker;
import com.ict.persistence.SearchCriteria;
import com.ict.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
// bean container�� �ֱ�
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	// 파일 업로드시 보조해주는 메서드 추가
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
		
	private String getFolder() {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date date = new Date();
			
			String str = sdf.format(date);
			
			return str.replace("-", File.separator);
		}
	
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
		log.info( board);
		service.insert(board);
		
		
		// 첨부파일 정보가 insert시 잘 들어오는지 디버깅
		log.info("=====================");
		log.info("register : " + board);
		
		if(board.getAttachList()!= null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		
		// redirect를 사용해야 전체 글 목록을 로딩해온 다음 화면을 열어준다.
		// 스프링 컨트롤러에서 리다이렉트를 할 때는
		// 목적주소 앞에 redirect:을 추가로 붙이기
		return "redirect:/board/list";
	}
	
	// 글삭제도 post방식으로 처리하도록 한다.
	@PostMapping("/delete")
	public String deleteBoard(Long bno,SearchCriteria cri,RedirectAttributes rttr) {
		// 삭제할 로직의 첨부파일 목록을 먼저 다 가지고 온다.
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		 
		
		// 삭제후 리스트로 돌아갈 수 있도록 내부 로직을 만들어주고
	    // 아래 로직은 DB에 있던 정보만 삭제하므로
		service.delete(bno);
		
		 rttr.addAttribute("page",cri.getPage());
		 rttr.addAttribute("searchType",cri.getSearchType());
		 rttr.addAttribute("keyword",cri.getKeyword());
		// 디테일 페이지에 삭제 요청을 넣을 수 있는 폼을 만들어주기
		 
		 // attachList에 들어있는 정보를 토대로 C:의 파일까지 삭제
		 if(attachList != null || attachList.size() > 0) {
			 deleteFiles(attachList);
		 }

		
		 
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
	
		
		@GetMapping("/uploadForm")
		public void uploadForm() {
			log.info("upload form");
		}
		
		@PostMapping("/uploadFormAction")
		public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
			
			String uploadFolder = "C:\\upload_data\\temp";
			
			for(MultipartFile multipartFile : uploadFile) {
				
				log.info("--------------------------");
				log.info("Upload File Name: " + multipartFile.getOriginalFilename());
				log.info("Upload File Size: " + multipartFile.getSize());
				
				// 다시 자바 변수였던 multipartFile을 외부 파일로 변환하기 위해서는
				// new File(저장위치, 원본 MultipartFile)을 이용해야 한다.
				
				File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
				
				try {
					multipartFile.transferTo(saveFile);
				}catch (Exception e) {
					log.error(e.getMessage());
				}
			}// end for
		}
	
	@PostMapping(value="/uploadFormAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadFormPost(MultipartFile[] uploadFile){
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload_data\\temp";
		
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists()==false) {
			uploadPath.mkdirs();
		}
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(
					uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("only file name: " + uploadFileName);
			
			attachDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath, "s_" + uploadFileName));
					
					Thumbnailator.createThumbnail(
							multipartFile.getInputStream(), thumbnail,
							                                 100,100);
					thumbnail.close();
				}
				list.add(attachDTO);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}// endfor
		return new ResponseEntity<>(list, HttpStatus.OK);
		
	}// uploadFormPost
	
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: " + fileName);
		
		File file = new File("c:\\upload_data\\temp\\" + fileName);
		
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			// 스프링쪽 HttpHeaders import하기 // java.net으로 임포트시 생성자가 오류남
			HttpHeaders header = new HttpHeaders();
			
			// 이 메세지를 통해 파일정보가 들어감
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
					                       header,
					                       HttpStatus.OK);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GetMapping(value="/download",
			   produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(String fileName){
		
		log.info("download file: " + fileName);
		
		Resource resource = new FileSystemResource(
				           "C:\\upload_data\\temp\\" + fileName);
		
		log.info("resource: " + resource);
		
		String resourceName = resource.getFilename();
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Disposition",
					  "attachment; filename=" + 
			           new String(resourceName.getBytes("UTF-8"),
			        		                          "ISO-8859-1"));
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource,
				                             headers,
				                             HttpStatus.OK);
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: " + fileName);
		
		File file = null;
		
		try {
			file = new File("c:\\upload_data\\temp\\" + URLDecoder.decode(fileName,"UTF-8"));
			
			file.delete();
			log.info("이미지 타입체크 : " + type);
			log.info("이미지 여부 : " + type.equals("image"));
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
			
				log.info("largeFileName: " + largeFileName);
				
				file = new File(largeFileName);
				
				file.delete();
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}// delete file method
	
	
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	// 삭제 보조 메서드
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("c:\\upload_data\\temp\\" + attach.getUploadPath() +
						          "\\" + attach.getUuid() + "_" + attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload_data\\temp\\" + attach.getUploadPath()+
							                "\\s_" + attach.getUuid() + "_" + attach.getFileName());
				
					Files.delete(thumbNail);
				  
				}
			}catch(Exception e) {
				log.error(e.getMessage());
			}//end catch
		});//end foreach
	}// end deleteFiles method
	
	
	
}

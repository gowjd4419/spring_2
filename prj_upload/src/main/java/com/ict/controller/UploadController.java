package com.ict.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ict.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
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
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	@PostMapping("/uploadAjaxAction")
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("ajax post update!");
		
		String uploadFolder = "C:\\upload_data\\temp";
		
		// 날짜로 폴더 생성
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("uploadPath : " + uploadPath);
		
		// 해당 경로에 폴더가 존재하는지 체크
		if(uploadPath.exists() == false) {
			// 폴더가 없다면 디렉토리 생성
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------");
			log.info("Upload file name: " + multipartFile.getOriginalFilename());
			log.info("upload file size: " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			// \\를 빼고 온전한 파일이름만 추출해서 uploadFileName에 저장
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("last file name : " + uploadFileName);
			
			// uuid 발급 부분
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			// temp 경로까지만 잡는 uploadFolder 대신 날짜경로까지 잡는 uploadPath로 위치를 교체
			//File saveFile = new File(uploadFolder, uploadFileName);
		try {
			// 일단 파일을 먼저 만들고, 이미지인 경우만 추가로 썸네일 생성
			File saveFile = new File(uploadPath,uploadFileName);
			multipartFile.transferTo(saveFile);
			
			// 이 아래부터 썸네일 생성로직
			if(checkImageType(saveFile)) {
				FileOutputStream thumbnail =
						new FileOutputStream(
								new File(uploadPath,"s_" + uploadFileName));
				
				Thumbnailator.createThumbnail(
						multipartFile.getInputStream(),thumbnail,100,100);
				thumbnail.close();
			   }
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}//end for
	}// upload ajax post method
	
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
	
	
	

}

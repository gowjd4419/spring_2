package com.ict.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
	
	
	// 그림파일 정보를 json으로 리턴해 비동기 요청으로 쓸 수 있도록 만들어주는 getFile메서드
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
	
	
	

}

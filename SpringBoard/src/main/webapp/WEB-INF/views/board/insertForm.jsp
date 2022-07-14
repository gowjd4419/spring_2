<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/uploadAjax.css"/>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 글쓸때, 제목 글쓴이, 본문 을 채우고 submit을 눌러야 한다.
vo에 적힌 명칭을 감안해서 제목, 글쓴이 본문을 쓸 수 있도록 폼태그를 완성시키기 -->
<form action="http://localhost:8181/board/insert" method="post">
   <input type="text" name="title" requried><br>     
   <input type="text" name="writer" requried ><br>  
   <textarea name="content" requried ></textarea> 
   <input type="submit" value="글쓰기">
</form>

 <div class="uploadDiv">
       <input type="file" name="uploadFile" multiple>
  </div>
  
  <div class='uploadResult'>
       <ul>
           <!-- 업로드된 파일들이 여기 나열됨. -->
       </ul>
  </div>
  
  <button id="uploadBtn">Upload</button>
  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  
  <script>
  
  
      let csrfHeaderName = "${_csrf.headerName}"
	  let csrfTokenValue="${_csrf.token}" 
  
     $(document).ready(function(){
    	 // 정규표현식 : 예).com 끝나는 문장 등의 조건이 복잡한 문장을 컴퓨터에게 이해시키기 위한 구문
    	 let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    	 let maxSize = 5242880; // 5MB
    	 
    	 function checkExtension(fileName, fileSize){
    		 if(fileSize >= maxSize){
    			 alert("파일 사이즈 초과");
    			 return false;
    		 }
    		 // regex에 표현해둔 정규식과
    		 if(regex.test(fileName)){
    			 alert("해당 종류의 파일은 업로드할 수 없습니다.");
    			 return false;
    		 }
    		 return true;
    	 }
    	 
    	 let cloneObj = $(".uploadDiv").clone();
    	 
    	$('#uploadBtn').on("click", function(e){
    		
    		let formData = new FormData();
    		console.log("-------빈 폼 생성 체크--------");
    		console.log(formData);
    		
    		let inputFile = $("input[name='uploadFile']");
    		console.log("-------보내진 파일 목록 체크--------");
    		console.log(inputFile);
    		
    		let files = inputFile[0].files;
    		console.log("--------파일들만 뽑아서 체크--------");
    		console.log(files);
    		
    		// 파일 데이터를 폼에 집어넣기
    		for(let i = 0; i < files.length; i++){
    			if(!checkExtension(files[i].name, files[i].size)){
    				return false;// 조건에 맞지않은 파일 포함시 onclick 이벤트 함수자체를 종료시켜버림
    			}
    			formData.append("uploadFile", files[i]);
    		}
    		console.log("------------파일 적재 후 formData 태그------------");
    		console.log(formData);
    		
    		$.ajax({
    			url: '/board/uploadFormAction',
   			    beforeSend : function(xhr) {
   				 xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   				},
    			processData: false,
    			contentType: false,
    			data: formData,
    			dataType: 'json',
    			type: 'POST',
    			success: function(result){
    				console.log(result);
    				alert("Uploaded");
    				
    				showUploadedFile(result);
    				
    				$(".uploadDiv").html(cloneObj.html());
    			}
    		});//ajax
    		
    	}); // uploadBtn onclick
    	
        let uploadResult = $(".uploadResult ul");
    	
    	function showUploadedFile(uploadResultArr){
    		let str = "";
    		
    		$(uploadResultArr).each(function(i,obj){
    			if(!obj.image){
    				
    				let fileCallPath = encodeURIComponent(
    						obj.uploadPath + "/"
    						+ obj.uuid + "_" + obj.fileName);
    				
    				str += "<li "
    				       + "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid
    				       + "' data-filename='" + obj.fileName + "' data-type'" + obj.fileType
    				       + "'><a href='/board/download?fileName=" + fileCallPath
                           + "'>" + "<img src='/resources/pngwing.com.png'>"
    				       + obj.fileName + "</a>"
                           + "<span data-file=\'" + fileCallPath + "\' data-type='file'> X </sapn>"
                           + "</li>";
    			}else{
    			//str += "<li>" + obj.fileName + "</li>";
    			// 수정 코드
    			
    			// 썸네일은 display에 배치
    			let fileCallPath = encodeURIComponent(
    					obj.uploadPath + "/s_"
    					+ obj.uuid + "_" + obj.fileName);
    			// 실제 파일은 download에 배치
    			let fileCallPath2 = encodeURIComponent(
    					obj.uploadPath + "/"
    					+ obj.uuid + "_" + obj.fileName);
    			
    			
    			str += `<li>
    			           <a href='/board/dowload?fileName=\${fileCallPath2}'>
    			             <img src='/board/display?fileName=\${fileCallPath}'>\${obj.fileName}
    			             </a>
    			             <span data-file='\${fileCallPath}' data-type='image'>X</span>
    			        </li>`;
    			
    			}
    		});
    		uploadResult.append(str);
    		
    	}//showUploadeFile
    	
    	$(".uploadResult").on("click","span",function(e){
    		// 파일이름을 span태그 내부의 data-file에서 얻어와서 저장
    		let targetFile = $(this).data("file");
    		// 이미지 여부를 span태그 내부의 data-type 에서 얻어와서 저장
    		let type = $(this).data("type");
    		
    		// 클릭한 span태그와 엮여있는 li를 targetLi에 저장
    		let targetLi = $(this).closest("li");
    		console.log(targetLi);
    		
    		$.ajax({
    			url: '/board/deleteFile',
    			 beforeSend : function(xhr) {
       				 xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
       				},
    			data: {fileName: targetFile, type:type},
    			dataType: 'text',
    			type: 'POST',
    			success: function(result){
    	    		alert(result);
    				// 클릭한 li요소를 화면에서 삭제함(파일삭제 후 화면에서도 삭제.)
    	    		targetLi.remove();
    			}
    		});//ajax
    	}); //span close
    	
     });   // document ready
    	
 </script>
    	
	
</body>
</html>
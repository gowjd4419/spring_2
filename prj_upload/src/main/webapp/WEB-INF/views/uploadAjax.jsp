<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>upload with ajax</h1>
  
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
    			url: '/uploadFormAction',
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
    			str += "<li>" + obj.fileName + "</li>";
    		});
    		uploadResult.append(str);
    		
    	}//showUploadeFile
    	
     });   // document ready
  </script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/resttest/modal.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
  /* uploadResult 결과물 css*/
 #uploadResult{
      width:100%;
      background-color: gray;
   }
   #uploadResult ul {
      display: flex;
      flex-flow: row;
      justify-content: center;
      align-items: center;
   }
   
   #uploadResult ul li {
   list-style: none;
   padding: 10px;
   align-content: center;
   text-align: center;
   }
   
   #uploadResult ul li img{
   width: 100px;
   }
</style>
</head>
<body>
<form action="/board/update" method="post">
   <input type="text" name="title" value="${board.title }"><br>     
   <input type="text" name="writer" value="${board.writer }" ><br>  
   <textarea name="content" requried>${board.content }</textarea> 
   <input type="hidden" name="bno" value="${board.bno }">
   	    <input type="hidden" name="page" value="${param.page}">
	    <input type="hidden" name="searchType" value="${param.searchType}">
	    <input type="hidden" name="keyword" value="${param.keyword}">
	    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
   <input type="submit" id="submitBtn" value="글쓰기" class="btn btn-primary">
</form>

<div class="row">
   <h3 class="text-primary">첨부파일</h3>
   
   <div class="form-group uploadDiv">
       <input type='file' name='uploadFile' multiple>
   </div>
   <button id="uploadBtn">등록</button>
   
   <div id="uploadResult">
      <ul>
          <!-- 첨부파일이 들어갈 위치 -->
      </ul>
   </div>
</div><!-- row -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>

      let csrfHeaderName = "${_csrf.headerName}"
	  let csrfTokenValue="${_csrf.token}" 
	  
	// 어떤 글의 첨부파일인지 확인하기위해 bno를 선언해 받아넣어주세요.
	let bno = ${board.bno};
	
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

(function(){
	$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
		console.log(arr);
		
		let str="";
		
		$(arr).each(function(i, attach){
			// image type
			if(attach.fileType){
				let fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" +
						attach.uuid + "_" + attach.fileName);
				str += "<li data-path='" + attach.uploadPath + "' data-uuid='"
				    + attach.uuid + "' data-filename='" + attach.fileName
				    + "' data-type='" + attach.fileType + "' ><div>"
				    + "<span>" + attach.fileName + "</span>"
				    + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' "
				    + "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
				    + "<img src='/board/display?fileName=" + fileCallPath + "'>"
				   	+ "</div>"
				    + "</li>";
			}else{
				let fileCallPath = encodeURIComponent(attach.uploadPath + "/" +
							attach.uuid + "_" + attach.fileName);
				str += "<li data-path='" + attach.uploadPath + "' data-uuid='"
			    + attach.uuid + "' data-filename='" + attach.fileName
			    + "' data-type='" + attach.fileType + "' ><div>"
			    + "<span> " + attach.fileName + "</span>"
			    + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' "
			    + "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
			    + "<img src='/resources/pngwing.com.png' width='100px' height='100px'>"
			    + "</div>"
			    +"</li>";
			}
		}); // .each 반복문 닫는부분
		// 위에서 str변수에 작성된 태그 형식을 화면에 끼워넣기
		$("#uploadResult ul").html(str);
		
	}); // end getJSON
})(); // end anonymous

	$("#uploadResult").on("click", "button", function(e){
		if(confirm("선택한 파일을 삭제하시겠습니까?")){
			let targetLi = $(this).closest("li");
			targetLi.remove();
			
			let regex
		}
	});
	
	
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
	
	let uploadResult = $("#uploadResult ul");
	
	function showUploadedFile(uploadResultArr){
		let str = "";
		
		$(uploadResultArr).each(function(i,obj){
			// 방금 업로드한 파일이 안올라감
			console.log(uploadResult);
			if(!obj.image){
				
				let fileCallPath = encodeURIComponent(
						obj.uploadPath + "/"
						+ obj.uuid + "_" + obj.fileName);
				
				str += "<li data-path='" + obj.uploadPath + "' data-uuid='"
				    + obj.uuid + "' data-filename='" + obj.fileName
				    + "' data-type='" + obj.fileType + "' ><div>"
				    + "<span> " + obj.fileName + "</span>"
				    + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' "
				    + "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
				    + "<img src='/resources/pngwing.com.png' width='100px' height='100px'>"
				    + "</div>"
				    +"</li>";
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
			
			
		str += "<li data-path='" + obj.uploadPath + "' data-uuid='"
		    + obj.uuid + "' data-filename='" + obj.fileName
		    + "' data-type='" + obj.image + "' ><div>"
		    + "<span>" + obj.fileName + "</span>"
		    + "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' "
		    + "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>"
		    + "<img src='/board/display?fileName=" + fileCallPath + "'>"
		    + "</div>"
		    +"</li>";
			
			}
		});
		uploadResult.append(str);
		
	}//showUploadeFile
	
	$("#submitBtn").on("click",function(e){
		// 1.버튼 기능을 막기
		e.preventDefault();
		
		// 2.let formObj = $("form");로 폼태그를 가져온다.
		let formObj = $("form");
		
		// 3.formObj 내부에 64페이지 장표를 참고해서
		// hidden태그들을 순서대로 만들어줍니다.
		
		let str = "";
		
		console.log($(".uploadResult ul li"));
		
		$("#uploadResult ul li").each(function(i, obj){
			let jobj = $(obj);
			
			str += "<input type='hidden' name='attachList[" + i + "].fileName' "
			    + "value='" + jobj.data("filename") + "' >"
			    + "<input type='hidden' name='attachList[" + i + "].uuid' "
			    + "value='" + jobj.data("uuid") + "' >"
			    + "<input type='hidden' name='attachList[" + i + "].uploadPath' "
			    + "value='" + jobj.data("path") + "' >"
			    + "<input type='hidden' name='attachList[" + i + "].fileType' "
			    + "value='" + jobj.data("type") + "' >"
		
		
		});
		console.log(str);
		
		// 4. formObj에 append를 이용해 str을 끼워넣는다.
		formObj.append(str);
		
		// 5. formObj.submit()을 이용해 제출기능이 실행되도록한다.
		formObj.submit();
		
	});
	
});   // document ready
		
</script>

</body>
</html>
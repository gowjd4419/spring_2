<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/resources/resttest/modal.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/board/list?page=${param.page }&searchType=${param.searchType}&keyword=${param.keyword}"><button>목록</button></a>
 
 <form action="http://localhost:8181/board/delete" method="post">
    <input type="hidden" name="bno" value="${board.bno}">
    <input type="hidden" name="page" value="${param.page}">
	<input type="hidden" name="searchType" value="${param.searchType}">
	<input type="hidden" name="keyword" value="${param.keyword}">
  <table class="table table-hover">
         <thead>
             <tr>
                <td>글번호</td>
                <td>글제목</td>
                <td>글쓴이</td>
                <td>쓴날짜</td>
                <td>수정날짜</td>
             </tr>
         </thead>
          <tbody>
              <tr>
                 <td>${board.bno }</td>
                 <td>${board.title }</td>
                 <td>${board.writer }</td>
                 <td>${board.regDate }</td>
                 <td>${board.updateDate }</td>
              </tr>
          </tbody>
     </table>
	   <input type="submit" value="삭제하기">
	</form>
	
	<form action="/board/updateForm" method="post">
	    <input type="hidden" name="bno" value="${board.bno}">
	    <input type="hidden" name="page" value="${param.page}">
	    <input type="hidden" name="searchType" value="${param.searchType}">
	    <input type="hidden" name="keyword" value="${param.keyword}">
	     <input type="submit" value="수정하기">
	 </form>
	 
	 <!-- 댓글달리는 영역 -->
	 <div class="row">
	    <h3 class="text-primary">댓글</h3>
	    <div id="replies">
	        <!-- 댓글이 들어갈 위치 -->
	    </div> 
     </div><!-- row -->
     
    <!-- 댓글쓰기 -->
   <div>
      <div>
         REPLYER <input type="text" name="replyer" id="newReplyWriter">
      </div>
      <div>
         REPLY TEXT <input type="text" name="reply" id="newReplyText">
      </div>
      <button id="replyAddBtn">ADD REPLY</button>
   </div>
   
   
   <div id="modDiv" style="display:none;">
       <div class="modal-title"></div>
       <div>
          <input type="text" id="replytext">
       </div>
       <div>
           <button type="button" id="replyModBtn">수정하기</button>
           <button type="button" id="replyDelBtn">삭제하기</button>
           <button type="button" id="closeBtn">닫기</button>
       </div>
   </div>
   
	 
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	 
	 <script>
	 function getAllList(){
		    let bno = ${board.bno};
			let str = "";
			// json 데이터를 얻어오는 로직 실행
			$.getJSON("/replies/all/" + bno, function(data){
				$(data).each(
					function(){
						console.log(this);
						// 백틱 문자열 사이에 변수를 넣고 싶다면 \${변수명} 을 적습니다.
						// 원래는 \를 왼쪽에 붙일 필요는 없지만
						// jsp에서는 el표기문법이랑 겹치기 때문에 el이 아님을 보여주기 위해
						// 추가로 왼쪽에 \를 붙입니다.
						
						// UNIX시간을 우리가 알고 있는 형식으로 바꿔보기
						let timestamp = this.updateDate;
						// UNIX시간이 저장된 timestamp를 Date 생성자로 변환한다.
						let date = new Date(timestamp);
						// 변수 formateedtime에 변환된 시간을 저장해 출력해본다
						let formattedTime = `게시일 : \${date.getFullYear()}년
						                            \${(date.getMonth()+1)}월
						                            \${date.getDate()}일`;
	str += 
		`<div class='replyLi' data-rno='\${this.rno}'>
	        <strong>@\${this.replyer}</strong>-\${formattedTime}<br/>
	        <div class='replytext'>\${this.reply}</div>
	        <button type='button' class='btn btn-info'>수정/삭제</button>
	        </div>`;
				});
				console.log(str);
				$("#replies").html(str);
			});			
		}
		getAllList();
		
		$("#replies").on("click", ".replyLi button", function(){// 화살표 함수는 this키워드를 쓰지 않을때만 쓸 수 있음
			 // 4. 콜백함수 내부의 this는 내가 클릭한 button이 된다.
			 // 선택 요소와 연관된 태그 고르기
			 // 1.prev().prev()... 등과 같이 연쇄적으로 prev, next를 걸어서 고르기 나는 한번만해도돼서 한번만 함
			 // 2.prev("태그선택자")를 써서 뒤족이나 앞쪽 형제 중 조건에 맞는것만 선택
			 // 3.siblings("태그선택자")는 next, prev 모두를 범위로 조회한다.
				let reply = $(this).siblings(".replytext");
		 
		 
		        // .attr("태그 내 속성명") => 해당 속성에 부여된 값을 가져온다.
		        // ex) <li data-rno="33"> => rno에 33을 저장해줌
				let rno = reply.attr("data-rno");
				let replytext = reply.text();
				
				$(".modal-title").html(rno);
				$("#replytext").val(replytext);
				$("#modDiv").show("slow");
		 });// 댓글 삽입
		 
		 $("#replyAddBtn").on("click", function(){
	    	 let replyer = $("#newReplyWriter").val();  // 받아오기
	    	 let reply = $("#newReplyText").val();  
	    	 
	    	 $.ajax({
	    		 type : 'post',
	    		 url : '/replies',
	    		 headers : {
	    			 "Content-Type" : "application/json",
	    			 "X-HTTP-Method-Override" : "POST"
	    		 },
	    		 dataType : 'text',
	    		 data : JSON.stringify({
	    			 bno : ${board.bno},
	    			 replyer : replyer,
	    			 reply : reply
	    		 }),
	    		 success : function(result){
	    			 if(result == 'SUCCESS'){
	    				 
	    				 alert("등록 되었습니다.");
	    				 getAllList();
	    				 $("#newReplyWriter").val(''); // 갱신 ''비워놔라
	    				 $("#newReplyText").val(''); 
	    			 }
	    		 }
	    		 
	    	 });
	    	 
	      });// 글 등록로직 종료
	      
		</script>
		
	  <script src="/resources/resttest/delete.js"></script>
      <script src="/resources/resttest/modify.js"></script>
      <script src="/resources/resttest/modalclose.js"></script>
</body>
</html>
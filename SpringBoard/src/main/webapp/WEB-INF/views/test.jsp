<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <!-- .css, .js, 그림파일 등은 src/main/webapp/resources폴더 아래에 저장한다음
   /resources/경로 형식으로 적으면 가져올 수 있다.
   이렇게 경로가 자동으로 잡히는 이유는 servlet-context.xml에 설정이 잡혀있기 때문이다. -->
   <link rel="stylesheet" href="/resources/resttest/modal.css">
<meta charset="UTF-8">
<title>Insert title here</title>  
</head>
<body>
   <h2>Ajax 테스트</h2>
   
   <div>
      <div>
         REPLYER <input type="text" name="replyer" id="newReplyWriter">
      </div>
      <div>
         REPLY TEXT <input type="text" name="reply" id="newReplyText">
      </div>
      <button id="replyAddBtn">ADD REPLY</button>
   </div>
   
   
   
   <!-- 댓글달리는 영역 -->
   <ul id="replies">
   
   </ul>
   
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
   
   <!-- jquery는 이곳에 -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
     <script>
		let bno = 100;
		
		function getAllList(){
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
	str+=
	`<li data-rno='\${this.rno}' class='replyLi'>\${this.rno}:\${this.reply} <button>수정/삭제</button></li>`;
				});
				console.log(str);
				$("#replies").html(str);
			});			
		}
		getAllList();
		
		////////////////////////////
		//////글 등록로직
		////////////////////////////
	
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
    			 bno : bno,
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
      
         //////////////////////////////
		 /// 댓글수정하기 이벤트 연결
		 /////////////////////////////
		 // 이벤트 위임
		 
		 // 1. ul#replies가 이벤트를 걸고 싶은 버튼 전체의 집합이므로 먼저 집합 전체에 이벤트를 건다.
		 // 2. #replies의 하위 항목 중 최종 목표 태그를 기입해준다.
		 // 3. 단, 여기서  #replies와 button 사이에 다른 태그가 끼어있다면 경유하는 형식으로 호출해도 되고 안해도된다.
		 $("#replies").on("click", ".replyLi button", function(){// 화살표 함수는 this키워드를 쓰지 않을때만 쓸 수 있음
			 // 4. 콜백함수 내부의 this는 내가 클릭한 button이 된다.
				let reply = $(this).parent();
		 
		 
		        // .attr("태그 내 속성명") => 해당 속성에 부여된 값을 가져온다.
		        // ex) <li data-rno="33"> => rno에 33을 저장해줌
				let rno = reply.attr("data-rno");
				let replytext = reply.text();
				
				$(".modal-title").html(rno);
				$("#replytext").val(replytext);
				$("#modDiv").show("slow");
		 });// 댓글 삽입
		 
		
		 
      </script>
      <!-- css파일의 경우는 link채그의 href로 경로 지정을, js파일인 경우는 script 태그의 src로 경로지정을 한다. -->
      <script src="/resources/resttest/delete.js"></script>
      <script src="/resources/resttest/modify.js"></script>
</body>
</html>
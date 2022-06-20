<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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

   
   <!-- jquery는 이곳에 -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  
     
      
     <script>
		let bno = 100;
		let str = "";
		function getAllList(){
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
	`<li data-rno='\${this.rno}' class='replyLi'>\${this.rno}:\${this.reply}</li>`;
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
    	 let replyer = $("#newReplyWriter").val();  
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
    			 }
    		 }
    	 });
      });// 글 등록로직 종료
      
      </script>
     
   
</body>
</html>
 // 수정버튼 작동
 $("#replyModBtn").on("click",function(){
	 let rno = $(".modal-title").html();
	 let reply = $("#replytext").val();
	 
	 $.ajax({
		 type : 'put',
		 url : '/replies/' + rno,
		 header : {
			 "Content-Type" : "application/json",
			 "X-HTTP-Method-Override" : "PUT"
		 },
		 contentType : "application/json",
		 data: JSON.stringify({reply:reply}),
		 dataType : 'text',
		 success : function(result){
			 console.log("result: " + result);
			 if(result == 'SUCCESS'){
				 alert("수정 되었습니다.");
				 $("#modDiv").hide("slow");
				 getAllList();
			 }
		 }
	 })
 })
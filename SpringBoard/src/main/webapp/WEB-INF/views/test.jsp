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
   
   <ul id="replies">
   
   </ul>
   
   <!-- jquery는 이곳에 -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   
   <script>
      let bno = 100;
      
    	  $.getJSON("/replies/all/" + bno, function(data){
       	     $(data).each(
       	    		 function(){
       	    			console.log(this);
       	    			console.log("-------------");
       	    });
         });  
     
      
   </script>
</body>
</html>
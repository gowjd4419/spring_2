<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
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
	
</body>
</html>
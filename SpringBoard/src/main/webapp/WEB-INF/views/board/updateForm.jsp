<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
   <input type="submit" value="글쓰기">
</form>
</body>
</html>
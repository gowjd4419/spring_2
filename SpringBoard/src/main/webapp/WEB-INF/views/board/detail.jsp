<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
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


</body>
</html>
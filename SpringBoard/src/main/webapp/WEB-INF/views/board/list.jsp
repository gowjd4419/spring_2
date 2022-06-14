<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		 <div class="container">
		   <div class="row">
		       <h1 class="text-primary text-center">전체 글 목록</h1>
		   </div>
		   <div class="row">
		      <div class="box-body">
		         
		         <select name="searchType">
		             <option value="n" ${cri.seachType == null ? 'selected' : '' }>
		             -
		             </option>
		             <option value="t" ${cri.seachType == 't' ? 'selected' : '' }>
		             제목
		             </option>
		             <option value="c" ${cri.seachType == 'n' ? 'selected' : '' }>
		             본문
		             </option>
		             <option value="w" ${cri.seachType == 'w' ? 'selected' : '' }>
		             글쓴이
		             </option>
		             <option value="tc" ${cri.seachType == 'tc' ? 'selected' : '' }>
		             제목 + 본문
		             </option>
		             <option value="cw" ${cri.seachType == 'cw' ? 'selected' : '' }>
		             본문 + 글쓴이
		             </option>
		             <option value="tcw" ${cri.seachType == 'tcw' ? 'selected' : '' }>
		             제목 + 본문 + 글쓴이
		             </option>
		         </select>
		         
		         <input type="text"
		             name="keyword"
		             id="keywordInput"
		             value="${cri.keyword }">
		          <button id="searchBtn">Search</button>
		         <script>
		         $('#searchBtn').on("click", function(event){
		            self.location = "list"
		               + "?page=1"
		               + "&searchType="
		               + $("select option:selected").val()
		               +"&keyword=" + $("#keywordInput").val();
		          })
		          </script>
		      </div>
		   </div>
		 </div>
		 
		 
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
             <c:forEach var="board" items="${boardList }">
              <tr>
                 <td>${board.bno }</td>
                 <td><a href="http://localhost:8181/board/detail?bno=${board.bno}&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}&page=${pageMaker.cri.page}">${board.title }</td>
                 <td>${board.writer }</td>
                 <td>${board.regDate }</td>
                 <td>${board.updateDate }</td>
              </tr>
              </c:forEach>
          </tbody>
     </table>
    
	<a href="/board/insert"><button class="btn btn-primary">글쓰기</button></a>
		<ul class="pagination">
		  <c:if test="${pageMaker.prev}">
		     <li class="page-item">
		       <a class="page-link" href="http://localhost:8181/board/list?page=${pageMaker.startPage -1}&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		       </a>
		     </li>
		   </c:if>
		   
		   <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pNum">
		       <li class="page-item ${pNum eq pageMaker.cri.page ? 'active' : '' }"><a class="page-link" href="http://localhost:8181/board/list?page=${pNum}&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}">${pNum}</a></li>
		   </c:forEach>
		   
		   <c:if test="${pageMaker.next}">
		     <li class="page-item">
		       <a class="page-link" href="http://localhost:8181/board/list?page=${pageMaker.endPage +1}&searchType=${pageMaker.cri.searchType}&keyword=${pageMaker.cri.keyword}" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		       </a>
		     </li>
		   </c:if>
		 </ul>
 ${pageMaker }
</body>
</html>
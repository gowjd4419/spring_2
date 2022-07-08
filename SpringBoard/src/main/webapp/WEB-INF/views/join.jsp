<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <h1>회원가입창</h1>
  <form action="/join" method="post">
      아이디 : <input type="text" name="userid"><br>
      비밀번호 : <input type="text" name="userpw"><br>
      이름 : <input type="text" name="userName"><br>
      <input type="checkbox" name="role" value="ROLE_ADMIN">어드민 권한&nbsp;&nbsp;&nbsp;;
      <input type="checkbox" name="role" value="ROLE_ADMIN"> 권한&nbsp;&nbsp;&nbsp;;
  </form>

</body>
</html>
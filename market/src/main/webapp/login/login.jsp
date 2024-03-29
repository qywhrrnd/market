<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<c:if test="${param.message == 'report' }">
   <script>
      alert("신고로 인해 로그인 불가 아이디입니다.");
   </script>
</c:if>
<!-- 아이디와 비밀번호 불일치시 뜨는 메시지 -->
<c:if test="${param.message == 'error' }">
   <script>
      alert("아이디 또는 비밀번호가 일치하지 않습니다.");
   </script>
</c:if>
<c:if test="${param.message == 'logout' }">
   <script>
      alert("로그아웃 되었습니다.");
   </script>
</c:if>
<link href="/market/include/mycss/login.css" rel="stylesheet" />
<style>
a {
  text-decoration: none; /* 링크의 밑줄 제거 */
  color: inherit; /* 부모 요소의 색상 상속 */
}
</style>
</head>
<body>
   

   <div class="container">
      <!-- Heading -->
      <a href="../main/main.jsp">
         <h1>..마켓</h1>
      </a>
      <!-- Links -->
      <ul class="links">
         <li><a href="#" id="signin">로그인</a></li>

         <li><a onclick="location.href='../login/join.jsp'" id="signup">회원가입</a>
         </li>

      </ul>

      <!-- Form -->
      <form method="post" action="/market/login_servlet/login.do">
         <!-- 아이디 넣기 -->
         <div class="first-input input__block first-input__block">
            <input placeholder="아이디" class="input" id="userid" name="userid" />
         </div>
         <!-- 비밀번호 넣기 -->
         <div class="input__block">
            <input type="password" placeholder="비밀번호" class="input"
               id="password" name="passwd" />
         </div>
         <!-- 로그인 버튼 -->
         <button type="submit" value="로그인" class="signin__btn">로그인</button>
      
   
      </form>
   </div>

</body>
</html>
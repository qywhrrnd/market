<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/market/include/mycss/login.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="../main/menu.jsp"%>
	<div class="container">
		<!-- Heading -->
		<h1>아이디 찾기</h1>



		<!-- Form -->
		<form method="post" action="/market/login_servlet/findid.do">
			<!-- 아이디 넣기 -->
			<div class="first-input input__block first-input__block">
				<input placeholder="이름" class="input" id="name" name="name" />
			</div>
			<!-- 비밀번호 넣기 -->
			<div class="input__block">
				<input type="number" placeholder="생년월일" class="input" id="birth"
					name="birth" />
			</div>


			<div class="input__block">
				<input type="number" placeholder="'-'를 빼고 입력해주세요" class="input"
					id="phone" name="phone" />
			</div>
			<!-- 로그인 버튼 -->
			<button type="submit" class="signin__btn">아이디 찾기</button>


		</form>
	</div>
</body>
</html>
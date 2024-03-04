<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quiz</title>
</head>
<body>
<%@ include file="../main/menu.jsp"%>

<form method="post" action="/market/event_servlet/quiz.do">
	<input type="radio" name="num" value="1">${dto.ans1}<br>
	<input type="radio" name="num" value="2">${dto.ans2}<br>
	<input type="radio" name="num" value="3">${dto.ans3}<br>
	<input type="radio" name="num" value="4">${dto.ans4}<br>
	<input type="hidden" name="survey_idx" value="${dto.survey_idx}">
	<input type="submit" value="OK">
</form>

</body>
</html>
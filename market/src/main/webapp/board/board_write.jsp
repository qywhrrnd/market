<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>
function returnBtn() {
	if(confirm("게시글 작성을 나가시겠습니까?\n변경사항이 저장되지 않을 수 있습니다.") == true) { // 확인
		window.history.back();
	} else {
		return false;
	}	
}
$(function() {
	$("#btnSave").click(function() {
		let nickname = $("#nickname").val();
		let subject = $("#subject").val();
		let content = $("#content").val();
		
		if (subject == "") {
			alert("제목을 입력하세요.");
			$("#subject").focus();
			return;
		}
		if (content == "") {
			alert("내용을 입력하세요.");
			$("#content").focus();
			return;
		}
		document.form1.submit();
	});
});
</script>
<style>
.board-write {
	margin: 5 0px auto;
	width: 100%;
	table-layout: fixed;
}
</style>
</head>
<body>
<h2>게시글 작성</h2>
<form name="form1" method="post" action="/market/board_servlet/insert.do" >
<table class="board-write">
	<tr>
		<td>제목</td>
		<td><input type="text" name="subject"></td>
	</tr>
	<tr>
		<td>닉네임</td>
		<td><input value="${sessionScope.nickname}" name="nickname" readonly></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea name="content" rows="5" cols="50"></textarea></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td colspan="2" align="center">
			<input type="button" value="확인" id="btnSave">
			<input type="button" value="나가기" onclick="returnBtn()">
		</td>
	</tr>
</table>
</form>
</body>
</html>
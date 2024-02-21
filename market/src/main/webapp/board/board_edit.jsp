<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	$("#btnDelete").click(function() {
		if(confirm("정말 삭제하시겠습니까?")) {
			document.form1.action = "/market/board_servlet/delete.do";
			document.form1.submit();
		}
	});
	
	$("#btnUpdate").click(function() {
		let nickname = $("#nickname").val();	
		let subject = $("#subject").val();	
		let content = $("#content").val();
		
		if(subject == "") {
			alert("제목을 입력하세요");
			$("#subject").focus();
			return;
		}
		if(content == "") {
			alert("내용을 입력하세요");
			$("#content").focus();
			return;
		}
		document.form1.action = "/market/board_servlet/update.do";
		document.form1.submit();
	});
	
	$("#btnView").click(function() {
		if(confirm("정말 돌아가시겠습니까?\n변경사항은 저장되지 않습니다.")) {
			document.form1.action = "/market/board_servlet/view.do";
			document.form1.submit();
		}
    });
});
</script>
</head>
<body>
<div>
<form name="form1" method="post">
<table class="board-list" width="100%">
    <tr>
        <th width="20%">날짜:</th>
        <td width="40%">${dto.reg_date}</td>
        <th width="20%">조회수:</th>
        <td width="20%">${dto.hit}</td>
    </tr>
    <tr>
        <td colspan="4"><hr></td>
    </tr>
    <tr>
        <th width="100%">닉네임:</th>
        <td colspan="3"><input name="nickname" id="nickname" value="${dto.nickname}" readonlyS></td>
    </tr>
    <tr>
        <th>제목:</th>
        <td colspan="3"><input name="subject" id="subject" value="${dto.subject}" size="60"></td>
    </tr>
    <tr>
        <th>본문:</th>
        <td colspan="3"><textarea rows="5" cols="50" name="content" id="content">${dto.content}</textarea></td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <input type="hidden" name="num" value="${dto.num}">
            <button id="btnUpdate">수정</button>
            <button id="btnDelete">삭제</button>
            <button id="btnView" data-num="${dto.num}">돌아가기</button>
        </td>
    </tr>
</table>
</form>
</div>
</body>
</html>
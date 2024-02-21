<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>

$(function() {
    $("#btnList").click(function() {
        location.href = "/market/board_servlet/list.do";
    });
});

$(function() {
    // 현재 로그인된 아이디
    let nowUserId = "${sessionScope.userid}";
    // 게시글 작성한 사람의 아이디
    let postUserId = "${dto.userid}";
    
    // 수정/삭제 버튼
    let btnEdit = $("#btnEdit");

    // 만약 현재 로그인된 아이디랑 게시글 작성한 사람의 아이디가 일치한다면?
    if (nowUserId === postUserId) {
        btnEdit.prop("disabled", false).show(); //버튼 활성화
        btnEdit.click(function() { // 클릭시 이벤트
            let num = $("#num").val();
            location.href = "/market/board_servlet/edit.do?num=" + num;
        });
    } else {
        // 일치하지 않으면 버튼을 비활성화!
        btnEdit.prop("disabled", true).hide();
    }
});
</script>
<style>
.center-container {
	display: flex;
	justify-content: center;
}
</style>
</head>
<body>
<div class="center-container">
<article>
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
        <td colspan="3">${dto.nickname}</td>
    </tr>
    <tr>
        <th>제목:</th>
        <td colspan="3">${dto.subject}</td>
    </tr>
    <tr>
        <th>본문:</th>
        <td colspan="3"><textarea rows="5" cols="50" readonly>${dto.content}</textarea></td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <input type="hidden" name="num" id="num" value="${dto.num}">
            <button id="btnEdit">수정/삭제</button>
            <button id="btnReply">답변</button>
            <button id="btnList">돌아가기</button>
        </td>
    </tr>
</table>
</article>
</div>
</body>
</html>
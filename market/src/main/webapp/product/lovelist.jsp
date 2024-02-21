<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	$("#chkAll").click(function() {
		if ($("#chkAll").prop("checked")) {
			$("input[name=num]").prop("checked",true);
		} else {
			$("input[name=num]").prop("checked",false);
		}
	});
	
	$("#btnAllDel").click(function() {
		let arr = $("input[name=num]"); //체크박스 배열
		let cnt = 0; //체크된 태그 카운트
		for(i=0; i<arr.length; i++) {
			if (arr[i].checked == true) cnt++;
		}
	    if(cnt == 0) {
	    	alert("삭제할 상품을 선택해주세요");
	    	return;
	    }
		document.form1.action = "/market/love_servlet/delete_all.do";
	    document.form1.submit();
	});
});

function lovelist_del(write_code) {
	location.href = "/market/love_servlet/delete.do?write_code=" + write_code;
}
</script>
</head>
<body>
<c:choose>
<c:when test="${list.size() == 0}">
	찜한 상품이 없습니다.
</c:when>
<c:when test="${list.size() > 0}">

<form method="post" name="form1">
<table>
	<tr>
		<th width="100"><input type="checkbox" id="chkAll"></th>
		<th width="100">상품이미지</th>
		<th width="100">제목</th>
		<th width="100">가격</th>
		<!-- <th>날짜</th> -->
		<th><input type="button" value="전체삭제" id="btnAllDel"></th>	
	</tr>
	<c:forEach var="row" items="${list}"> <!-- 컨트롤러의 setAttribute에서 넘어옴 -->
	           <!-- 개별행      넘어온 리스트 -->
		<tr>
			<td><input type="checkbox" name="num" value="${row.write_code}"></td>
			<td align="center"><img src="/market/images/${row.filename}" width="100px" height="100px"></td>
			<td><a href="/market/mk_servlet/detail.do?write_code=${row.write_code}">${row.subject}</a></td>
			<td><fmt:formatNumber value="${row.price}" pattern="#,###" /></td>
			<td><input type="button" value="삭제" onclick="lovelist_del('${row.write_code}')"></td>
		</tr>
	</c:forEach>
</table>
</form>
</c:when>
</c:choose>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<c:if test="${sessionScope.userid == 'admin'}" >
	<%@ include file="../admin/admin_menu.jsp"%>
</c:if>
<c:if test="${sessionScope.userid != 'admin'}" >
	<%@ include file="../main/menu.jsp"%>
</c:if>
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
	    	alert("삭제할 신고내역을 선택해주세요");
	    	return;
	    }
		document.form1.action = "/market/report_servlet/delete_all.do";
	    document.form1.submit();
	});
});

function reportlist_del(idx) {
	location.href = "/market/report_servlet/delete.do?idx=" + idx;
}
</script>
</head>
<body>
<c:choose>
<c:when test="${list.size() == 0}">
	신고내역이 없습니다.
</c:when>
<c:when test="${list.size() > 0}">

<form method="post" name="form1">
<table>
	<tr>
		<th width="10%"><input type="checkbox" id="chkAll"></th>
		<th width="10%">제목</th>
		<th width="10%">신고인</th>
		<th width="10%">피신고인</th>
		<th width="50%">신고내용</th>
		<!-- <th>날짜</th> -->
		<th width="10%"><input type="button" value="선택삭제" id="btnAllDel"></th>	
	</tr>
	<c:forEach var="row" items="${list}"> <!-- 컨트롤러의 setAttribute에서 넘어옴 -->
	           <!-- 개별행      넘어온 리스트 -->
		<tr>
			<td><input type="checkbox" name="num" value="${row.idx}"></td>
			<td><a href="${pageContext.request.contextPath}${row.link.substring(7)}">${row.report_subject}</a></td>
			<td>${row.reporter}</td>
			<td>${row.report_userid}</td>
			<td>${row.report_contents}</td>
			<td><input type="button" value="삭제" onclick="reportlist_del('${row.idx}')"></td>
		</tr>
	</c:forEach>
</table>
</form>
</c:when>
</c:choose>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$(document).ready(function(){
    $(".a").click(function(){
        // 클릭된 행의 다른 ID를 가져와야 합니다.
        var toid = $(this).data("toid");
        location.href = "/market/chat_servlet/chat.do?toid=" + toid +"&userid="+"${sessionScope.userid}";   
    });
});
</script>
</head>
<body>
<c:if test="${sessionScope.userid == 'admin'}">
		<%@ include file="../admin/admin_menu.jsp"%>
	</c:if>
	<c:if test="${sessionScope.userid != 'admin'}">
		<%@ include file="../main/menu.jsp"%>
	</c:if>
<table border="1">
    <tr>
        <th>상대방</th>
        <th>시간</th>
    </tr>
    <c:forEach var="row" items="${list}">
    <c:if test="${sessionScope.userid == row.toid}"> 
            <td>${row.fromid}</td>
            <td>${row.max_time}</td>
        </tr>
    </c:if>
    
    <c:if test="${sessionScope.userid != row.toid && sessionScope.userid != row.fromid}">
        <tr class="a" data-toid="${row.toid}">
            <td>${row.toid}</td>
            <td>${row.max_time}</td>
        </tr>
    </c:if>
</c:forEach>

</table>
</body>
</html>

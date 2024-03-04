<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/market/include/css/bootstrap.css">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/market/include/js/bootstrap.js"></script>

<title>Market Main</title>
<c:if test="${sessionScope.userid == 'admin'}">
		<%@ include file="../admin/admin_menu.jsp"%>
	</c:if>
	<c:if test="${sessionScope.userid != 'admin'}">
		<%@ include file="../main/menu.jsp"%>
	</c:if>


</head>
<body>

</body>

</html>
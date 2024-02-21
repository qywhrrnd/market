<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>
function delete_product(write_code) {
	if (confirm("삭제하시겠습니까?")) {
		location.href = "/market/mk_servlet/delete.do?write_code="+write_code;
		
	}
} 

/* function delete_Product(write_Code) {
    if (confirm("삭제하시겠습니까?")) {
        $.ajax({
            type: "GET",
            url: "/market/mk_servlet/delete.do",
            data: { write_code: write_code },
            success: function() {
                // 삭제 후 필요한 작업 수행
                location.reload(); // 예: 페이지 새로고침
            },
            error: function() {
                alert("삭제 실패");
            }
        });
    }
} */
</script>
</head>
<body>
<%@ include file="../main/menu.jsp"%>
<div align="center">
<form method="post" name="form1" >
	<h2 align="center">내 판매게시물</h2>
	<table border="1" style="width: 200%">
	
		<tr align="center">
			<th style="width: 200px">제목(상품명)</th>
			<th style="width: 300px">사진</th>
			<th style="width: 100px">가격</th>
			<th style="width: 200px">&nbsp;</th>
		</tr>

		<c:forEach var="row" items="${list}">
			<c:if test="${row.userid eq sessionScope.userid}">
				<tr>
					<td align="center"><a
						href="/market/mk_servlet/detail.do?write_code=${row.write_code}">
							${row.subject}</a></td>

					<td align="center"><img src="/market/images/${row.filename}"
						width="100px" height="100px"></td>

					<td align="center"><fmt:formatNumber value="${row.price}"
							pattern="#,###" />
					<c:if test="${sessionScope.userid != null}">
						
					<td align="center"><a href="/market/mk_servlet/edit.do?write_code=${row.write_code}">[편집]</a> 
					&nbsp;<input type="button" onclick="delete_product('${row.write_code}')" value="삭제"></td>
					
					</c:if>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
	</form>
	</div>
</body>
</html>
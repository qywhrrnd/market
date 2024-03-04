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
			location.href = "/market/mk_servlet/delete.do?write_code="
					+ write_code;
		}
	}
	function updateStatus(write_code,status_code) {
		if (confirm("판매 상태를 변경하시겠습니까?")){
			location.href = "/market/mk_servlet/updateStatus.do?write_code=" + write_code + "&status_code=" + status_code;
			
		}
	}
</script>
</head>
<body>
	   <%@ include file="../main/menu.jsp"%>
   <c:choose>
      <c:when test="${list.size() == 0}">
   판매하는 상품이 없습니다.
   </c:when>
      <c:when test="${list.size() > 0}">
      
	<div align="center">
		<form method="post" name="form1">
			<h2 align="center">내 전체게시물</h2>
			<!-- <input type="button" value="전체" onclick="">&nbsp; <input
				type="button" value="판매중" onclick="">&nbsp; <input
				type="button" value="판매완료" onclick=""><br> -->
	

			<table border="1" style="width: 100%">

				<tr align="center">
					<th style="width: 200px">제목(상품명)</th>
					<th style="width: 300px">사진</th>
					<th style="width: 100px">가격</th>
					<th style="width: 200px">&nbsp;</th>
					<th style="width: 200px">판매상태</th>
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

									<td align="center"><a
										href="/market/mk_servlet/edit.do?write_code=${row.write_code}">[편집]</a>
										&nbsp;<input type="button"
										onclick="delete_product('${row.write_code}')" value="삭제"></td>
									<td align="center">
									<%-- <c:if test="${sessionScope.userid != null}"> --%>
										<select id="statusSelect" onchange="updateStatus('${row.write_code}', this.value)">
											<option value="선택">선택</option>
											<option value="0" ${row.status_type eq '판매중'? 'selected' : '' }>판매중</option>
											<option value="1" ${row.status_type eq '판매완료'? 'selected' : '' }>판매완료</option>
											<option value="2" ${row.status_type eq '판매보류'? 'selected' : '' }>판매보류</option>			
										</select>				
									<%-- </c:if> --%>									
								</c:if>
								</td>
								<td>${row.status_type}</td>
						</tr>  
					</c:if>
				</c:forEach>
			</table>
		</form>
	</div>
	</c:when>
      </c:choose>
</body>
</html>
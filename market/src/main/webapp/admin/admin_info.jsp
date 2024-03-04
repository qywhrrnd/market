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
			location.href = "/market/login_servlet/delete.do?userid="
					+ userid;
		}
	}
	function updateReport(userid,report_code) {
		if (confirm("판매 상태를 변경하시겠습니까?")){
			location.href = "/market/login_servlet/updateReport.do?userid=" + userid + "&report_code=" + report_code;
			
		}
	}
</script>
</head>
<body>
	<%@ include file="../admin/admin_menu.jsp"%>
	<div align="center">
		<form method="post" name="form1">
			<h2 align="center">회원 정보</h2>
			<!-- <input type="button" value="전체" onclick="">&nbsp; <input
				type="button" value="판매중" onclick="">&nbsp; <input
				type="button" value="판매완료" onclick=""><br> -->
	

			<table border="1" style="width: 100%">

				<tr align="center">
					<th style="width: 100px">회원 아이디</th>
					<th style="width: 100px">회원 이름</th>
					<th style="width: 100px">생년월일</th>
					<th style="width: 300px">주소</th>
					<th style="width: 100px">핸드폰번호</th>
					<th style="width: 100px">이메일</th>
					<th style="width: 100px">&nbsp;</th>
					<th style="width: 100px">회원상태</th>
				</tr>

				<c:forEach var="row" items="${dto}">
						<tr>
							<td align="center">${row.userid}</td>
							<td align="center">${row.name}</td>
							<td align="center">${row.birth}</td>
							<td align="center">${row.address}</td>
							<td align="center">${row.phone}</td>
							<td align="center">${row.email}</td>
							<td align="center">
										<select onchange="updateReport('${row.userid}', this.value)">
											<option value="선택">선택</option>
											<option value="0" ${row.report_type == '기본상태'? 'selected' : '' }>기본상태</option>
											<option value="1" ${row.report_type == '로그인제재'? 'selected' : '' }>로그인제재</option>
										</select>				
								</td>
							<td align="center">${row.report_type}</td>
								
							</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</body>
</html>
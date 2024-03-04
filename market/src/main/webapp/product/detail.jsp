<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<style>
.like-button {
	background-color: transparent;
	border: none;
	cursor: pointer;
	outline: none;
}

.hidden-button {
	visibility: hidden;
}

.like-icon {
	width: 32px;
	height: 32px;
	fill: none;
	stroke: red;
	stroke-width: 2;
	stroke-linecap: round; /* 선의 끝 부분을 둥글게 처리 */
	stroke-linejoin: round;
	stroke-linecap: round; /* 선이 교차하는 부분을 둥글게 처리 */
}

/* 하트가 선택되었을 때의 스타일 */
.like-selected .like-icon {
	fill: red; /* 선택된 경우에만 채워진 하트를 빨간색으로 변경 */
}
</style>
<script>
function openReport() {
	
	var userId = "${dto.userid}";
    var linkParam = encodeURIComponent("<%=request.getContextPath()%>/mk_servlet/detail.do?write_code=" + "${dto.write_code}");
    var url = "../report_servlet/report_detail.do?userid=" + userId + "&link=" + linkParam;
	
    // 팝업 창 크기 설정
    var popupWidth = 600;
    var popupHeight = 700;

    // 화면 가운데에 위치 계산
    var left = (window.innerWidth - popupWidth) / 2;
    var top = (window.innerHeight - popupHeight) / 2;

    // 작은 팝업 창을 열기 위한 코드
    window.open(url, '신고', 'width=' + popupWidth + ',height=' + popupHeight + ',left=' + left + ',top=' + top);
}
</script>
</head>
<body>
	<c:if test="${sessionScope.userid == 'admin'}">
		<%@ include file="../admin/admin_menu.jsp"%>
	</c:if>
	<c:if test="${sessionScope.userid != 'admin'}">
		<%@ include file="../main/menu.jsp"%>
	</c:if>
	<a><h2 align="center">상품정보</h2>
	<input type="button" class="report" value="삭제하기" onclick="openReport()"></a>
	<table align="center">
		<tr>
			<td><img src="/market/images/${dto.filename}" width="300px"
				height="300px"></td>

			<td align="center">
				<form name="form1" method="post">
					<table>
						<tr>
							<td>판매자</td>
							<td>${dto.userid}</td>
						</tr>

						<tr>
							<td>상품명</td>
							<td>${dto.subject}</td>
						</tr>

						<tr>
							<td>가격</td>
							<td>${dto.price}</td>
						</tr>

						<tr>
							<td><input type="hidden" name="userid" value="${dto.userid}">
								<input type="hidden" value="${dto.write_code}"> <c:choose>
									<c:when test="${sessionScope.userid == null}">
										<input type="hidden" class="chat" value="구매자와 채팅하기">
									</c:when>
									<c:when test="${sessionScope.userid == dto.userid}">
										<input type="hidden" class="chat" value="구매자와 채팅하기">
									</c:when>
									<c:when test="${sessionScope.userid != dto.userid}">
										<input type="button" class="chat" value="구매자와 채팅하기">
									</c:when>
								</c:choose></td>
							<td><c:choose>
									<c:when test="${sessionScope.userid == 'admin'}">
										<button class="hidden-button" id="noButton"></button>
									</c:when>
									<c:when test="${sessionScope.userid == null}">
										<button class="hidden-button" id="noButton"></button>
									</c:when>
									<c:otherwise>
										<button class="like-button" id="likeButton">
											<svg class="like-icon" viewBox="0 0 24 24">
									<path
													d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" />
								</svg>
									</c:otherwise>
								</c:choose></td>
						</tr>

						<tr>
							<td></td>
							<td colspan="2"><input type="hidden" name="link"
								value="<%= request.getContextPath() %>/mk_servlet/detail.do?write_code=${dto.write_code}">
								<c:choose>
									<c:when test="${sessionScope.userid == 'admin'}">
										<input type="hidden" class="report" value="신고하기">
									</c:when>
									<c:when test="${sessionScope.userid == null}">
										<input type="hidden" class="report" value="신고하기">
									</c:when>
									<c:when test="${sessionScope.userid == dto.userid}">
										<input type="hidden" class="report" value="신고하기">
									</c:when>
									<c:when test="${sessionScope.userid != dto.userid}">
										<input type="button" class="report" value="신고하기"
											onclick="openReport()">
									</c:when>
								</c:choose></td>
						</tr>
					</table>
				</form>

			</td>
		</tr>
	</table>
	<hr style="border: 2">
	<table align="center">
		<tr>
			<td>${dto.contents}</td>

		</tr>

	</table>

	<script>

	$(document).ready(function(){
	    $(".chat").click(function(){
	        var toid = "${dto.userid}";
	        location.href = "/market/chat_servlet/chat.do?toid=" + toid +"&userid="+"${sessionScope.userid}";   
	    });
	});
	

		const likeButton = document.getElementById("likeButton");
		
		
		likeButton.addEventListener("click", function() {
		    if (likeButton.classList.contains("like-selected")) {
		                $.ajax({
		                    url: "/market/love_servlet/love_clear.do",
		                    type: "GET",
		                    data: {
		                        write_code: ${dto.write_code},
		                        userid: "${sessionScope.userid}"
		                    },
		                    success: function () {
		                        likeButton.classList.remove("like-selected");
		                    },
		                });
		           
		    }else {
		    	      $.ajax({
	                    url: "/market/love_servlet/love_apply.do",
	                    type: "GET",
	                    data: {
	                        write_code: ${dto.write_code},
	                        userid: "${sessionScope.userid}"
	                    },
	                    success: function () {
	                        likeButton.classList.add("like-selected");
	                    },
		    	        });
		    	    }
		    	});
		
		
		if (${sessionScope.check} == 1) {
			likeButton.classList.add("like-selected");
		}
	</script>
</body>
</html>
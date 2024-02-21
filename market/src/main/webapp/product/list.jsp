<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고상품 목록</title>
<style>
body {
	text-align: center; /* 수평 가운데 정렬 */
}

.card-img {
	width: 200px;
	height: 200px;
}

.card-link {
	margin: 0 auto;
	width: 200px;
	height: 500px;
	margin-left: 30px;
	margin-right: 40px;
	float: left;
}

.card {
	margin: 0 auto; /* 수평 가운데 정렬 */
	width: 200px;
	height: 100px;
	background-color: lightblue;
	margin-bottom: 10px;
}

.card-title {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	font-size: 20px;
}

.card-content {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	font-size: 10px;
}

.card-mini {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	font-size: 10px;
}
</style>
<script>
function list(page){
	location.href="/market/mk_servlet/list.do?cur_page=" 
			+ page;
}
</script>
</head>
<body>
	<%@ include file="../main/menu.jsp"%>
	<h1 align="center">중고상품</h1>

	<table align="center">
		<c:forEach var="row" items="${list}" varStatus="status">
			<c:if test="${status.index % 3 == 0}">
				<tr>
					<!-- 3개의 항목을 담을 행 시작 -->
			</c:if>
			<td>
				<!-- 행 안의 카드 링크 시작 -->
				<article class="card-link">
					<a class="card"
						href="/market/mk_servlet/detail.do?write_code=${row.write_code}">
						<div>
							<img class="card-img" alt="${row.filename}"
								src="/market/images/${row.filename}" />
						</div>
						<div>
							<h2 class="card-title">${row.subject}</h2>
							<div class="card-content">${row.price}</div>
							<div class="card-content">전북 전주시 완산구 평화2동</div>
							<div class="card-mini">
								<span>관심 : ${dao.love_count(row.write_code)}</span> ∙ <span>조회수
									:${row.see} </span>
							</div>
						</div>
					</a>
				</article>
			</td>
			<!-- 행 안의 카드 링크 끝 -->
			<c:if test="${status.index % 3 == 2 or status.last}">
				</tr>
				<!-- 3개의 항목을 담은 행 끝 -->
			</c:if>
		</c:forEach>
		<tr>
			<td align="center" colspan="3">
			<c:if test="${page.curPage > 1}">
				<a href="#" onclick="list('1')">[처음]</a>
			</c:if>
			<c:if test="${page.curBlock > 1}">
				<a href="#" onclick="list('${page.prevPage}')">[이전]</a>
			</c:if>
			
			<c:forEach var="num" begin="${page.blockStart}" end="${page.blockEnd}">
				<c:choose>
					<c:when test="${num == page.curPage}">
						<span style="color:green">${num}</span>
					</c:when>
					<c:otherwise>
						<a href="#" onclick="list('${num}')">${num}</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${page.curBlock < page.totBlock}">
 				<a href="#" onclick="list('${page.nextPage}')">[다음]</a>
			</c:if>
			<c:if test="${page.curPage < page.totPage}">
 				<a href="#" onclick="list('${page.totPage}')">[마지막]</a>
			</c:if>
			</td>
		</tr>
	</table>
</body>
</html>

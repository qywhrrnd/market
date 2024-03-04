<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function list(page) {
		location.href = "/market/at_servlet/list.do?cur_page=" + page;
	}
</script>
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
	height: 350px;
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

.page_wrap {
	text-align: center;
	font-size: 10px;
	margin-bottom: 50px;
}

.page_nation {
	display: inline-block;
}

.page_nation .none {
	display: none;
}

.page_nation a {
	display: block;
	margin: 0 3px;
	float: left;
	border: 1px solid #e6e6e6;
	width: 28px;
	height: 28px;
	line-height: 28px;
	text-align: center;
	background-color: #fff;
	font-size: 13px;
	color: #999999;
	text-decoration: none;
}

.page_nation .arrow {
	border: 1px solid #ccc;
}

.page_nation .pprev {
	background: #f8f8f8 url('../images/page_pprev.png') no-repeat center
		center;
	margin-left: 0;
}

.page_nation .prev {
	background: #f8f8f8 url('../images/page_prev.png') no-repeat center
		center;
	margin-right: 7px;
}

.page_nation .next {
	background: #f8f8f8 url('../images/page_next.png') no-repeat center
		center;
	margin-left: 7px;
}

.page_nation .nnext {
	background: #f8f8f8 url('../images/page_nnext.png') no-repeat center
		center;
	margin-right: 0;
}

.page_nation a.active {
	background-color: #42454c;
	color: #fff;
	border: 1px solid #42454c;
}
</style>
</head>
<body>
	<c:if test="${sessionScope.userid == 'admin'}">
		<%@ include file="../admin/admin_menu.jsp"%>
	</c:if>
	<c:if test="${sessionScope.userid != 'admin'}">
		<%@ include file="../main/menu.jsp"%>
	</c:if>
	<h1 align="center">경매상품</h1>

	<div style="display: flex; flex-direction: column;">
		<!-- 컬럼을 세로로 배치하는 flexbox를 사용합니다. -->
		<div style="margin-bottom: 10px;">
			<!-- 첫 번째 컬럼 -->
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
								href="/market/at_servlet/detail.do?auction_code=${row.auction_code}">
								<div>
									<img class="card-img" alt="${row.filename}"
										src="/market/images/${row.filename}" />
								</div>
								<div>
									<h2 class="card-title">${row.subject}</h2>
									<div class="card-content">가격:${row.price}원</div>
									<div class="card-content">${mdao.address(row.userid)}</div>
									<div class="card-mini"></div>
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
			</table>
		</div>
		<div style="margin-bottom: 100px;">
			<!-- 페이지네이션을 위로 조금 올립니다. -->
			<table align="center">
				<tr class="page_wrap" align="center">
					<td class="page_nation" align="center"><c:if
							test="${page.curPage > 1}">
							<a class="arrow pprev" href="#" onclick="list('1')"></a>
						</c:if> <c:if test="${page.curBlock > 1}">
							<a class="arrow prev" href="#" onclick="list('${page.prevPage}')"></a>
						</c:if> <c:forEach var="num" begin="${page.blockStart}"
							end="${page.blockEnd}">
							<c:choose>
								<c:when test="${num == page.curPage}">
									<a style="color: green" class="active">${num}</a>
								</c:when>
								<c:otherwise>
									<a href="#" onclick="list('${num}')">${num}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach> <c:if test="${page.curBlock < page.totBlock}">
							<a class="arrow next" href="#" onclick="list('${page.nextPage}')"></a>
						</c:if> <c:if test="${page.curPage < page.totPage}">
							<a class="arrow nnext" href="#" onclick="list('${page.totPage}')"></a>
						</c:if></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
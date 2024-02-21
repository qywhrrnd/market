<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>


<div style="text-align: right;">
	<c:choose>
		<c:when test="${sessionScope.userid == null }">
			<a href="/market/login/login.jsp">로그인</a>&nbsp;&nbsp;&nbsp;&nbsp;
</c:when>
		<c:otherwise>
${sessionScope.nickname}님이 로그인중입니다.
<a href="/market/login_servlet/logout.do">로그아웃</a>
		</c:otherwise>
	</c:choose>
</div>

<meta charset="UTF-8">
<link rel="stylesheet" href="/market/include/css/bootstrap.css">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/market/include/js/bootstrap.js"></script>
<script>
	$(function() {
		$("#a").click(function() {
			location.href = "/market/mk_servlet/list.do";
		});
	});
</script>
<title>Market Main</title>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="../main/main.jsp">Market</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item"><a class="nav-link" id="a"
					aria-current="page" href="#">중고거래</a></li>
				<li class="nav-item"><a class="nav-link"
					href="../board_servlet/list.do">게시판</a></li>
				<li class="nav-item"><a class="nav-link"
					href="../at_servlet/list.do">경매 게시판</a></li>
				<c:choose>
					<c:when test="${sessionScope.userid != null}">
						<li class="nav-item"><a class="nav-link"
							href="/market/product/write.jsp">글쓰기</a></li>
					</c:when>
					<c:otherwise>
						<script>
							function showAlert() {
								alert("로그인이 필요합니다.");
								window.location.href = '/market/login/login.jsp';
							}
						</script>
						<li class="nav-item"><a class="nav-link" href="#"
							onclick="showAlert()">글쓰기</a></li>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${sessionScope.userid != null}">
						<!-- 아이디가 있으면 -->
						<li class="nav-item"><a class="nav-link"
							href="/market/auction/auction_write.jsp">경매 글쓰기</a></li>
					</c:when>
					<c:otherwise>
						<!-- 다른경우? 아이디가 없으면 -->
						<li class="nav-item"><a class="nav-link" href="#"
							onclick="showAlert()">경매 글쓰기</a> <!-- showAlert로 가라! --></li>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${sessionScope.userid != null}">
						<li class="nav-item"><a class="nav-link"
							href="/market/main/information.jsp">내정보</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class="nav-link" href="#"
							onclick="showAlert()">내정보</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<form class="d-flex" role="search">
				<input class="form-control me-2" type="search"
					placeholder="물품이나 지역을 검색하세요." aria-label="Search">
				<button class="btn btn-outline-success" type="submit">검&nbsp;색</button>
			</form>
		</div>
	</div>
</nav>
<style>
form {
	width: 28.8rem;
	box-sizing: border-box;
	height: 4rem;
	padding: 0.9rem 1.2rem;
	border: none;
	border-radius: 0.6rem;
}

#home-main-first {
	width: 100%;
	height: 50%;
	float: right;
}

#home-main-second {
	width: 100%;
	height: 50%;
}
</style>
</head>

<hr>
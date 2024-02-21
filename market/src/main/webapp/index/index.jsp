<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 필요한 css,js include폴더에서 불러옴 -->
<link rel="stylesheet" href="/market/include/css/bootstrap.css">
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/market/include/js/bootstrap.js"></script>

<title>Market Main</title>

<!-- 헤더쪽 style -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Market</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="#">중고거래</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">게시판</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">로그인</a>
        </li>
        </ul>
        <form class="d-flex" role="search">
        <input class="form-control me-2" type="search" placeholder="물품이나 지역을 검색하세요." aria-label="Search">
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
.small-text {
        font-size: 16px; /* 작은 텍스트의 글씨 크기 */
}

.large-text {
        font-size: 24px; /* 큰 텍스트의 글씨 크기 */
}
</style>
</head>
<body>

<main class="overflow-auto">
	<div id="home-main-first" style="display: flex; align-item: center;" >
		<div>
		<p class="fw-bold">중고거래
		<h1 >
			당신 근처의
			<br>
			지역 생활 커뮤니티
		</h1>
		<img src="/market/images/gazi.png" style="max-width: 100%; margin-left: 10%;"/>
		</div>
	</div>

	<div id="home-main-second" class="float-end"  style="display: block;">
		<h1>
			믿을 만한
			<br>
			이웃 간 중고거래
		</h1>
	</div>
	<div style="display: block;">
		<h1>
			동네 주민들과 따뜻한 경험을
			<br>
			느껴보세요.
		</h1>
	</div>

</main>
</body>

</html>
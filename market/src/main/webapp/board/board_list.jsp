<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>
$(function() {	
	$("#btnWrite").click(function() {
		if("${sessionScope.userid}" != ""){
		location.href="/market/board/board_write.jsp";
		}else{
			alert("로그인 ㄱㄱ");
		location.href="/market/login/login.jsp";
		}
	});
});
function list(page){
	location.href="/market/board_servlet/list.do?cur_page=" 
			+ page + "&search_option=${search_option}&keyword=${keyword}";
}	

$(function() {
	$(".article-row").click(function() {
		let articleId = $(this).attr("data-id");
		
		location.href = "/market/board_servlet/view.do?num=" + articleId;
	});
});
</script>
<style>
.board-list {
	margin: 30px auto;
	width: 100%;
	table-layout: fixed;
}
 
.size01 { 
	width:9%;
}

.size02 {
	width: 30%;
}

.size03 {
	width: 15%;
}

.size04 {
	width: 7%;
}
</style>
</head>
<body>
<h2>게시판</h2>
<form name="form1" method="post" action="/market/board_servlet/search.do">
<select name="search_option">
<c:choose>
	<c:when test="${search_option == null || search_option == 'all'}">
		<option value="all" selected>전체검색</option>
		<option value="nickname" >작성자</option>
		<option value="subject" >제목</option>
		<option value="content" >내용</option>
	</c:when>
	<c:when test="${search_option == 'nickname'}">
		<option value="all" >전체검색</option>
		<option value="nickname" selected>작성자</option>
		<option value="subject" >제목</option>
		<option value="content" >내용</option>
	</c:when>
	<c:when test="${search_option == 'subject'}">
		<option value="all" >전체검색</option>
		<option value="nickname" >작성자</option>
		<option value="subject" selected>제목</option>
		<option value="content" >내용</option>
	</c:when>
	<c:when test="${search_option == 'content'}">
		<option value="all" >전체검색</option>
		<option value="nickname" >작성자</option>
		<option value="subject" >제목</option>
		<option value="content" selected>내용</option>
	</c:when>
</c:choose>
</select>
<input name="keyword" value="${keyword}">
<input type="submit" value="검색" id="btnSearch"> 
<button type="button" id="btnWrite">게시글 작성</button>
</form>
<article>	
	<table class="board-list">
		<tr align="center">
			<th class="size04">번호</th>
			<th class="size01">작성자</th>
			<th class="size02">제목</th>
			<th class="size03">날짜</th>
			<th class="size03">조회수</th>
		</tr>
		<c:forEach var="dto" items="${list}" varStatus="s">
			<tr align="center" class="article-row" data-id="${dto.num}">
				<td>${(page.totalCount - dto.rn) + 1}</td>  
				<td>${dto.nickname}</td>
				<td>${dto.subject}</td>
				<td>${dto.reg_date}</td>
				<td>${dto.hit}</td>
			</tr>
		</c:forEach>
		<tr align="center">
			<td align="center" colspan="5">
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
</article>
</body>
</html>
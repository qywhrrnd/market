<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>내물건 팔기</title>
  <script src="http://code.jquery.com/jquery-3.6.1.js"></script>
  <style>
    table {
      margin: 0 auto;
    }
  </style>
  <script>
    function product_write() {
      let form1 = $("#form1");
      let subject = $("#subject").val();
      let contents = $("#contents").val();
      let price = $("#price").val();

      if (subject == "") {
        alert("제목을 입력하세요");
        $("#subject").focus();
        return;
      }
      if (price == "") {
        alert("금액을 입력하세요");
        $("#price").focus();
        return;
      }
      if (contents == "") {
        alert("설명을 입력하세요");
        $("#contents").focus();
        return;
      }
      form1.attr("action", "/market/mk_servlet/insert_product.do");
      form1.submit();
    }
  </script>
</head>
<body>
<%@ include file="../main/menu.jsp"%>
  <h2 align="center">내물건 팔기</h2>
  <hr style="text-align: left; margin-left: 0">
  <form id="form1" method="post" enctype="multipart/form-data">
    <table>
			<tr>
				<th>제목</th>
			</tr>
			<tr>
				<td><input type="text" name="subject" placeholder="상품명(제목)"></td>
			</tr>
			<tr>
				<th>상품가격</th>
			</tr>
			<tr>
				<td><input type="number" name="price" placeholder="상품가격"></td>
			</tr>
			<tr>
				<th>상품이미지</th>
			</tr>
			<tr>
				<td><input type="file" name="file1"></td>
			</tr>
			<tr>
				<th>상품설명</th>
			</tr>
			<tr>
				<td><textarea rows="5" cols="80" id="contents" name="contents" style="resize: none; height: 300px"
				placeholder="-상품명(브랜드)&#13;&#10;&#13;&#10;-구매시기&#13;&#10;&#13;&#10;-사용기간&#13;&#10;&#13;&#10;-하자여부&#13;&#10;&#13;&#10;*실제 촬영한 사진과 함께 상세 정보를 입력해주세요."></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="등록" onclick="product_write()">
					<input type="button" value="취소" onclick="location.href='/market/mk_servlet/list.do'">
				</td>
			</tr>
		</table>
  </form>
</body>
</html>

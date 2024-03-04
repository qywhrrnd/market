<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
  <style>
    table {
      margin: 0 auto;
    }
  </style>
  <script>
    function report_detail() {
      let form1 = $("#form1");
      let subject = $("#subject").val();
      let contents = $("#contents").val();
      let userid = $("#userid").val();
      let link = $("#link").val();
      let reporter = $("#reporter").val();

      if (subject == "") {
        alert("제목을 입력하세요");
        $("#subject").focus();
        return;
      }
      if (contents == "") {
        alert("설명을 입력하세요");
        $("#contents").focus();
        return;
      }
      alert("신고되었습니다");
      form1.attr("action", "/market/report_servlet/report.do");
      form1.submit();
    }
  </script>
</head>
<body>
  <h2 align="center">신고하기</h2>
<form id="form1" method="post">
<table>
			<tr>
				<th>제목</th>
			</tr>
			<tr>
				<td align="center"><input type="text" id="subject" name="subject" placeholder="신고(제목)"></td>
			</tr>
			<tr>
				<th>신고할사람</th>
			</tr>
			<tr>
				<td align="center"><input type="text" id="userid" name="userid" value="${userid}" readonly style="border: none; text-align: center;"></td>
			</tr>
			<tr>
				<th>신고내용</th>
			</tr>
			<tr>
				<td><textarea rows="5" cols="80" id="contents" name="contents" style="resize: none; height: 300px"
				placeholder="-상품명(브랜드)&#13;&#10;&#13;&#10;-구매시기&#13;&#10;&#13;&#10;-사용기간&#13;&#10;&#13;&#10;-하자여부&#13;&#10;&#13;&#10;*실제 촬영한 사진과 함께 상세 정보를 입력해주세요."></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="hidden" value="${sessionScope.userid}" name="reporter" id="reporter">
								<input type="hidden" name="link" id="link" value="${link}">
				
					<input type="submit" value="등록" onclick="report_detail()">
				</td>
			</tr>
		</table>
</form>
</body>
</html>
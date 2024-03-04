<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>

$(document).ready(function(){
    $(".chat").click(function(){
        var toid = "${dto.userid}";
        location.href = "/market/chat_servlet/chat.do?toid=" + toid +"&userid="+"${sessionScope.userid}";   
    });
});
</script>

</head>
<body>
	<c:if test="${sessionScope.userid == 'admin'}">
		<%@ include file="../admin/admin_menu.jsp"%>
	</c:if>
	<c:if test="${sessionScope.userid != 'admin'}">
		<%@ include file="../main/menu.jsp"%>
	</c:if>
	<h2 align="center">경매상품정보</h2>
	<table align="center">
		<tr>
			<td><img src="/market/images/${dto.filename}" width="300px"
				height="300px"></td>
			<td align="center">

				<table>
					<tr>
						<td>상품명</td>
						<td>${dto.subject}</td>
					</tr>

					<tr>
						<td>가격</td>
						<td id="price">${dto.price}</td>
					</tr>
					<tr>
						<td>현재입찰자</td>
						<td id="bid2userid">${dto.biduserid}</td>
					</tr>
					<tr>
						<td>입찰가 : <input type="number" min="${dto.price}" id="bidnum"></td>
						<td><input type="hidden" id="bid1userid"
							style="background-color: transparent; border: 0px"
							value="${sessionScope.userid}" readonly></td>
					</tr>

					<tr>
						<td colspan="2">
							<form name="form1" method="post"
								action="/market/at_servlet/bid.do">
								<input type="hidden" name="userid" value="${dto.userid}">
								<input type="hidden" name="auction_code"
									value="${dto.auction_code}"> <input type="button"
									id="btnbid" value="입찰">
								<c:if test="${sessionScope.userid == dto.userid}">
									<input type="hidden" class="chat" id="btnchat" value="채팅">
								</c:if>
								<c:if test="${sessionScope.userid != dto.userid}">
									<input type="button" class="chat" id="btnchat" value="채팅"
										disabled="disabled">
								</c:if>
								<input type="button" value="새로고침"
									onclick="location.href='/market/at_servlet/detail.do?auction_code=${dto.auction_code}'">
							</form>
						</td>
					</tr>
					<tr>
						<td>
							<div id="demo"></div>
						</td>
					</tr>
				</table>
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
// 가격과 현재 입찰자 정보를 업데이트하는 함수
function updatePriceAndBidder() {
    $.ajax({
        url: "/market/at_servlet/updatePriceAndBidder.do",
        type: "GET",
        data: {
            auction_code: "${dto.auction_code}"
        },
        success: function(response) {
            // 응답에서 새로운 가격과 입찰자 정보를 가져와 업데이트합니다.
            $("#price").text(response.price);
            $("#bid2userid").text(response.bidder);
            var seconds = response.time;
            var minutes = Math.floor(seconds / 60);
            var remainingSeconds = seconds % 60;
            $("#demo").text(minutes + "분 " + remainingSeconds + "초");
                if ($("#demo").text() === "0분 0초") {
                var biduserid = "${dto.biduserid}";
                $("#btnbid").prop("disabled", true);
                $("#demo").text("시간초과");
                if ("${sessionScope.userid}" === biduserid) {
                    $("#btnchat").prop("disabled", false);
                }
            }
            
        },
    });
}

// 일정한 시간 간격으로 업데이트 함수 호출
var updateInterval = setInterval(updatePriceAndBidder, 1000); // 5초마다 업데이트

// 페이지를 닫을 때 업데이트 간격을 멈추도록 합니다.
$(window).on('beforeunload', function(){
    clearInterval(updateInterval);
});





const btnbid = document.getElementById("btnbid");

btnbid.addEventListener("click", function() {
    // 사용자가 입력한 입찰가를 가져옵니다.
    var bidPrice = $("#bidnum").val();
    var bid1Userid = $("#bid1userid").val();
    
    // AJAX를 사용하여 현재 상품의 가격을 업데이트하고, 비교를 수행합니다.
    $.ajax({
        url: "/market/at_servlet/updatePriceAndBidder.do",
        type: "GET",
        data: {
            auction_code: "${dto.auction_code}"
        },
        success: function(response) {
            // AJAX 요청에서 받은 최신 가격을 가져와 비교합니다.
            var currentPrice = response.price;
            
            // 사용자가 입력한 입찰가와 현재 상품의 가격을 비교합니다.
            if (bidPrice <= parseInt(currentPrice)) {
                alert("기존 가격보다 높은 가격을 입력해주세요");
                return;
            } else {
                // AJAX 요청의 데이터로 사용자가 입력한 입찰가를 전달합니다.
                $.ajax({
                    url: "/market/at_servlet/bid.do",
                    type: "GET",
                    data: {
                        bidPrice: bidPrice,
                        auction_code: ${dto.auction_code},
                        biduserid: bid1Userid
                    },
                    success: function(response) {
                        // AJAX 요청에 대한 응답에서 새로운 입찰자 정보를 가져와 업데이트합니다.
                        $("#price").text(bidPrice);
                        $("#bid2userid").text(bid1Userid);
                        $("#bidnum").val('');
                    },
                });
            }
        },
    });
});
</script>





</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
<script>
var time = ${dto.time};
var min = "";
var sec = "";

var x = setInterval(function(){
   min = parseInt(time/60);
   sec = time%60;
   
   document.getElementById("demo").innerHTML = min + "분" + sec + "초";
   time--;
   
   if(time < 0) {
      clearInterval(x);
      document.getElementById("demo").innerHTML = "시간초과";
      $("#btnbid").prop("disabled", true);
   }
}, 1000);
</script>
</head>
<body>
	<%@ include file="../main/menu.jsp"%>
	<h2 align="center">상품정보</h2>
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
						<td>입찰자 : <input type="text" id="bid1userid"
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
									id="btnbid" value="입찰"> <input type="button"
									value="새로고침"
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
        },
    });
}

// 일정한 시간 간격으로 업데이트 함수 호출
var updateInterval = setInterval(updatePriceAndBidder, 5000); // 5초마다 업데이트

// 페이지를 닫을 때 업데이트 간격을 멈추도록 합니다.
$(window).on('beforeunload', function(){
    clearInterval(updateInterval);
});
</script>

	<script>
	const btnbid = document.getElementById("btnbid");

	btnbid.addEventListener("click", function() {
	    // 사용자가 입력한 입찰가를 가져옵니다.
	    var bidPrice = $("#bidnum").val();
	    var bid1Userid = $("#bid1userid").val();
	    
	    // 사용자가 입력한 입찰가와 상품의 가격을 비교합니다.
	    if (bidPrice <= parseInt("${dto.price}")) {
	        alert("기존 가격보다 높은 가격을 입력해주세요");
	        return;
	    } else {
	        // AJAX 요청의 데이터로 사용자가 입력한 입찰가를 전달합니다.
	        $.ajax({
	            url: "/market/at_servlet/bid.do",
	            type: "GET",
	            data: {
	                bidPrice: bidPrice,
	                auction_code : ${dto.auction_code},
	            	biduserid : bid1Userid
	            },
	            success: function (response) {
	                // AJAX 요청에 대한 응답에서 새로운 입찰자 정보를 가져와 업데이트합니다.
	                $("#price").text(bidPrice);
	                $("#bid2userid").text(bid1Userid);
	                $("#bidnum").val('');
	            },
	        });
	    }
	});
</script>





</body>
</html>
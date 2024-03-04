<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="../main/menu.jsp"%>

<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
// 돌아가기 버튼 클릭시!
function btnList() {
	location.href = "/market/board_servlet/list.do";
}

// DOMContentLoaded를 이용해서 현재 문서로드시 btnEdit 실행 유무
document.addEventListener('DOMContentLoaded', function() {
    let nowUserId = "${sessionScope.userid}";
    let postUserId = "${dto.userid}";
    let btnEdit = $("#btnEdit");

    if (nowUserId === postUserId) {
        btnEdit.prop("disabled", false).show();
        btnEdit.click(function() {
            let num = $("#num").val();
            location.href = "/market/board_servlet/edit.do?num=" + num;
        });
    } else {
        btnEdit.prop("disabled", true).hide();
    }
});

function btnWrite() { //댓글 작성
	let num = $("#num").val();
	let userid = '${sessionScope.userid}';
	let comment_content = $("#comment_content").val();
	
	$.ajax({
		url:'/market/comment_servlet/write.do',
		type:'GET',
		data:{
			"num":num, 
        	"userid":userid, 
        	"comment_content":comment_content, 
		},
		dataType: 'json',
		success: function(data) {
            // 성공적으로 댓글을 작성하면 댓글 목록을 다시 불러와서 업데이트
            show_List();
        },
        error: function(xhr, status, error) {
            console.error('댓글 작성에 실패했습니다:', status, error);
        }
    });
	comment_content = $("#comment_content").val("");
}

function show_List() {
    let num = '${dto.num}';
    let userid = '${sessionScope.userid}';
    let comment_content = '${comment.comment_content}';
    let comment_date = '${comment.comment_date}';
    
    $.ajax({
        url: '/market/comment_servlet/list.do', // 댓글 목록 서블릿
        type: 'GET',
        data:{
            "num": num, 
            "userid": userid, 
            "comment_content": comment_content, 
            "comment_date": comment_date 
        },
        dataType: 'json',
        success: function(data) { // data는 서버로부터 받은 JSON
            $('#commentList').empty();
            let row = "";
            if (data.length < 1) {
                row += '<div class="container" id="commentList">';
                row += '<ul class="list-group">';
                row += '<li class="list-group-item">등록된 댓글이 없습니다.</li>';
                row += '</ul>';
                row += '</div>';
            } else {
                row += '<div class="container" id="commentList">';
                row += '<ul class="list-group">';
                data.forEach(function(comment) { // 댓글 하나씩 추가하기
                    row += '<li class="list-group-item">';
                    row += '<strong>아이디:</strong> ' + comment.userid + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
                    row += '<span style="font-size: 12px">' + comment.comment_date + '</span>'  + '<br>';
                    row += '<br>';
                    row += '<strong>내용:</strong> ' + comment.comment_content;
                    
                    // 사용자와 댓글 작성자가 동일하면 수정/삭제 버튼 추가
                    if (comment.userid == '${sessionScope.userid}') {
                        row += '<div style="font-size: 12px;">';
                        row += '<button style="color: gray; background-color: transparent; border : 0px;" onclick="update_comment(' + comment.comment_num + ')">수정</button>';
                        row += '<button style="color: gray; background-color: transparent; border : 0px;" onclick="delete_comment(' + comment.comment_num + ')">삭제</button>';
                        row += '</div>';
                    }
                    row += '</li>';
                });
                row += '</ul>';
                row += '</div>';
            }
            // 댓글 목록을 추가
            $('#commentList').append(row);
        },
        error: function(xhr, status, error) {
            console.error('댓글 목록을 가져오는데 실패했습니다:', status, error);
        }
    });
}






$(document).ready(function() {
    show_List(); //board_view 켜지면 자동로드됙
});

function update_comment() {
	
}

function delete_comment(comment_num) {
    let num = $("#num").val();
    let userid = '${sessionScope.userid}';
    if (confirm("정말로 삭제하시겠습니까?")) {
        $.ajax({
            url: '/market/comment_servlet/delete.do',
            type: 'GET',
            data: {
                "userid": userid,
                "comment_num": comment_num,
                "num": num
            },
            dataType: 'json',
            success: function(data) {
                // 댓글 삭제 후 댓글 목록 다시 로드
                show_List();
            },
            error: function(xhr, status, error) {
                console.error('댓글 삭제에 실패했습니다:', status, error);
            }
        });
    }
}

</script>
<style>
.center-container {
    display: flex;
    justify-content: center;
    width: 100%; /* 전체 너비를 화면에 맞추기 위해 추가 */
    max-width: 800px; /* 최대 너비를 설정하여 화면이 너무 커지지 않도록 제한 */
    margin: 0 auto; /* 가운데 정렬을 위해 추가 */
    box-sizing: border-box; /* 너비와 패딩, 보더를 함께 계산하도록 설정 */
}

article {
    width: 100%; /* 전체 너비를 화면에 맞추기 위해 추가 */
    max-width: 800px; /* 최대 너비를 설정하여 화면이 너무 커지지 않도록 제한 */
    padding: 20px; /* 내용과 가장자리 사이의 간격을 추가 */
    box-sizing: border-box; /* 너비와 패딩, 보더를 함께 계산하도록 설정 */
}

</style>
</head>


<body>
<div class="center-container">
<article>
<table class="board-list" width="100%">
    <tr>
        <th width="20%">날짜:</th>
        <td width="40%">${dto.reg_date}</td>
        <th width="20%">조회수:</th>
        <td width="20%">${dto.hit}</td>
    </tr>
    <tr>
        <td colspan="4"><hr></td>
    </tr>
    <tr>
        <th width="100%">닉네임:</th>
        <td colspan="3">${dto.nickname}</td>
    </tr>
    <tr>
        <th>제목:</th>
        <td colspan="3">${dto.subject}</td>
    </tr>
    <tr>
        <th>본문:</th>
        <td colspan="3"><textarea rows="5" cols="50" readonly>${dto.content}</textarea></td>
    </tr>
    <tr>
        <td colspan="4" align="center">
            <input type="hidden" name="num" id="num" value="${dto.num}">
            <button id="btnEdit">수정/삭제</button>
            <input type="button" value="돌아가기" onclick="btnList()">
        </td>
    </tr>
</table>
</article>
</div>

<!-- 댓글목록 -->
<div class="container" id="commentList">
    <div class="row">
        <ul class="list-group">
            <li class="list-group-item active">댓글</li>
            <c:forEach var="comment" items="${list}">
                <li class="list-group-item">
                    <input type="hidden" value="${comment.num}">
                    <input type="hidden" id="comment_num" value="${comment.comment_num}">
                    <strong>아이디:</strong> ${comment.userid}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="font-size: 12px">${comment.comment_date}</span><br>
                    <strong>내용:</strong> ${comment.comment_content}
                    <c:if test="${sessionScope.userid != null && comment.userid eq sessionScope.userid}">
                        <div style="font-size: 12px;">
                            <a style="color: gray;" href="#" onclick="delete_comment('${comment.comment_num}')">수정</a>
                            <a style="color: gray;" href="#" onclick="delete_comment('${comment.comment_num}')">삭제</a>
                        </div>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>



<!-- 여기에 댓글 수정 창 띄우기 -->
<div style="visibility: hidden;" id="">
</div>


<!-- 댓글쓰기 -->
<br>

<div class="container">
    <div class="row">
            <table class="table table-bordered" style="text-align: center; border: 1px solid #dddddd">
                    <tr>
                        <td align="left">${userid}</td>
                    </tr>
                    <tr>
                        <td><textarea class="form-control" placeholder="댓글 쓰기" id="comment_content" style="width:100%;"></textarea></td>
                    </tr>
            </table>
            <input type="button" onclick="btnWrite()" class="btn btn-success pull-right" value="댓글 쓰기">
    </div>
</div>
</body>
</html>
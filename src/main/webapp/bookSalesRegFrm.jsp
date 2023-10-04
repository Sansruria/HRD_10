 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>		
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>
	<form name="salesRegFrm">

		<table border="1">
			<tr>
				<td>전표번호</td>
				<td><input type="text" id="전표번호" name="saleNo" value="${newSaleNo}"></td>
			</tr>
			<tr>
				<td>판매일자</td>
				<td><input type="text" id="판매일자" name="saleDate">(예)20191231</td>
			</tr>
			<tr>
				<td>도서코드</td>
				<td><select id="도서코드" name="bCode">
						<option value="도서선택">도서선택</option>
						<option value="AA01">[AA01]컴퓨터개론</option>
						<option value="AA02">[AA02]시스템분석및설계</option>
						<option value="AA03">[AA03]데이터베이스</option>
						<option value="AA04">[AA04]JAVA</option>
						<option value="AA05">[AA05]HTML5</option>
						<option value="AA06">[AA06]C 언어</option>
						<option value="AA07">[AA07]JSP 프로그래밍</option>
						<option value="AA08">[AA08]리눅스</option>
						<option value="AA09">[AA09]소프트웨어공학</option>
						<option value="AA10">[AA10]모바일앱입문</option>
				</select></td>
			</tr>
			<tr>
				<td>판매수량</td>
				<td><input type="text" id="판매수량" name="aMount"></td>
			</tr>
			<tr>
				<td>전표승인</td>
				<!-- 라디오버튼은 checked하여 유효성검사 회피할것.-->
				<td><input type="radio" class="approved" name="approved" value="Y" checked>승인
					<input type="radio" class="approved" name="approved" value="N">미승인</td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="button" value="매출등록" onclick="forward(this)" >
				<input type="button" value="다시쓰기" onclick="forward(this)" >
				</td>
			</tr>
		</table>

	</form>

<!--인사관리 시스템 버튼 구현  -->
<!-- <a href="byBookSelect"><button>도서별</button> </a> -->

<form name="btnFrm">
	<button value="book" onclick="go(this)">도서별</button>
	<button value="com" onclick="go(this)">출판사별</button>
</form> -->

	<jsp:include page="footer.jsp"></jsp:include>
	
<script>
function forward(btn){
	let frm=document.salesRegFrm;
	console.log('frm='+frm);
	let length=frm.length-4;
	console.log(length);
	//console.log(frm[4], frm[4].value)  //라디오 버튼 value 기본값: "on"
	//for문 없이 if문 여러번도 가능
	if(btn.value=="매출등록"){
		for(var i=0; i<length; i++){
			if(frm[i].value=="" || frm[i].value==null || frm[i].value=="도서선택"){
				alert(frm[i].id+" 입력되지 않았습니다!");
				return false;
			}//if end
		}//for end
		
		//radion button은 checked속성으로 에러메세지 생략할것.
		/* let radioBtns=document.querySelectorAll(".approved");
		console.log(radioBtns);
		for(let rBtn of radioBtns){
			if(rBtn.checked==true){
				form.action="bookSalesInsert";
				form.submit();
				return true;
			}	
		} 
		alert("전표승인이 입력되지 않았습니다!!")
		*/
		frm.action="bookSalesInsert";   //url
	}else if(btn.value=="다시쓰기"){
		alert("정보를 지우고 처음부터 다시 입력 합니다!");
		form.reset();
		return false;
	}
	//미리 성공 메시지 처리해도 됨
	alert("매출전표가 정상적으로 등록되었습니다!");
	form.submit();
}
//위에서 미리 성공메세지 출력했다면 생략가능 
/* let m='${msg}';  //꼭 콤마할것.
if(m!=''){
	alert("msg:"+m)
} */
//인사 관리 버튼
/* function go(btn){
	let frm=document.btnFrm;
	if(btn.value=="book"){
		frm.action="byBookSelect";
	}
	if(btn.value=="com"){
		frm.action="byComSelect";
	}
	frm.submit();
} */
</script>	
</body>

</html>
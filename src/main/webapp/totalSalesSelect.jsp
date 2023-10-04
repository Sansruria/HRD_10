<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
//사전에 성공메세지 출력했다면 생략가능 
/* let m='${msg}';  //꼭 콤마할것.
if(m!=''){
	alert("msg:"+m)
} */
</script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="nav.jsp"></jsp:include>

<%-- ${makeBsList} --%>

<table border='1'>
	<tr>
		<th>전표번호</th><th>출판사</th><th>판매일자</th><th>도서코드</th>
		<th>도서명</th><th>판매수량</th><th>매출액</th><th>전표승인</th>
	</tr>
<c:forEach var="book" items="${bsList}">
	<tr>
		<td>${book.saleNo}</td>
		<td>${book.comCode}-${book.comName}</td>
		<td>${book.saleDate}</td>
		<td>${book.bCode}</td>
		<td>${book.bName}</td>
		<td>${book.amount}</td>
		<td>${book.price}</td>
		<td>${book.approved}</td>
	</tr>
</c:forEach>
</table>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>










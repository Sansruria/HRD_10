
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="nav.jsp"></jsp:include>

	<table border='1'>
		<tr>
			<th>도서 코드</th>
			<th>도서명</th>
			<th>총매출액</th>
		</tr>
		<c:forEach var="book" items="${bookList}">
			<tr>
				<td>${book.bCode}</td>
				<td>${book.bName}</td>
				<td>${book.price}</td>
			</tr>
		</c:forEach>
	</table>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
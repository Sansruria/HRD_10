
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

	<%-- ${makeCList} --%>
	<table border="1">
		<tr>
			<th>출판사 코드</th>
			<th>출판사 명</th>
			<th>총매출액</th>
		</tr>
		<c:forEach var="com" items="${comList}">
			<tr>
				<td><a href="comUpdateFrm?comCode=${com.comCode}">
						${com.comCode}</a></td>
				<td>${com.comName}</td>
				<td>${com.price}</td>
			</tr>
		</c:forEach>
	</table>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
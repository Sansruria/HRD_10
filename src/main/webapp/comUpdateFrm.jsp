<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>출판사 정보</h1>
	<form action="comUpdate">
		<table border="1">
			<tr>
				<th>출판사 코드</th>
				<th>출판사 이름</th>
			</tr>
			<tr>
				<td><input type="text" name="comCode" value="${com.comCode}"
					readonly></td>  <!-- 출판사코드는 pk이므로  변경금지 -->
				<td><input type="text" name="comName" value="${com.comName}"></td>
			</tr>
		</table>
		<button>수정</button>
	</form>
</body>
</html>
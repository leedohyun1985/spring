<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Users</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/fontawesome-free/5.14.0/css/all.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/bootstrap-table-master/dist/bootstrap-table.min.css">

</head>
<body>
	<h1>Admin Hello world!</h1>

	<sec:authorize access="isAnonymous()">
		<form action="${pageContext.request.contextPath}/signin" method="GET">

			<button type="submit">LOGIN</button>
		</form>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<form action="${pageContext.request.contextPath}/signout"
			method="POST">
			<sec:csrfInput />
			<button type="submit">LOGOUT</button>
		</form>
	</sec:authorize>

	<P>The time on the server is ${serverTime}.</P>

	<table data-toggle="table">
		<thead>
			<tr>
				<th>Item ID</th>
				<th>Item Name</th>
				<th>Item Price</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>Item 1</td>
				<td>$1</td>
			</tr>
			<tr>
				<td>2</td>
				<td>Item 2</td>
				<td>$2</td>
			</tr>
		</tbody>
	</table>
	<script
		src="${pageContext.request.contextPath}/resources/jQuery/3.5.1/jquery-3.5.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/popper.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/js/bootstrap.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap-table-master/dist/bootstrap-table.min.js"></script>
	<!-- Latest compiled and minified Locales -->
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap-table-master/dist/locale/bootstrap-table-zh-CN.min.js"></script>
</body>
</html>

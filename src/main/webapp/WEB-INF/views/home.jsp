<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i"%>

<html>
<head>
<title>Home</title>
</head>
<body>
	<h1><i:message code="main.hello_world" />!</h1>

	<form action="${pageContext.request.contextPath}/signup" method="GET">
		<sec:csrfInput />
		<button type="submit">SIGN UP</button>
	</form>

	<sec:authorize access="isAnonymous()">
		<form action="${pageContext.request.contextPath}/signin" method="GET">
			<sec:csrfInput />
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
</body>
</html>

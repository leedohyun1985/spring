<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>User</title>
</head>
<body>
	<h1>User Hello world!</h1>

	<sec:authorize access="isAnonymous()">
		<form action="${pageContext.request.contextPath}/signin" method="GET">
			
			<button type="submit">LOGIN</button>
		</form>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<form action="${pageContext.request.contextPath}/signout" method="POST">
			<sec:csrfInput />
			<button type="submit">LOGOUT</button>
		</form>
	</sec:authorize>

	<P>The time on the server is ${serverTime}.</P>
</body>
</html>

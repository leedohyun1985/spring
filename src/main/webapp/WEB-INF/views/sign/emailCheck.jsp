<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta name="generator" content="">
<title>Email Check</title>

<link rel="canonical" href="">

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Favicons -->
<link rel="apple-touch-icon"
	href="${pageContext.request.contextPath}/resources/favicons/apple-icon-180x180.png"
	sizes="180x180">
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/favicons/favicon-32x32.png"
	sizes="32x32" type="image/png">
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/favicons/favicon-16x16.png"
	sizes="16x16" type="image/png">
<link rel="manifest"
	href="${pageContext.request.contextPath}/resources/favicons/manifest.json">
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/favicons/favicon.ico">
<meta name="msapplication-config"
	content="${pageContext.request.contextPath}/resources/favicons/browserconfig.xml">
<meta name="theme-color" content="#563d7c">

<!-- Custom styles for this template -->
<link
	href="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/css/signin.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/custom-css-bootstrap-magic-2020-08-16.css"
	rel="stylesheet">
</head>
<body>
	<form class="form-signin" method="post"
		action="${pageContext.request.contextPath}/signupemailcheck">
		<sec:csrfInput />
		<div class="text-center mb-4">
			<img class="mb-4"
				src="${pageContext.request.contextPath}/resources/favicons/favicon-96x96.png"
				alt="" width="72" height="72">
			<h1 class="h3 mb-3 font-weight-normal">이메일 인증 코드 입력</h1>
		</div>
		<div class="form-label-group">
			<input type="email" id="email" class="form-control" name="email" value="${email}" <c:if test = "${email ne null}"> readonly="readonly" </c:if> required> <label for="email">Email</label>
		</div>
		<div class="form-label-group">
			<input type="number" id="code" class="form-control" name="code"
				placeholder="Registration Number" required autofocus> <label
				for="inputEmail">Check Code</label>
		</div>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
		<p class="mt-5 mb-3 text-muted text-center">&copy; 2020</p>
	</form>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>ERROR ${errorCode}</title>
<link
	href="https://fonts.googleapis.com/css?family=Righteous&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/space-404/dist/style.css">
</head>

<body>


	<!-- partial:index.partial.html -->
	<div class="moon"></div>
	<div class="moon__crater moon__crater1"></div>
	<div class="moon__crater moon__crater2"></div>
	<div class="moon__crater moon__crater3"></div>

	<div class="star star1"></div>
	<div class="star star2"></div>
	<div class="star star3"></div>
	<div class="star star4"></div>
	<div class="star star5"></div>

	<div class="error">
		<div class="error__title">${errorCode}</div>
		<div class="error__subtitle">${errorMessage}</div>
		<%-- <div class="error__description">${errorName}</div> --%>
		<button class="error__button error__button--active"
			onclick="location.href='${pageContext.request.contextPath}/signin'">LOGIN</button>
		<button class="error__button">CONTACT</button>
	</div>

	<div class="astronaut">
		<div class="astronaut__backpack"></div>
		<div class="astronaut__body"></div>
		<div class="astronaut__body__chest"></div>
		<div class="astronaut__arm-left1"></div>
		<div class="astronaut__arm-left2"></div>
		<div class="astronaut__arm-right1"></div>
		<div class="astronaut__arm-right2"></div>
		<div class="astronaut__arm-thumb-left"></div>
		<div class="astronaut__arm-thumb-right"></div>
		<div class="astronaut__leg-left"></div>
		<div class="astronaut__leg-right"></div>
		<div class="astronaut__foot-left"></div>
		<div class="astronaut__foot-right"></div>
		<div class="astronaut__wrist-left"></div>
		<div class="astronaut__wrist-right"></div>

		<div class="astronaut__cord">
			<canvas id="cord" height="500px" width="500px"></canvas>
		</div>

		<div class="astronaut__head">
			<canvas id="visor" width="60px" height="60px"></canvas>
			<div class="astronaut__head-visor-flare1"></div>
			<div class="astronaut__head-visor-flare2"></div>
		</div>
	</div>
	<!-- partial -->
	<script src="${pageContext.request.contextPath}/resources/space-404/dist/script.js"></script>

</body>

</html>


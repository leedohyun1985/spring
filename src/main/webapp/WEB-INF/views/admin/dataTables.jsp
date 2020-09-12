<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta name="generator" content="">
<title>Admin</title>

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

<script
	src="${pageContext.request.contextPath}/resources/jQuery/3.5.1/jquery-3.5.1.js"></script>

<script type="text/javascript" charset="utf8"
	src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/datatables.js"></script>

<link
	href="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/DataTables/1.10.21/datatables.css">

<script type="text/javascript">

	$(document).ready(function() {
/* 		$('#table_id').DataTable( {
		    serverSide: true,
		    ajax: '${pageContext.request.contextPath}/admin/userinfo'
		} ); */
		
		$("#table_id").DataTable({

		    "serverSide": true,
		    "processing": true,
		    "ajax": {
		        "url": "${pageContext.request.contextPath}/admin/userinfo",
		        "type": "GET",
		        "dataSrc": function(data) {
		            return data;
		        }
		    },
		    "columns" : [
		        {"data": "user_id"},
		        {"data": "user_email"},
		        {"data": "user_name"}
		    ]

		});
	});
</script>

</head>
<body>
	<div class="container">
		<div class="row py-5">
			<div class="col-12">
				<table id="table_id" class="table table-hover responsive nowrap"
					style="width: 100%">
					<thead>
						<tr>
							<th>user_id</th>
							<th>user_email</th>
							<th>user_name</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>


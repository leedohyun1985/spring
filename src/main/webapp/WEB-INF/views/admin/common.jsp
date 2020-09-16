<%@page import="org.springframework.jdbc.support.JdbcUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page import="java.util.*"%>
<%@page import="org.springframework.jdbc.support.JdbcUtils"%>

<%
ArrayList<Map<String, Object>> tableList = (ArrayList<Map<String, Object>>) request.getAttribute("tableList");
ArrayList<Map<String, Object>> columnList = (ArrayList<Map<String, Object>>) request.getAttribute("columnList");
ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) request.getAttribute("dataList");
ArrayList<Map<String, Object>> foreignKeyList = (ArrayList<Map<String, Object>>) request.getAttribute("foreignKeyList");
ArrayList<String> exceptionColumnList = (ArrayList<String>) request.getAttribute("exceptionColumnList");
ArrayList<String> primaryKeyList = (ArrayList<String>) request.getAttribute("primaryKeyList");
ArrayList<String> autoIncrementColumnList = (ArrayList<String>) request.getAttribute("autoIncrementColumnList");
%>

<!DOCTYPE html>
<html>
<head>
<title>Database "${tableName}" Table Management</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta name="generator" content="">
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

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/fontawesome-free/5.14.0/css/all.css">


<link
	href="${pageContext.request.contextPath}/resources/startbootstrap-sb-admin-gh-pages/dist/css/styles.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/resources/DataTables/1.10.21/DataTables-1.10.21/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" />
<%-- <link
	href="${pageContext.request.contextPath}/resources/DataTables/1.10.21/Select-1.3.1/css/select.bootstrap4.min.css"
	rel="stylesheet" /> --%>
<link
	href="${pageContext.request.contextPath}/resources/DataTables/1.10.21/Select-1.3.1/css/select.dataTables.min.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/resources/fontawesome-free/5.14.0/js/all.min.js"></script>


<script
	src="${pageContext.request.contextPath}/resources/jQuery/3.5.1/jquery-3.5.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/DataTables-1.10.21/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {

				var table = $('#dataTable').DataTable({
					select : {
						style : 'single'
					}
				});

				table.on('select', function() {

					//$('#insertButton').attr("disabled", false);
					$('#updateButton').attr("disabled", false);
					$('#deleteButton').attr("disabled", false);

					var inputData = table.row({
						selected : true
					}).data();

					for (i = 0; i < inputData.length; i++) {
						var inputBox = eval("$('#deleteInput" + i + "')");
						inputBox.val(inputData[i]);

					}

					for (var i = 0; i < $('[id^=insertInput]').length; i++) {
						
						var input_name = eval("$('#insertInput" + i + "')").attr(
								'name');
						
						$("#dataTable > thead > tr > th").each(function () {
						   
						   var columnname = $(this).data('columnname');
						   var columnorder = $(this).data('columnorder');
						   
						if(columnname == input_name)
						{	
							var inputBox = eval("$('#insertInput" + i + "')");
							inputBox.val(inputData[$(this).data('columnorder')]);
							inputBox.attr("placeholder", inputData[$(this).data('columnorder')]);
						}
						   
							})
						
					}

					for (var i = 0; i < $('[id^=updateInput]').length; i++) {

						var input_name = eval("$('#updateInput" + i + "')").attr(
								'name');

						$("#dataTable > thead > tr > th").each(function () {
						   
						   var columnname = $(this).data('columnname');
						   var columnorder = $(this).data('columnorder');
						   
						if(columnname == input_name)
						{	   
							var inputBox = eval("$('#updateInput" + i + "')");
							inputBox.val(inputData[$(this).data('columnorder')]);
						}
						   
							})
					}
				});

				table.on('deselect', function() {
					var rowData = table.rows({
						selected : true
					}).data()[0];

					//$('#insertButton').attr("disabled", true);
					$('#updateButton').attr("disabled", true);
					$('#deleteButton').attr("disabled", true);
				});

			    $('#insertAjaxButton').on('click', function () {
			        
			    	$.ajax({
			        		type: "post" // HTTP 요청 방식(GET, POST)
			        	,	async: true
		                ,	contentType: "application/json; charset=utf-8"
			            ,	url: '${pageContext.request.contextPath}/admin/database/insert/${tableName}'
			            //,	data: $("#insertform").serialize()
			            ,	data: JSON.stringify($('form[name="insertform"]').serializeArray())
			            //,	dataType : 'json'
		            	,   beforeSend : function(xhr)
		               	{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
		                   xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		               	}
			            ,	success: function(data) {
			            	location.reload();
			                }
			            ,	error: function(jqXHR, textStatus, errorThrown) {
			            	alert(jqXHR.responseText);
			            	console.log(jqXHR.responseText);
			            	}
			        });
			    });
				
			    $('#updateAjaxButton').on('click', function () {
			        
			    	$.ajax({
			        		type: "post" // HTTP 요청 방식(GET, POST)
			        	,	async: true
		                ,	contentType: "application/json; charset=utf-8"
			        	,	url: '${pageContext.request.contextPath}/admin/database/update/${tableName}'
			            //,	data: $("#updateform").serialize()
			            ,	data: JSON.stringify($('form[name="updateform"]').serializeArray()) 
			            ,	dataType : 'json'
						,   beforeSend : function(xhr)
			               	{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
			                   xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			               	}
			            ,	success: function(data) {
			            	location.reload();
			                }
			            ,	error: function(jqXHR, textStatus, errorThrown) {
			            	alert(jqXHR.responseText);
			            	console.log(jqXHR.responseText);
			            	}
			        });
			    });
				
			    $('#deleteAjaxButton').on('click', function () {
			        
			    	$.ajax({
			        		type: "post" // HTTP 요청 방식(GET, POST)
			        	,	async: true    
			    		,	contentType: "application/json; charset=utf-8"
			        	,	url: '${pageContext.request.contextPath}/admin/database/delete/${tableName}'
			            //,	data: JSON.stringify($("#deleteform").serializeArray())
			            ,	data: JSON.stringify($('form[name="deleteform"]').serializeArray()  )
			            ,	dataType : 'json'
		            	,   beforeSend : function(xhr)
		               	{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
		                   xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		               	}
			            ,	success: function(data) {
			            	location.reload();
			                }
			            ,	error: function(jqXHR, textStatus, errorThrown) {
			            	alert(jqXHR.responseText);
			            	console.log(jqXHR.responseText);
			            	}
			        });
			    });
				

				$('#insertModal').on('show.bs.modal', function(event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var modalname = button.data('modalname') // Extract info from data-* attributes
					// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					var modal = $(this)
					modal.find('.modal-title').text(modalname)
					//modal.find('.modal-body input').val(recipient)
				})

				$('#updateModal').on('show.bs.modal', function(event) {

					table.rows('.selected').data()

					var button = $(event.relatedTarget) // Button that triggered the modal
					var modalname = button.data('modalname') // Extract info from data-* attributes
					// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					var modal = $(this)
					modal.find('.modal-title').text(modalname)
					//modal.find('.modal-body input').val(recipient)
				})

				$('#deleteModal').on('show.bs.modal', function(event) {
					var button = $(event.relatedTarget) // Button that triggered the modal
					var modalname = button.data('modalname') // Extract info from data-* attributes
					// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					var modal = $(this)
					modal.find('.modal-title').text(modalname)
					//modal.find('.modal-body input').val(recipient)
				})

			});
</script>
</head>
<body class="sb-nav-fixed">

	<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
		<a class="navbar-brand"
			href="${pageContext.request.contextPath}/resources/startbootstrap-sb-admin-gh-pages/dist/index.html">관리자
			화면</a>
		<button class="btn btn-link btn-sm order-1 order-lg-0"
			id="sidebarToggle" href="#">
			<i class="fas fa-bars"></i>
		</button>
		<!-- Navbar Search-->
		<form
			class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
			<div class="input-group">
				<input class="form-control" type="text" placeholder="Search for..."
					aria-label="Search" aria-describedby="basic-addon2" />
				<div class="input-group-append">
					<button class="btn btn-primary" type="button">
						<i class="fas fa-search"></i>
					</button>
				</div>
			</div>
		</form>
		<!-- Navbar-->
		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" id="userDropdown" href="#"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
				<div class="dropdown-menu dropdown-menu-right"
					aria-labelledby="userDropdown">
					<a class="dropdown-item" href="#">Settings</a> <a
						class="dropdown-item" href="#">Activity Log</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/resources/startbootstrap-sb-admin-gh-pages/dist/login.html">Logout</a>
				</div></li>
		</ul>
	</nav>

	<div id="layoutSidenav">
		<div id="layoutSidenav_nav">

			<nav class="sb-sidenav accordion sb-sidenav-dark"
				id="sidenavAccordion">

				<div class="sb-sidenav-menu">
					<div class="nav">

						<div class="sb-sidenav-menu-heading">Core</div>
						<a class="nav-link" href="#">
							<div class="sb-nav-link-icon">
								<i class="fas fa-tachometer-alt"></i>
							</div> Dashboard
						</a>

						<div class="sb-sidenav-menu-heading">DATABASE</div>
						<a class="nav-link collapsed" href="#" data-toggle="collapse"
							data-target="#collapseLayouts" aria-expanded="false"
							aria-controls="collapseLayouts">
							<div class="sb-nav-link-icon">
								<i class="fas fa-table"></i>
							</div> Tables
							<div class="sb-sidenav-collapse-arrow">
								<i class="fas fa-angle-down"></i>
							</div>
						</a>

						<div class="collapse" id="collapseLayouts"
							aria-labelledby="headingOne" data-parent="#sidenavAccordion">
							<nav class="sb-sidenav-menu-nested nav">

								<%
									for (Map<String, Object> map : tableList) {
								%>
								<a class="nav-link"
									href="${pageContext.request.contextPath}/admin/database/<%=map.get("tableName")%>">
									<%=map.get("tableComment")%>
								</a>
								<%
									}
								%>

							</nav>
						</div>

					</div>
				</div>

				<div class="sb-sidenav-footer">
					<div class="small">Logged in as:</div>
					Start Bootstrap
				</div>
			</nav>
		</div>

		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">${tableComment}</h1>
					<div class="card mb-4">
						<div class="card-header">
							<i class="fas fa-table mr-1"></i> ${tableName}
						</div>
						<div class="card-body">
							<div class="table-responsive">

								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">

									<thead>
										<tr>

											<%
												int index = 0;
											for (Map<String, Object> map : columnList) {
											%>
											<%-- <th><%=map.get("columnName")%></th> --%>
											<th data-columnname="<%=map.get("columnName")%>"
												data-columnorder="<%=index%>"><%=map.get("columnComment")%>
											</th>
											<%
												index++;
											}
											%>

										</tr>
									</thead>

									
									<tfoot>
										<tr>
											<%
											for (Map<String, Object> map : columnList) {
											%>
											<th><%=map.get("columnComment")%></th>
											<%
											}
											%>
										</tr>
									</tfoot>
									
									<tbody>
										<%
											for (Map<String, Object> map : dataList) {
										%>
										<tr>
											<%
												for (Map<String, Object> cmap : columnList) {
											%>
											<td><%=map.get(JdbcUtils.convertUnderscoreNameToPropertyName(String.valueOf(cmap.get("columnName"))))%></td>
											<%
												}
											%>
										</tr>
										<%
											}
										%>
									</tbody>

								</table>
							</div>
						</div>
					</div>
					<button type="button" class="btn btn-danger btn-sm float-right"
						disabled="disabled" id="deleteButton" data-modalname="삭제"
						data-toggle="modal" data-target="#deleteModal">삭제</button>
					<button type="button" class="btn btn-warning btn-sm float-right"
						disabled="disabled" id="updateButton" data-modalname="갱신"
						data-toggle="modal" data-target="#updateModal">갱신</button>
					<button type="button" class="btn btn-primary btn-sm float-right"
						id="insertButton" data-modalname="입력" data-toggle="modal"
						data-target="#insertModal">입력</button>
				</div>
			</main>
			<footer class="py-4 bg-light mt-auto">
				<div class="container-fluid">
					<div
						class="d-flex align-items-center justify-content-between small">
						<div class="text-muted">Copyright &copy; Lee Dohyun 2020</div>
						<div>
							<a href="#">Privacy Policy</a> &middot; <a href="#">Terms
								&amp; Conditions</a>
						</div>
					</div>
				</div>
			</footer>
		</div>


		<!-- insertModal -->
		<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
			aria-labelledby="modalTitle" aria-hidden="true">
			<div
				class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
				role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="modalTitle">입력</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<form name="insertform">
							<%-- <%
								for (int i = 0; i < primaryKeyList.size(); i++) {
								String pk = primaryKeyList.get(i);
							%>
							<input type="hidden" name="primaryKey<%=i%>" value="<%=pk%>">
							<%
								}
							%>
							 --%>
							<%
								int insertInputIndex = 0;
								for (int i = 0; i < columnList.size(); i++) {
								Map<String, Object> map = columnList.get(i);
								boolean print = true;

								for (int j = 0; j < exceptionColumnList.size(); j++) {
									if (String.valueOf(map.get("columnName")).equalsIgnoreCase(String.valueOf(exceptionColumnList.get(j)))) {
								print = false;
									}
								}
								for (int j = 0; j < autoIncrementColumnList.size(); j++) {
									if (String.valueOf(map.get("columnName")).equalsIgnoreCase(String.valueOf(autoIncrementColumnList.get(j)))) {
								print = false;
									}
								}
								if (print == true) {
							%>

							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="basic-addon<%=i%>"><%=map.get("columnComment")%></span>
								</div>

								<%
									boolean fkFlag = false;
								ArrayList<String> inputList = new ArrayList<String>();
								for (Map<String, Object> fmap : foreignKeyList) {

									Iterator<String> keys = fmap.keySet().iterator();
									while (keys.hasNext()) {
										String key = keys.next();
										//System.out.println("외래키 칼럼 여부" + key.equalsIgnoreCase(String.valueOf(map.get("columnName"))));
										if (key.equalsIgnoreCase(String.valueOf(map.get("columnName")))) {
									fkFlag = true;
									inputList = (ArrayList<String>) fmap.get(key);
										}
									}
								}

								if (!fkFlag) {
								%>
								<input type="text" class="form-control" id="insertInput<%=insertInputIndex%>"
									name="<%=map.get("columnName")%>"
									aria-describedby="basic-addon<%=i%>">
								<%
								insertInputIndex++;
									} else {
								%>
								<select class="custom-select" name="<%=map.get("columnName")%>"
									id="insertInput<%=insertInputIndex%>">
									<%
										for (String value : inputList) {
									%>
									<option value=<%=value%>>
										<%=value%></option>
									<%
										}
									%>
								</select>

								<%
								insertInputIndex++;
									}
								%>

							</div>
							<%
								}
							%>

							<%
								}
							%>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" id="insertAjaxButton">입력</button>
					</div>
				</div>
			</div>
		</div>

		<!-- updateModal -->
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
			aria-labelledby="modalTitle" aria-hidden="true">
			<div
				class="modal-dialog modal-dialog-centered modal-dialog-scrollable"
				role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="modalTitle">갱신</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<form name="updateform">
							<%
								for (int i = 0; i < primaryKeyList.size(); i++) {
								String pk = primaryKeyList.get(i);
							%>
							<input type="hidden" name="primaryKey<%=i%>" value="<%=pk%>">
							<%
								}
							%>							
							<%
								int updateInputIndex = 0;
								for (int i = 0; i < columnList.size(); i++) {
								Map<String, Object> map = columnList.get(i);
								boolean print = true;
								boolean autoIncrement = false;

								for (int j = 0; j < exceptionColumnList.size(); j++) {
									if (String.valueOf(map.get("columnName")).equalsIgnoreCase(String.valueOf(exceptionColumnList.get(j)))) {
								print = false;
									}
								}
								for (int j = 0; j < autoIncrementColumnList.size(); j++) {
									if (String.valueOf(map.get("columnName")).equalsIgnoreCase(String.valueOf(autoIncrementColumnList.get(j)))) {
										autoIncrement = true;
									}
								}
								if (print == true) {
							%>

							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="basic-addon<%=i%>"><%=map.get("columnComment")%></span>
								</div>

								<%
									boolean fkFlag = false;
								ArrayList<String> inputList = new ArrayList<String>();
								for (Map<String, Object> fmap : foreignKeyList) {

									Iterator<String> keys = fmap.keySet().iterator();
									while (keys.hasNext()) {
										String key = keys.next();
										//System.out.println("외래키 칼럼 여부" + key.equalsIgnoreCase(String.valueOf(map.get("columnName"))));
										if (key.equalsIgnoreCase(String.valueOf(map.get("columnName")))) {
									fkFlag = true;
									inputList = (ArrayList<String>) fmap.get(key);
										}
									}
								}

								if (!fkFlag) {
								%>
								<input type="text" class="form-control" id="updateInput<%=updateInputIndex%>"
									name="<%=map.get("columnName")%>" <%if(autoIncrement){%>readonly<%}%>
									aria-describedby="basic-addon<%=updateInputIndex%>"  >
								<%
								updateInputIndex++;
									} else {
								%>
								<select class="custom-select" name="<%=map.get("columnName")%>" <%if(autoIncrement){%>disabled<%}%>
									id="updateInput<%=updateInputIndex%>">
									<%
										for (String value : inputList) {
									%>
									<option value=<%=value%>>
										<%=value%></option>
									<%
										}
									%>
								</select>

								<%
								updateInputIndex++;
									}
								%>

							</div>


							<%
								}
							%>

							<%
								}
							%>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-warning" id="updateAjaxButton">갱신</button>
					</div>
				</div>
			</div>
		</div>

		<!-- deleteModal -->
		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
			aria-labelledby="deleteModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="deleteModalLabel">삭제</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">

						<form name="deleteform">						
							<%
								for (int i = 0; i < primaryKeyList.size(); i++) {
								String pk = primaryKeyList.get(i);
							%>
							<input type="hidden" name="primaryKey<%=i%>" value="<%=pk%>">
							<%
								}
							%>
							<%
								for (int i = 0; i < columnList.size(); i++) {
								Map<String, Object> map = columnList.get(i);
							%>
							<div class="input-group mb-3">
								<div class="input-group-prepend">
									<span class="input-group-text" id="basic-addon<%=i%>"><%=map.get("columnComment")%></span>
								</div>
								<input type="text" class="form-control" id="deleteInput<%=i%>"
									name="<%=map.get("columnName")%>"
									aria-describedby="basic-addon<%=i%>" readonly="readonly">
							</div>
							<%
								}
							%>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" 
							data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-danger" id="deleteAjaxButton">삭제</button>
					</div>
				</div>
			</div>
		</div>

	</div>


	<script
		src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/Select-1.3.1/js/dataTables.select.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/Buttons-1.6.3/js/dataTables.buttons.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/Buttons-1.6.3/js/buttons.bootstrap4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/bootstrap/4.5.2/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/startbootstrap-sb-admin-gh-pages/dist/js/scripts.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/DataTables/1.10.21/DataTables-1.10.21/js/dataTables.bootstrap4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/startbootstrap-sb-admin-gh-pages/dist/assets/demo/datatables-demo.js"></script>
</body>
</html>

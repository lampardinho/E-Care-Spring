<%@ page import="com.tsystems.javaschool.ecare.entities.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: Kolia
  Date: 01.07.2015
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="icon" href="../../img/favicon.ico">

		<title>Admin lobby</title>

		<!-- Bootstrap core CSS -->
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

		<!-- Custom styles for this template -->
		<link href="/css/navbar.css" rel="stylesheet">

		<link href="/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/css/bootstrap-select.min.css">

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src="/js/bootstrap-dialog.min.js"></script>
		<script src="/js/bootstrap-select.min.js"></script>

	</head>

	<body>


		<div class="container">

			<!-- Static navbar -->
			<nav class="navbar navbar-default navbar-fixed-top">
				<div class="container">
					<div class="navbar-header">
						<a class="navbar-brand" href="#">E-Care</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">

						<div class="navbar-right">
							<p class="navbar-text"><c:out value="${user.email}"/></p>

							<button type="button" class="btn btn-default navbar-btn" id="logout">Sign out</button>
						</div>

						<ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
							<li class="active"><a href="admin_clients.jsp" data-toggle="tab">Clients</a></li>
							<li><a id="contracts_tab" href=admin_lobby.jsp" data-toggle="tab">Contracts</a></li>
							<li><a id="tariffs_tab" href="admin_clients.jsp" data-toggle="tab">Tariffs</a></li>
							<li><a id="options_tab" href="#options" data-toggle="tab">Options</a></li>
						</ul>

					</div><!--/.nav-collapse -->
				</div><!--/.container-fluid -->
			</nav>


			<!-- Main component for a primary marketing message or call to action -->
			<div id="content" class="jumbotron">
				<div id="my-tab-content" class="tab-content">

					<div class="tab-pane" id="options">
						<div id="options_info" class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Options</h3>
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Name</th>
											<th>Connection Price</th>
											<th>Monthly Price</th>
											<th>Locked Options</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="option" items="${options}" varStatus="myIndex">
											<tr>
												<th scope="row">${myIndex.index+1}</th>
												<td class="option_name">${option.name}</td>
												<td>${option.connectionPrice}</td>
												<td>${option.monthlyPrice}</td>
												<td>
													<c:forEach var="option" items="${option.lockedOptions}">
														${option.name};
													</c:forEach>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<!-- Modal -->
								<div class="modal fade" id="editOption" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Edit Option</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group col-sm-10 col-sm-offset-2">
														<label>Locked options</label>
														<c:forEach var="option" items="${options}">
															<div class="checkbox">
																<label>
																	<input type="checkbox" name="edit_lock_options" value="${option.name}">
																		${option.name}
																</label>
															</div>
														</c:forEach>
													</div>
													<div class="form-group">
														<div class="col-sm-offset-2 col-sm-10">
															<%--<button type="submit" class="btn btn-success">Edit tariff</button>--%>
														</div>
													</div>

												</form>
											</div>
											<div class="modal-footer">
												<button id="editOptionButton" type="button" class="btn btn-success">Save changes</button>
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>

				</div>

				<div id="myScripts">
					<script src="${pageContext.request.contextPath}/js/admin-lobby.js"></script>
				</div>
			</div>

		</div> <!-- /container -->



	</body>
</html>

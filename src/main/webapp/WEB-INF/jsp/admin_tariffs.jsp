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

		<%--<%--%>
			<%--User user = (User)session.getAttribute("user");--%>
		<%--%>--%>


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
							<li class="active"><a href="#clients" data-toggle="tab">Clients</a></li>
							<li><a id="contracts_tab" href="#contracts" data-toggle="tab">Contracts</a></li>
							<li><a id="tariffs_tab" href="#tariffs" data-toggle="tab">Tariffs</a></li>
							<li><a id="options_tab" href="admin_options.jsp#options" data-toggle="tab">Options</a></li>
						</ul>

					</div><!--/.nav-collapse -->
				</div><!--/.container-fluid -->
			</nav>


			<!-- Main component for a primary marketing message or call to action -->
			<div id="content" class="jumbotron">
				<div id="my-tab-content" class="tab-content">

					<div class="tab-pane" id="tariffs">
						<div id="tariffs_info" class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Tariffs</h3>
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Name</th>
											<th>Price</th>
											<th>Available Options</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="tariff" items="${tariffs}" varStatus="myIndex">
											<tr>
												<th scope="row">${myIndex.index+1}</th>
												<td class="tariff_name">${tariff.name}</td>
												<td>${tariff.price}</td>
												<td>
													<c:forEach var="option" items="${tariff.availableOptions}">
														${option.name};
													</c:forEach>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<br>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newTariff">Add Tariff</button>

								<!-- Modal -->
								<div class="modal fade" id="newTariff" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">New Tariff</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group">
														<label for="tariffName" class="col-sm-2 control-label">Name</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="tariffName" placeholder="Name">
														</div>
													</div>
													<div class="form-group">
														<label for="tariffPrice" class="col-sm-2 control-label">Price</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="tariffPrice" placeholder="Price">
														</div>
													</div>
													<div class="form-group col-sm-10 col-sm-offset-2">
														<label>Available options</label>
														<c:forEach var="option" items="${options}">
															<div class="checkbox">
																<label>
																	<input type="checkbox" name="avail_options" value="${option.name}">
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
												<button type="button" class="btn btn-success" id="createTariff">Create tariff</button>
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>

									</div>
								</div>

								<!-- Modal -->
								<div class="modal fade" id="editTariff" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Edit Tariff</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group col-sm-10 col-sm-offset-2">
														<label>Available options</label>
														<c:forEach var="option" items="${options}">
															<div class="checkbox">
																<label>
																	<input type="checkbox" name="edit_avail_options" value="${option.name}">
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
												<button id="saveEditTariff" type="button" class="btn btn-success">Save changes</button>
												<button id="deleteTariff" type="button" class="btn btn-danger">Delete tariff</button>
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

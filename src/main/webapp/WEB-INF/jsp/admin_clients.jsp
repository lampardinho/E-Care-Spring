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
							<li class="active"><a href="admin_clients.jsp#clients" data-toggle="tab">Clients</a></li>
							<li><a id="contracts_tab" href="admin_contracts.jsp#contracts" data-toggle="tab">Contracts</a></li>
							<li><a id="tariffs_tab" href="admin_tariffs.jsp#tariffs" data-toggle="tab">Tariffs</a></li>
							<li><a id="options_tab" href="admin_options.jsp#options" data-toggle="tab">Options</a></li>
						</ul>

					</div><!--/.nav-collapse -->
				</div><!--/.container-fluid -->
			</nav>


			<!-- Main component for a primary marketing message or call to action -->
			<div id="content" class="jumbotron">
				<div id="my-tab-content" class="tab-content">

					<div class="tab-pane active" id="clients">
						<div id="clients_info" class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Clients</h3>
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>First Name</th>
											<th>Last Name</th>
											<th>Birth Date</th>
											<th>Passport</th>
											<th>Address</th>
											<th>E-mail</th>
											<th>Privileges</th>
											<th>Blocking</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${users}" varStatus="myIndex">
											<tr>
												<th scope="row">${myIndex.index+1}</th>
												<td>${user.name}</td>
												<td>${user.surname}</td>
												<td>${user.birthDate}</td>
												<td>${user.passportData}</td>
												<td>${user.address}</td>
												<td class="user_email">${user.email}</td>
												<td>
													<c:choose>
														<c:when test="${user.isAdmin == true}">
															Admin
														</c:when>
														<c:otherwise>
															Client
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${lockedUsers.contains(user)}">
															<button type="button" class="btn btn-success unlockButton">Unlock</button>
														</c:when>
														<c:otherwise>
															<button type="button" class="btn btn-danger lockButton">Lock</button>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<br>
								<form class="form-inline">
									<div class="form-group">
										<label for="searchPhoneNumber">Phone number</label>
										<input type="text" class="form-control" id="searchPhoneNumber" placeholder="123456">
									</div>
									<button type="button" class="btn btn-default" id="searchUser">Search</button>
								</form>

								<!-- Modal -->
								<div class="modal fade" id="userProfile" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Client Profile</h4>
											</div>
											<div class="modal-body">

												<table class="table table-hover">
													<thead>
													<tr>
														<th>First Name</th>
														<th>Last Name</th>
														<th>Birth Date</th>
														<th>Passport</th>
														<th>E-mail</th>
														<th>Privileges</th>
													</tr>
													</thead>
													<tbody>
														<tr>
															<td>${foundUser.name}</td>
															<td><c:out value="${foundUser.surname}"/></td>
															<td><c:out value="${foundUser.birthDate}"/></td>
															<td><c:out value="${foundUser.passportData}"/></td>
															<td id="foundUserEmail"><c:out value="${foundUser.email}"/></td>
															<td>
																<c:choose>
																	<c:when test="${foundUser.isAdmin == true}">
																		Admin
																	</c:when>
																	<c:otherwise>
																		Client
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
													</tbody>
												</table>

											</div>
											<div class="modal-footer">
												<c:choose>
													<c:when test="${lockedUsers.contains(foundUser)}">
														<button type="button" class="btn btn-success unlockButton">Unlock</button>
													</c:when>
													<c:otherwise>
														<button type="button" class="btn btn-danger lockButton">Lock</button>
													</c:otherwise>
												</c:choose>
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>

									</div>
								</div>

								<br>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addUser">Add user</button>

								<!-- Modal -->
								<div class="modal fade" id="addUser" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">New Client</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group">
														<label for="user_firstName" class="col-sm-2 control-label">First name</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="user_firstName" placeholder="First name" required>
														</div>
													</div>
													<div class="form-group">
														<label for="user_lastName" class="col-sm-2 control-label">Last name</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="user_lastName" placeholder="Last name">
														</div>
													</div>
													<div class="form-group">
														<label for="user_birthDate" class="col-sm-2 control-label">Birth date</label>
														<div class="col-sm-10">
															<input type="date" class="form-control" id="user_birthDate" placeholder="Birth date">
														</div>
													</div>
													<div class="form-group">
														<label for="user_passportData" class="col-sm-2 control-label">Passport data</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="user_passportData" placeholder="Passport data">
														</div>
													</div>
													<div class="form-group">
														<label for="user_address" class="col-sm-2 control-label">Address</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="user_address" placeholder="Address">
														</div>
													</div>
													<div class="form-group">
														<label for="user_email" class="col-sm-2 control-label">E-mail</label>
														<div class="col-sm-10">
															<input type="email" class="form-control" id="user_email" placeholder="E-mail">
														</div>
													</div>
													<div class="form-group">
														<label for="user_password" class="col-sm-2 control-label">Password</label>
														<div class="col-sm-10">
															<input type="password" class="form-control" id="user_password" placeholder="Password">
														</div>
													</div>
													<div class="form-group">
														<label for="user_password2" class="col-sm-2 control-label">Repeat password</label>
														<div class="col-sm-10">
															<input type="password" class="form-control" id="user_password2" placeholder="Password">
														</div>
													</div>
													<div class="form-group">
														<div class="col-sm-offset-2 col-sm-10">
															<div class="checkbox">
																<label>
																	<input type="checkbox" id="user_isAdmin"> Admin
																</label>
															</div>
														</div>
													</div>

												</form>
											</div>
											<div class="modal-footer">
												<button type="button" id="createUser" class="btn btn-success">Create user</button>
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

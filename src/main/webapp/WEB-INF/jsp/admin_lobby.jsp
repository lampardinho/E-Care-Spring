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
		<link rel="icon" href="${pageContext.request.contextPath}/img/favicon.ico">

		<title>Admin lobby</title>

		<!-- Bootstrap core CSS -->
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

		<!-- Custom styles for this template -->
		<link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap-dialog.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>

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
							<li class="active"><a href="#clients" data-toggle="tab">Clients</a></li>
							<li><a id="contracts_tab" href="#contracts" data-toggle="tab">Contracts</a></li>
							<li><a id="tariffs_tab" href="#tariffs" data-toggle="tab">Tariffs</a></li>
							<li><a id="options_tab" href="#options" data-toggle="tab">Options</a></li>
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
												<td>${user.role.roleName}</td>
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
															<td><c:out value="${foundUser.role.roleName}"/></td>
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



					<div class="tab-pane" id="contracts">
						<div id="contracts_info" class="panel panel-info">
							<div class="panel-heading">
								<h3 class="panel-title">Contracts</h3>
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>Phone Number</th>
											<th>Owner</th>
											<th>Balance</th>
											<th>Tariff</th>
											<th>Selected Options</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="contract" items="${contracts}" varStatus="myIndex">
											<tr>
												<th scope="row">${myIndex.index+1}</th>
												<td class="contract_phone">${contract.phoneNumber}</td>
												<td>${contract.user.name} ${contract.user.surname}</td>
												<td>${contract.balance}</td>
												<td class="tariff-name">${contract.tariff.name}</td>
												<td>
													<c:forEach var="option" items="${contract.selectedOptions}">
														${option.name};
													</c:forEach>
												</td>
												<td>
													<button type="button" class="btn btn-default editOptionsButton">Select options</button>
													<button type="button" class="btn btn-default changeTariffButton">Change tariff</button>
												</td>

											</tr>
										</c:forEach>
									</tbody>
								</table>


								<br>
								<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newContract">Add Contract</button>

								<!-- Modal -->
								<div class="modal fade" id="newContract" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">New Contract</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group">
														<label for="owner" class="col-sm-2 control-label">Owner</label>
														<div class="col-sm-10">
															<select class="" id="owner">
																<c:forEach var="user" items="${users}">
																	<option>${user.name} ${user.surname}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="form-group">
														<label for="phoneNumber" class="col-sm-2 control-label">Phone number</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="phoneNumber" placeholder="Phone number">
														</div>
													</div>
													<div class="form-group">
														<label for="balance" class="col-sm-2 control-label">Balance</label>
														<div class="col-sm-10">
															<input type="text" class="form-control" id="balance" placeholder="Balance">
														</div>
													</div>
													<div class="form-group">
														<label for="tariff" class="col-sm-2 control-label">Tariff</label>
														<div class="col-sm-10">
															<select class="" id="tariff">
																<c:forEach var="tariff" items="${tariffs}">
																	<option>${tariff.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</form>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-success" id="createContract">Create contract</button>
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>

									</div>
								</div>

								<!-- Modal -->
								<div class="modal fade" id="editOptions" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Select Options</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">

													<div id="avail_options_div" class="form-group col-sm-10 col-sm-offset-2">
														<label>Available options</label>
														<c:forEach var="option" items="${availableOptions}">
															<div class="checkbox">
																<label>
																	<c:choose>
																		<c:when test="${contract_cantDisableOptions.contains(option)}">
																			<input type="checkbox" checked="checked" disabled="disabled" name="selectedOptions" value="${option.name}">${option.name}
																		</c:when>
																		<c:when test="${contract_selectedOptions.contains(option)}">
																			<input type="checkbox" checked="checked" name="selectedOptions" value="${option.name}">${option.name}
																		</c:when>
																		<c:when test="${contract_disabledOptions.contains(option)}">
																			<input type="checkbox" disabled="disabled" name="selectedOptions" value="${option.name}">${option.name}
																		</c:when>
																		<c:otherwise>
																			<input type="checkbox" name="selectedOptions" value="${option.name}">${option.name}
																		</c:otherwise>
																	</c:choose>
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
												<button type="button" class="btn btn-success" id="saveEditOptions">Save changes</button>
												<button type="button" class="btn btn-default" id="closeEditOptions">Close</button>
											</div>
										</div>

									</div>
								</div>

								<!-- Modal -->
								<div class="modal fade" id="changeTariff" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Change Tariff</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div class="form-group">
														<label for="avail_tariffs" class="col-sm-2 control-label">Tariffs</label>
														<div class="col-sm-10">
															<select class="" id="avail_tariffs">
																<c:forEach var="tariff" items="${tariffs}">
																	<option>${tariff.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
												</form>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-success" id="saveChangeTariff">Save changes</button>
												<button type="button" class="btn btn-default" id="closeChangeTariff">Close</button>
											</div>
										</div>

									</div>
								</div>


							</div>
						</div>
					</div>



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
											<th>Actions</th>
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
												<td>
													<button type="button" class="btn btn-default editTariffButton" data-toggle="modal" data-target="#editTariff">Edit tariff</button>
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
											<th>Needed Options</th>
											<th>Actions</th>
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
													<c:forEach var="option1" items="${option.lockedOptions}">
														${option1.name};
													</c:forEach>
												</td>
												<td>
													<c:forEach var="option2" items="${option.neededOptions}">
														${option2.name};
													</c:forEach>
												</td>
												<td>
													<button type="button" class="btn btn-default editLockedOptionsButton">Locked options</button>
													<button type="button" class="btn btn-default editNeededOptionsButton">Needed options</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<!-- Modal -->
								<div class="modal fade" id="editLockedOptions" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Edit Option</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div id="locked_options_div" class="form-group col-sm-10 col-sm-offset-2">
														<label>Locked options</label>
														<c:forEach var="option" items="${options}">
															<div class="checkbox">
																<label>
																	<c:choose>
																		<c:when test="${option_disabledOptions.contains(option)}">
																			<input type="checkbox" disabled="disabled" name="edit_lock_options" value="${option.name}">${option.name}
																		</c:when>
																		<c:when test="${option_editOption.equals(option)}">
																			<input type="checkbox" disabled="disabled" name="edit_lock_options" value="${option.name}">${option.name}
																		</c:when>
																		<c:otherwise>
																			<input type="checkbox" name="edit_lock_options" value="${option.name}">${option.name}
																		</c:otherwise>
																	</c:choose>
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
												<button id="saveEditLockedOptionButton" type="button" class="btn btn-success">Save changes</button>
												<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
											</div>
										</div>

									</div>
								</div>



								<!-- Modal -->
								<div class="modal fade" id="editNeededOptions" role="dialog">
									<div class="modal-dialog">

										<!-- Modal content-->
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal">&times;</button>
												<h4 class="modal-title">Edit Option</h4>
											</div>
											<div class="modal-body">

												<form class="form-horizontal">
													<div id="needed_options_div" class="form-group col-sm-10 col-sm-offset-2">
														<label>Needed options</label>
														<c:forEach var="option" items="${options}">
															<div class="checkbox">
																<label>
																	<c:choose>
																		<c:when test="${option_disabledOptions.contains(option)}">
																			<input type="checkbox" disabled="disabled" name="edit_need_options" value="${option.name}">${option.name}
																		</c:when>
																		<c:when test="${option_editOption.equals(option)}">
																			<input type="checkbox" disabled="disabled" name="edit_need_options" value="${option.name}">${option.name}
																		</c:when>
																		<c:otherwise>
																			<input type="checkbox" name="edit_need_options" value="${option.name}">${option.name}
																		</c:otherwise>
																	</c:choose>
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
												<button id="saveEditNeededOptionButton" type="button" class="btn btn-success">Save changes</button>
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

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

				</div>

				<div id="myScripts">
					<script src="${pageContext.request.contextPath}/js/admin-lobby.js"></script>
				</div>
			</div>

		</div> <!-- /container -->



	</body>
</html>

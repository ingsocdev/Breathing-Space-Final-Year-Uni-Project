<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BreathingSpace - Login</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
<link href="static/third_party/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="static/third_party/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link href="static/css/style.css" rel="stylesheet">
<script src="static/js/Login.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Breathing Space</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="/breathingspace">Home</a></li>
					<li><a href="/breathingspace/dashboard">Dashboard</a></li>
					<li><a href="/breathingspace/archive">Archive</a></li>
				</ul>
			</div>
		</nav>
	<div class="col-sm-2"></div>
	<div class="col-sm-8 vertical-center horizontal-center">
		<div id="main-body">
			<form id="login-form" action="<c:url value='/j_spring_security_check' />" method="POST">
				<div class="form-group">
					<label for="username">Username:</label>
					<input name="username" type="text" class="form-control" />
				</div>
				<div class="form-group">
					<label for="password">Password:</label>
					<input name="password" type="password" class="form-control" />
				</div>
				<div class="btn-group">
					<button type="submit" class="btn btn-default">
						<span class="glyphicon glyphicon-user"></span>
						Login
					</button>
					<button id="create-account-button" type="button" class="btn btn-primary">Create Account</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			
			<form id="create-account-form" action="/breathingspace/createAccount" method="POST" class="collapse">
				<div class="form-group">
					<label path="username" for="username">Username:</label>
					<input path="username" name="username" type="text" class="form-control" />
				</div>
				<div class="form-group">
					<label path="email" for="email">Email:</label>
					<input path="email" name="email" type="email" class="form-control" />
				</div>
				<div class="form-group">
					<label path="password" for="password">Password:</label>
					<input path="password" name="password" type="password" class="form-control" />
				</div>
				<div class="btn-group">
					<button type="submit" class="btn btn-default">
						<span class="glyphicon glyphicon-user"></span>
						Create User
					</button>
					<button id="login-button" type="button" class="btn btn-primary">Existing Users</button>
				</div>

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			<c:choose>
				<c:when test="${success ne null}">
					<div id="error-box" class="alert alert-success">
						<strong>${success}</strong>
					</div>
				</c:when>
				<c:when test="${error ne null}">
					<div id="error-box" class="alert alert-danger">
						<strong>${error}</strong>
					</div>
				</c:when>
				<c:when test="${msg ne null}">
					<div id="error-box" class="alert alert-info">
						<strong>${msg}</strong>
					</div>
				</c:when>
			</c:choose>
		</div>
	</div>
	<div class="col-sm-2"></div>
</body>
</html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BreathingSpace - Archive</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
<link href="static/third_party/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="static/third_party/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!-- Mustache.js -->
<script src="static/third_party/mustache.js-2.3.0/mustache.min.js"></script>
<!-- BreathingSpace Stylesheet -->
<link href="static/css/style.css" rel="stylesheet">
<!-- BreathingSpace JS Classes -->
<script src="static/js/Common.js"></script>
<script src="static/js/AlertMessage.js"></script>
<script src="static/js/Archive.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<sec:authorize access="hasRole('ROLE_USER')">
		<nav class="navbar navbar-default collapse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Breathing Space</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="/breathingspace">Home</a></li>
					<li><a href="/breathingspace/dashboard">Dashboard</a></li>
					<li class="active"><a href="/breathingspace/Archive">Archive</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><p class="navbar-text">Logged in as user: ${pageContext.request.userPrincipal.name}</p></li>
					<li>
						<form action="<c:url value='/logout' />" method="post" id="logoutForm">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="submit" class="btn btn-danger navbar-btn btn-margin-left btn-margin-right"><span class="glyphicon glyphicon-log-out"></span> Logout</button>
						</form>
					</li>
				</ul>
			</div>
		</nav>
		<div id="main-body" class="col-sm-12">
			<c:forEach items="${userService.user.getNotes()}" var="note">
				<c:if test="${note.isArchived == 1}">
					<div class="note panel panel-info collapse">
						<div class="panel-heading clearfix">
							<h4 class="panel-title pull-left" style="padding-top: 7.5px;">
								<span class="glyphicon glyphicon-flag"></span>
								${note.title}
							</h4>
							<div class="pull-right">
								<button type="button" id="delete-note-${note.noteId}" class="btn btn-danger" data-toggle="tooltip" data-placement="bottom" title="Delete"><span class="glyphicon glyphicon-remove"></span></button>
							</div>
						</div>
						<div class="panel-body fixed-height-panel">
							<p id="description" name="description">${note.description}</p>
						</div>
						<div class="panel-footer">
							<div id="note-${note.noteId}-error-box" class="alert alert-danger collapse">
								<strong></strong>
							</div>
							<input type="hidden" name="noteId" value="${note.noteId}" />
							<button type="button" id="unarchive-note-${note.noteId}" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Unarchive"><span class="glyphicon glyphicon-inbox"></span></button>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</sec:authorize>
</body>
</html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BreathingSpace - Dashboard</title>
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
<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="static/third_party/bootstrap-datepicker-master/dist/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="static/third_party/bootstrap-datepicker-master/dist/css/bootstrap-datepicker3.css"/>
<!-- Mustache.js -->
<script src="static/third_party/mustache.js-2.3.0/mustache.min.js"></script>
<!-- BreathingSpace Stylesheet -->
<link href="static/css/style.css" rel="stylesheet">
<!-- BreathingSpace JS Classes -->
<script src="static/js/Common.js"></script>
<script src="static/js/AlertMessage.js"></script>
<script src="static/js/Dashboard.js"></script>

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
					<li class="active"><a href="/breathingspace/dashboard">Dashboard</a></li>
					<li><a href="/breathingspace/archive">Archive</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li>
						<button type="button" class="btn btn-info navbar-btn btn-margin-left" data-toggle="modal" data-target="#add-note-modal"><span class="glyphicon glyphicon-plus"></span> Note</button>
					</li>
					<li>
						<button type="button" class="btn btn-info navbar-btn btn-margin-left" data-toggle="modal" data-target="#add-reminder-modal"><span class="glyphicon glyphicon-plus"></span> Reminder</button>
					</li>
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
		<c:if test="${firstrun}">
			<div id="welcome" class="vertical-center horizontal-center collapse">
				<h1 id="message">Hello ${userService.user.username} :)</h1>
			</div>
		</c:if>
		<div id="main-body" class="col-sm-10">
			<c:forEach items="${userService.user.getNotes()}" var="note">
				<c:if test="${note.isArchived == 0}">
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
							<button type="button" id="edit-note-${note.noteId}" class="btn btn-info" data-toggle="tooltip" data-placement="bottom" title="Edit"><span class="glyphicon glyphicon-pencil"></span></button>
							<button type="button" id="archive-note-${note.noteId}" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" title="Archive"><span class="glyphicon glyphicon-ok"></span></button>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="col-sm-2">
			<div class="panel-group" id="accordian">
				<div class="panel panel-info collapse">
					<div class="panel-heading clearfix">
						<h4 class="panel-title pull-left" style="padding-top: 7.5px;">
							<span class="glyphicon glyphicon-flag"></span>
							<a data-toggle="collapse" data-parent="#accordian" href="#collapse1">User Stats</a>
						</h4>
					</div>
					<div id="collapse1" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="userLevel" value='${userService.user.level}' />
							<c:set var="userXp" value='${userService.user.xp}' />
							<span>Level ${userLevel} - ${userService.getLevelTagline()}</span>
							<div class="progress">
								<div class="progress-bar-info" role="progressbar" aria-valuenow="${userLevel}" aria-valuemin="0" aria-valuemax="100" style="width:${userLevel}%">${userLevel}/${userService.getMaxLevel()}</div>
							</div>
							<c:set var="percentageComplete" value="${userService.getPercentageComplete()}" />
							<span>XP ${percentageComplete}%</span>
							<div class="progress">
								<div class="progress-bar-success" role="progressbar" aria-valuenow="${userXp}" aria-valuemin="0" aria-valuemax="0" style="width: ${percentageComplete}%">${userXp}/${userService.getXpInLevel()}</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-info collapse">
					<div class="panel-heading clearfix">
						<h4 class="panel-title pull-left" style="padding-top: 7.5px;">
							<span class="glyphicon glyphicon-th-list"></span>
							<a data-toggle="collapse" data-parent="#accordian" href="#collapse2">Filters</a>
						</h4>
					</div>
					<div id="collapse2" class="panel-collapse collapse">
						<div class="panel-body">
							<button type="button" class="btn btn-info btn-md btn-margin-left btn-margin-bottom pull-left" id="toggle-notes-button">Notes <span class="badge">0</span></button>
							<button type="button" class="btn btn-info btn-md btn-margin-left btn-margin-top pull-left" id="toggle-reminders-button">Reminders <span class="badge">0</span></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="modals">
			<div id="add-note-modal" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times</button>
							<h4 class="modal-title">Add Note</h4>
						</div>
						<div class="modal-body">
							<form action="/breathingspace/dashboard/addNote">
								<div class="form-group">
									<input name="title" type="text" placeholder="Title" class="form-control" />
								</div>
								<div class="form-group">
									<textarea name="description" class="form-control" placeholder="Description" rows="5"></textarea>
								</div>
								<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Create Note</button>
								<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
							</form>
							<br />
							<div id="add-note-error-box" class="alert alert-danger collapse">
								<strong></strong>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="add-reminder-modal" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times</button>
							<h4 class="modal-title">Add Reminder</h4>
						</div>
						<div class="modal-body">
							<form action="/breathingspace/dashboard/addReminder">
								<div class="form-group">
									<input class="form-control" name="title" placeholder="Title" type="text" />
								</div>
								<div class="form-group">
									<input class="form-control datepicker" id="dueDate" name="date" placeholder="Due Date" type="text" />
								</div>
								<div class="form-group">
									<textarea name="description" class="form-control" placeholder="Description" rows="5"></textarea>
								</div>
								<button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span> Create Reminder</button>
								<button type="button" class="btn btn-danger" data-dismiss="modal">Cancel</button>
							</form>
							<br />
							<div id="add-reminder-error-box" class="alert alert-danger collapse">
								<strong></strong>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</body>
</html>
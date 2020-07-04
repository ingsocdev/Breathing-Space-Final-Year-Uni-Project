function Dashboard(common) {
	
	var common = common;
	this.numberOfNotes = 0;
	this.numberOfReminders = 0;
	
	this.init = function() {
		if($('#welcome').length) {
			$('#welcome').fadeIn(2000).fadeOut(1000, function(){
				$('#welcome').remove();
				$('.navbar').fadeIn(1000);
				$('.panel').fadeIn(1000);
			});
		}
		else {
			$('.navbar').fadeIn(1000);
			$('.panel').fadeIn(1000);
		}
		common.init();
	};
	
	this.resetModal = function(modal) {
		$(modal).find(input).val("");
		$(modal).find(textarea).val("");
	};
	
	this.updateNoteCount = function() {
		$("#toggle-notes-button").find("span").text(this.numberOfNotes);
	};
	
	this.attachNoteBindings = function(note) {
		$(note).find("button[id^='edit-note']").click(function(){				
			var editNoteModalTemplate = [
				"<div id='edit-note-modal' class='modal fade collapse' role='dialog'>",
					"<div class='modal-dialog'>",
						"<div class='modal-content'>",
							"<div class='modal-header'>",
								"<button type='button' class='close' data-dismiss='modal'>&times</button>",
									"<h4 class='modal-title'>Edit Note</h4>",
							"</div>",
							"<div class='modal-body'>",
								"<form action='/breathingspace/dashboard/editNote'>",
									"<div class='form-group'>",
										"<label for='title'>Title:</label>",
										"<input name='title' type='text' class='form-control' value='{{note_title}}' />",
									"</div>",
									"<div class='form-group'>",
										"<label for='description'>Description:</label>",
										"<textarea name='description' class='form-control' rows='5'>{{note_description}}</textarea>",
									"</div>",
									"<input type='hidden' name='noteId' value='{{note_id}}'/>",
									"<button type='submit' class='btn btn-success'><span class='glyphicon glyphicon-pencil'></span> Edit Note</button>",
									"<button type='button' class='btn btn-danger' data-dismiss='modal'>Cancel</button>",
									"<br />",
									"<div id='edit-note-error-box' class='alert alert-danger collapse'>",
										"<strong></strong>",
									"</div>",
								"</form>",
							"</div>",
						"</div>",
					"</div>",
				"</div>"
			].join("\n");
			
			var editNoteModalData = {note_title: note.find("h4.panel-title").text().trim(), note_description: note.find("p[name='description']").text(), note_id: note.find("input[name='noteId']").val()};
			var editNoteModal = $(Mustache.render(editNoteModalTemplate, editNoteModalData));
			
			$(editNoteModal).on('hidden.bs.modal', function () {
			    $(this).remove();
			})
			
			$(editNoteModal).find("form").submit(function(event){
				event.preventDefault();
				
				var $form = $(this);
				var title = $form.find("input[name='title']").val();
				var description = $form.find("textarea[name='description']").val();
				var noteId = editNoteModalData.note_id;
				var errorbox = $('#edit-note-error-box');
				var data = {noteId: noteId, title: title, description: description};
				
				if (title === "") {
					var alertMessage = new AlertMessage(errorbox, "Title cannot be empty!");
					alertMessage.danger();
				}
				else if (description === "") {
					var alertMessage = new AlertMessage(errorbox, "Description cannot be empty!");
					errorbox.find("strong").text("Description cannot be empty!");
				}
				else {
					var csrfToken = $("meta[name='_csrf']").attr("content");
					var csrfHeader = $("meta[name='_csrf_header']").attr("content");
					
					var callback = function(data) {
						
						var noteTitleTemplate = [
							"<h4 class='panel-title pull-left' style='padding-top: 7.5px;'>",
								"<span class='glyphicon glyphicon-flag'></span>",
								"{{note_title}}",
							"</h4>"
							].join("\n");
						
						var noteTitleData = {note_title: title};
						var editNoteModal = $(Mustache.render(noteTitleTemplate, noteTitleData));
						
						$(note).find("h4.panel-title").replaceWith(editNoteModal);
						$(note).find("p[id='description']").text(description);
						$(note).find("input[name='noteId']").val();
						
						var alertMessage = new AlertMessage(errorbox, "Alert updated successfully!");
						alertMessage.success();
						$('#edit-note-modal').modal("hide");
					};

					common.performAjaxPost(errorbox, $form.attr("action"), data, csrfHeader, csrfToken, callback);
				}
			});
			
			$("#modals").append(editNoteModal);
			editNoteModal.modal("show");
		});
		$(note).find("button[id^='archive-note']").click(function(){
			var errorbox = $(note).find("div[id$='error-box']");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var data = {noteId: $(note).find("input[name='noteId']").val(), archive: true};
			
			var self = this;
			var callback = function(data) {
				$(note).fadeOut(1000, function(){
					$(note).remove();
				});
				$(document).trigger("note:removed");
			};
			
			common.performAjaxPost(errorbox, "/breathingspace/archive/archiveNote", data, csrfHeader, csrfToken, callback);
		});
		$(note).find("button[id^='delete-note']").click(function(){
			var errorbox = $(note).find("div[id$='error-box']");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var data = {noteId: $(note).find("input[name='noteId']").val()};
			
			var self = this;
			var callback = function(data) {
				$(note).fadeOut(1000, function(){
					$(note).remove();
				});
			};
			$(document).trigger("note:removed");
			
			common.performAjaxPost(errorbox, "/breathingspace/dashboard/deleteNote", data, csrfHeader, csrfToken, callback);
		});
	};
}

$(document).ready(function() {
	var common = new Common();
	var dashboard = new Dashboard(common);
	dashboard.init();
	
	$('.datepicker').datepicker({
		format: "dd/mm/yyyy",
		todayHighlight: true
	});
	
	$('.note').each(function(){
		dashboard.attachNoteBindings($(this));
		dashboard.numberOfNotes += 1;
	}).promise().done(function(){
		dashboard.updateNoteCount();
	});
	
	$("#toggle-notes-button").click(function(){
		$(".note").fadeToggle("1000");
	});
	$("#toggle-notes-button").click(function(){
		$(".reminder").fadeToggle("1000");
	});
	
	$(document).on("note:added", function(event) {
		dashboard.numberOfNotes += 1;
		dashboard.updateNoteCount();
		$("[data-toggle='tooltip']").tooltip();
	});
	
	$(document).on("note:removed", function(event) {
		dashboard.numberOfNotes -= 1;
		dashboard.updateNoteCount();
	});
	
	$(document).on("reminder:added", function(event) {
		dashboard.numberOfNotes += 1;
		dashboard.updateReminderCount();
		$("[data-toggle='tooltip']").tooltip();
	});
	
	$(document).on("reminder:removed", function(event) {
		dashboard.numberOfReminders -= 1;
		dashboard.updateReminderCount();
	});
	
	$('#add-note-modal').find('form').submit(function(event) {
		event.preventDefault();
		
		var $form = $(this);
		var title = $form.find("input[name='title']").val();
		var description = $form.find("textarea[name='description']").val();
		var errorbox = $('#add-note-error-box');
		
		if (title === "") {
			var alertMessage = new AlertMessage(errorbox, "Title cannot be empty!");
			alertMessage.danger();
		}
		else if (description === "") {
			var alertMessage = new AlertMessage(errorbox, "Description cannot be empty!");
			errorbox.find("strong").text("Description cannot be empty!");
		}
		else {
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var data = {title: title, description: description};
			
			var callback = function(data) {
				
				var noteTemplate = [
					"<div class='note panel panel-info collapse'>",
						"<div class='panel-heading clearfix'>",
							"<h4 class='panel-title pull-left' style='padding-top: 7.5px;'>",
							"<span class='glyphicon glyphicon-flag'></span>",
								"{{note_title}}",
							"</h4>",
							"<div class='pull-right'>",
								"<button type='button' id='delete-note-{{note_id}}' class='btn btn-danger' data-toggle='tooltip' data-placement='bottom' title='Delete'><span class='glyphicon glyphicon-remove'></span></button>",
							"</div>",
						"</div>",
						"<div class='panel-body fixed-height-panel'>",
							"<p id='description' name='description'>{{note_description}}</p>",
						"</div>",
						"<div class='panel-footer'>",
							"<div id='note-{{note_id}}-error-box' class='alert alert-danger collapse'>",
								"<strong></strong>",
							"</div>",
							"<input type='hidden' name='noteId' value='{{note_id}}' />",
							"<button type='button' id='edit-note-{{note_id}}' class='btn btn-info' data-toggle='tooltip' data-placement='bottom' title='Edit'><span class='glyphicon glyphicon-pencil'></span></button>",
							"<button type='button' id='archive-note-{{note_id}}' class='btn btn-success' data-toggle='tooltip' data-placement='bottom' title='Archive'><span class='glyphicon glyphicon-ok'></span></button>",
						"</div>",
					"</div>"].join("\n");
				
				var noteData = {
					note_title: data.title,
					note_description: data.description,
					note_id: data.noteId
				};
				
				var newNote = $(Mustache.render(noteTemplate, noteData));
				dashboard.attachNoteBindings(newNote);
				$("#main-body").append(newNote);
				newNote.show();
				$(document).trigger("note:added");
				
				var alertMessage = new AlertMessage(errorbox, "Note created successfully!");
				alertMessage.success();
				$('#add-note-modal').modal("hide");
			};

			common.performAjaxPost(errorbox, $form.attr("action"), data, csrfHeader, csrfToken, callback);
		}
	});
	
	$('#add-reminder-modal').find('form').submit(function(event){
		event.preventDefault();
		
		var $form = $(this);
		var title = $form.find("input[name='title']").val();
		var description = $form.find("textarea[name='description']").val();
		var errorbox = $('#add-note-error-box');
		
		if (title === "") {
			var alertMessage = new AlertMessage(errorbox, "Title cannot be empty!");
			alertMessage.danger();
		}
		else if (description === "") {
			var alertMessage = new AlertMessage(errorbox, "Description cannot be empty!");
			errorbox.find("strong").text("Description cannot be empty!");
		}
		else {
			var csrfToken = $("meta[name='_csrf']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var data = {title: title, description: description};
			
			var callback = function(data) {
				
			var noteTemplate = [
				"<div class='note panel panel-info collapse'>",
					"<div class='panel-heading clearfix'>",
						"<h4 class='panel-title pull-left' style='padding-top: 7.5px;'>",
						"<span class='glyphicon glyphicon-flag'></span>",
							"{{note_title}}",
						"</h4>",
						"<div class='pull-right'>",
							"<button type='button' id='delete-note-{{note_id}}' class='btn btn-danger' data-toggle='tooltip' data-placement='bottom' title='Delete'><span class='glyphicon glyphicon-remove'></span></button>",
						"</div>",
					"</div>",
					"<div class='panel-body fixed-height-panel'>",
						"<p id='description' name='description'>{{note_description}}</p>",
					"</div>",
					"<div class='panel-footer'>",
						"<div id='note-{{note_id}}-error-box' class='alert alert-danger collapse'>",
							"<strong></strong>",
						"</div>",
						"<input type='hidden' name='noteId' value='{{note_id}}' />",
						"<button type='button' id='edit-note-{{note_id}}' class='btn btn-info' data-toggle='tooltip' data-placement='bottom' title='Edit'><span class='glyphicon glyphicon-pencil'></span></button>",
						"<button type='button' id='archive-note-{{note_id}}' class='btn btn-success' data-toggle='tooltip' data-placement='bottom' title='Archive'><span class='glyphicon glyphicon-ok'></span></button>",
					"</div>",
				"</div>"].join("\n");
				
				var noteData = {
					note_title: data.title,
					note_description: data.description,
					note_id: data.noteId
				};
				
				var newNote = $(Mustache.render(noteTemplate, noteData));
				dashboard.attachNoteBindings(newNote);
				$("#main-body").append(newNote);
				newNote.show();
				
				var alertMessage = new AlertMessage(errorbox, "Reminder created successfully!");
				alertMessage.success();
				$('#add-note-modal').modal("hide");
			};

			common.performAjaxPost(errorbox, $form.attr("action"), data, csrfHeader, csrfToken, callback);
		}
	});
	
});
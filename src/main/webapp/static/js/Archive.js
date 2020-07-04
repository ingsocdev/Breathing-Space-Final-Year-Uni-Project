$(document).ready(function() {
	function Archive(common) {
		
		var common = common;
		
		this.init = function() {
			$('.navbar').fadeIn(1000);
			$('.panel').fadeIn(1000);
			common.init();
		};
		
		this.attachNoteBindings = function(note) {
			$(note).find("button[id^='unarchive-note']").click(function(){	
				var errorbox = $(note).find("div[id$='error-box']");
				var csrfHeader = $("meta[name='_csrf_header']").attr("content");
				var csrfToken = $("meta[name='_csrf']").attr("content");
				var data = {noteId: $(note).find("input[name='noteId']").val(), archive: false};
				
				var callback = function(data) {
					$(note).fadeOut(1000, function(){
						$(note).remove();
					});
				};
				
				common.performAjaxPost(errorbox, "/breathingspace/archive/archiveNote", data, csrfHeader, csrfToken, callback);
			});
			$(note).find("button[id^='delete-note']").click(function(){
				var errorbox = $(note).find("div[id$='error-box']");
				var csrfHeader = $("meta[name='_csrf_header']").attr("content");
				var csrfToken = $("meta[name='_csrf']").attr("content");
				var data = {noteId: $(note).find("input[name='noteId']").val()};
				
				var callback = function(data) {
					$(note).fadeOut(1000, function(){
						$(note).remove();
					});
				};
				
				common.performAjaxPost(errorbox, "/breathingspace/dashboard/deleteNote", data, csrfHeader, csrfToken, callback);
			});
		};
	};
	
	var archive = new Archive(new Common());
	archive.init();

	$('.note').each(function(){
		archive.attachNoteBindings($(this));
	});
});
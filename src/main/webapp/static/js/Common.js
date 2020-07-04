var Common = function () {
	
	this.init = function() {
		$("[data-toggle='tooltip']").tooltip();
	}

	this.performAjaxPost = function(errorbox, action, data, csrfHeader, csrfToken, callback) {
				$.ajax({
					type : "POST",
					url : action,
					data : data,
					timeout : 100000,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(csrfHeader, csrfToken);
					},
					success : function(data) {
						callback(data);
					},
					error : function(e) {
						var alertMessage = new AlertMessage(errorbox, "An error has occurred during form processing.");
						alertMessage.danger();
						console.log(e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
			}
};
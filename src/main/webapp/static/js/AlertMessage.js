function AlertMessage (errorbox, description) {
	
	this.errorbox = errorbox;
	this.description = description;
	
	this.success = function () {
		$(this.errorbox).removeClass();
		$(this.errorbox).addClass("alert alert-success collapse");
		this.displayAlert();
	}
	
	this.info = function () {
		$(this.errorbox).removeClass();
		$(this.errorbox).addClass("alert alert-info collapse");
		this.displayAlert();
	}
	
	this.danger = function () {
		$(this.errorbox).removeClass();
		$(this.errorbox).addClass("alert alert-danger collapse");
		this.displayAlert();
	}
	
	this.displayAlert = function() {
		errorbox.find("strong").text(this.description);
		errorbox.fadeIn(1000, function() {
			errorbox.fadeOut(5000);
		});
	}
}
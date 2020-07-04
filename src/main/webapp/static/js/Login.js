$(document).ready(function() {
	function Login() {
		
		this.init = function() {
			// Expire the notification after displaying for a reasonable period.
			$('#error-box').fadeOut(5000);
		}
		
		$('#create-account-button').click(function(){
			$('#login-form').fadeOut(1000, function(){
				$('#create-account-form').fadeIn(1000);
			});
		});
		
		$('#login-button').click(function(){
			$('#create-account-form').fadeOut(1000, function(){
				$('#login-form').fadeIn(1000);
			});
		});

	}

	var login = new Login();
	login.init();
});
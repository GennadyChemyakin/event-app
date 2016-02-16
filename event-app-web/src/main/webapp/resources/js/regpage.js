    //Function checks that email is valid
    function checkEmail() {
        var email = $('#email');
        matches = email.val().match(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/);
        return matches != null;
     }
    //Function checks that login consists of valid letters and it's length is more than 3
     function checkLogin() {
     	var login    = $('#username').val();
        var expLogin = /^[a-zA-Z0-9_]+$/g;
        return login.length > 3 && login.search(expLogin) != -1;
     }
    //Function checks that passwords are equal
     function pwdEqual() {
     	var pwd  = $('#pwd').val();
        var cpwd = $('#cpwd').val();
        return pwd.match(cpwd) && cpwd.match(pwd);
     }
    //Function checks that password length is more than 3 symbols
     function pwdLen(pass) {
     	return pass.val().length > 3;
     }
    //Function mark input field with appropriate classes
    //@param fiels - input field to be marked
    //@isGreen - bool type, condition for choosing color for the field
     function markField(field, isGreen) {

       var parentField = field.parent();
       var glyph = parentField.find('.glyphicon:first');
       var errorField  = parentField.find('.alert:first').hide();
       if(!isGreen) {
          parentField.removeClass('has-success');
          parentField.addClass('has-error');
          glyph.removeClass('glyphicon-ok');
          glyph.addClass('glyphicon-remove');
       } else {
          parentField.addClass('has-success');
          parentField.removeClass('has-error');
          glyph.removeClass('glyphicon-remove');
          glyph.addClass('glyphicon-ok');
       }

     }
    //function adds text message under needed field
    //@param field - field under which message should appear
    //@param text  - message text
     function addErrorMessage(field, text) {
        var parentField = field.parent();
        var errorField  = parentField.find('.alert:first');
        errorField.text(text);
        errorField.show();
     }

     //function adds message in the middle of the page
     function addMainMessage(success, errormsg) {
     	var field = $('#msg_main');
        if(success) {
        	field.addClass('alert-success').text("Your registration passed successfully!");

        } else {
        	field.addClass('alert-danger').text(errormsg);
        }
        field.show();
     }
    //listeners for input fields
    //and submit button click
     $(document).ready(function() {
		$('.alert').hide();
		$('#username').keyup(function(){
          markField($('#username'), checkLogin());
        });

        $('#email').keyup(function(){
          markField($('#email'), checkEmail());
        });

        $('#pwd').keyup(function(){
          var pwd = $('#pwd');
          var pwd2 = $('#cpwd');
           if(pwd2.val().length != 0) {
           		markField(pwd, pwdEqual());
              markField(pwd2, pwdEqual());
           } else {
           		markField(pwd, pwdLen(pwd));
           }
        });

        $('#cpwd').keyup(function(){
          var pwd = $('#pwd');
          var pwd2 = $('#cpwd');
          $('.alert').hide();
           if(pwd.val().length != 0) {
           		markField(pwd2, pwdEqual());
              markField(pwd, pwdEqual());
           } else {
           		markField(pwd2, pwdLen(pwd2));
           }
        });


        $("#register_btn").click(function() {
        var username    = $("#username").val();
        var email       = $("#email").val();
        var password    = $("#pwd").val();
        var password2   = $("#cpwd").val();
        var noMistakes = true;
        if (!checkLogin()) {
            markField($("#username"), false);
            addErrorMessage($("#username"), "Login must be at least 3 characters long.. \n Must " + "contain only letters, numbers and underscore");
				}
				if(!checkEmail()) {
        		markField($("#email"), false);
            addErrorMessage($("#email"), "Enter valid email address");
            noMistakes = false;
        }

        if(!pwdLen($("#pwd"))) {
        		markField($("#pwd"), false);
            addErrorMessage($("#pwd"), "Password should be at least 4 characters long");
            noMistakes = false;
        }

        if(!pwdEqual()) {
        		markField($("#pwd"), false);
            markField($("#cpwd"), false);
            addErrorMessage($("#cpwd"), "Passwords does not match!!!");
            noMistakes = false;
        }

        if(noMistakes) {

            $("#register_btn").addClass('disabled').attr('disabled', 'disabled');

            var data = JSON.stringify({
                username: username,
                email: email,
                password: password
            });

            $.ajax({
                type: "POST",
                url: "registration",
                data: data,
                contentType: "application/json",
                success: function (data) {
					addMainMessage(true);
                    setTimeout(function() {
                       window.location.href = "/event-app/home.html"}, 2000);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    $('#register_btn').removeClass('disabled').prop("disabled", false);
                    addMainMessage(false, "Oops.. Something went wrong");
                }
            });

        }

        });
        });
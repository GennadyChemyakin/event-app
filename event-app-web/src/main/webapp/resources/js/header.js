$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/event-app/user/current"
    }).then(function (data) {
        if (data.username) {
            $("#logInButton").css("display", "none");
            $("#signUpButton").css("display", "none");
            $("#dropDownMenuText").text(data.username);
            $("#dropDownMenu").css("display", "block");
        } else {
            $("#logInButton").css("display", "block");
            $("#signUpButton").css("display", "block");
            $("#dropDownMenu").css("display", "none");
        }
    });
    $("#logInButton").click(function () {
        window.location.href = "/event-app/login.html";
    });
    $("#signUpButton").click(function () {
        window.location.href = "/event-app/register.html";
    });
    $("#addEventButton").click(function () {
        window.location.href = "/event-app/new-event.html";
    });
});

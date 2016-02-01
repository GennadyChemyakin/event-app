$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/user/current"
    }).then(function(data){

    });
    $("#signInButton").click(function () {
        window.location.href = "/event-app/login.html";
    });
    $("#signUnButton").click(function () {
        window.location.href = "/event-app/register.html";
    });
    $("#addEventButton").click(function () {
        window.location.href = "/event-app/new-event.html";
    });
});

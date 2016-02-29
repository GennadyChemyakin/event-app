$(document).ready(function () {

    $('#profile').click(function() {
         $('#credentials_screen').collapse("hide");
         $('#profile_screen').collapse('show');
         $(".nav-stacked > li").toggleClass("active");
    });

    $('#credentials').click(function() {
        $('#profile_screen').collapse("hide");
        $('#credentials_screen').collapse("show");
        $(".nav-stacked > li").toggleClass("active");
    });

    $("#file-4").attr("data-upload-url", "images/users/" + window.username.id);

    $("#user_photo").attr("src", "/event-app/images/users/" + window.username.id);

});
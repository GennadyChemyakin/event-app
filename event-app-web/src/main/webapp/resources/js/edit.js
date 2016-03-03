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

    $.ajax({
            type: "GET",
            url: "/event-app/user/current"
    }).then(function (data) {

        $("#user_photo").attr("src", "/event-app/image/user/" + data.username );
        $("#file-4").attr("data-upload-url", "/event-app/image/user/" + data.username );
        var $input = $('input.file[type=file]');
        if ($input.length) {
            $input.fileinput();
        }
    });

});

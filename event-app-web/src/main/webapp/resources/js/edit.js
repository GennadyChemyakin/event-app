$(document).ready(function () {

//    $('.collapse').collapse();

//    $.ajax({
//        type: "GET",
//        url: "/event-app/profile/" + urlParam("id")
//    }).then(function (data) {
//
//
//
//
//    });


    $('#profile').click(function() {
         $('#credentials_screen').collapse("hide");
         $('#profile_screen').collapse('show');
         $("li").toggleClass("active");
    });

    $('#credentials').click(function() {
        $('#profile_screen').collapse("hide");
        $('#credentials_screen').collapse("show");
        $("li").toggleClass("active");
    });

});
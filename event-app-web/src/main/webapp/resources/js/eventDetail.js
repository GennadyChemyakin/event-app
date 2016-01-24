$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/event-app/event/" + urlParam("id")
    }).then(function (data) {

        var event = {};
        event.name = data.name;
        event.description = data.description != null ? data.description : "no description";
        event.date = null;
        if (data.timeStamp != null) {
            event.date = new Date(data.timeStamp.year, data.timeStamp.monthValue - 1, data.timeStamp.dayOfMonth,
                data.timeStamp.hour, data.timeStamp.minute);
        }
        event.user = {};
        event.user.username = data.user.username;
        event.user.name = data.user.name != null ? data.user.name : "";
        event.user.surname = data.user.surname != null ? data.user.surname : "";
        event.country = data.country != null ? data.country : "";
        event.city = data.city != null ? data.city : "";
        event.location = data.location != null ? data.location : "";


        //event
        $('#eventName').text(event.name);
        $('#description').text(event.description);
        $('#address').append(" " + (event.country + " " + event.city + " " + event.location).trim());
        if (event.date != null) {
            $('#time').append(" " + event.date.toLocaleDateString() + " " + event.date.toLocaleTimeString());
        }
        else {
            $('#time').append(" - -");
        }
        //user
        $('#username').text(event.user.username.trim());
        $('#name').text((event.user.name + " " + event.user.surname).trim());
    });
});

//getting params from request
function urlParam(name) {
    var params = window.location.href.slice(window.location.href.indexOf('?') + 1);
    var results = new RegExp(name + '=([^&#]*)').exec(params);
    if (results == null) {
        return null;
    }
    else {
        return results[1];
    }
}

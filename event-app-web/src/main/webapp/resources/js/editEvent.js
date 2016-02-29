$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/event-app/event/" + urlParam("id")
    }).then(function (data) {

        var event = {};
        event.name = data.name;
        event.description = data.description != null ? data.description : "no description";
        event.eventTime = null;

        if (data.eventTime != null) {
            event.eventTime = new Date(data.eventTime[0], data.eventTime[1] - 1, data.eventTime[2],
                data.eventTime[3], data.eventTime[4]);
        }
        event.country = data.country != null ? data.country : "";
        event.city = data.city != null ? data.city : "";
        event.location = data.location != null ? data.location : "";

        $('#eventName').val(event.name);
        $('#description').val(event.description);
        $('#country').val(event.country);
        $('#city').val(event.city);
        $('#location').val(event.location);
        if (event.eventTime != null) {
            $('#picker').val(event.eventTime.getFullYear() + "/" + (event.eventTime.getUTCMonth() + 1) + "/" + event.eventTime.getUTCDate());
        }


        //instantiate datePicker with appropriate settings
        $.datetimepicker.setLocale('en');
        var now = new Date();
        var dateStr = now.getUTCFullYear() + "/" + (now.getUTCMonth() + 1) + "/" + now.getUTCDate();
        var timeStr = now.getHours() + ':' + now.getMinutes();
        $('#picker').datetimepicker({
            formatTime: 'H:i',
            formatDate: 'Y:m:d',
            defaultDate: dateStr,
            defaultTime: timeStr,
            timepickerScrollbar: false

        });
    });

    $('#editEventButton').click(function () {
        if ($('#eventName').val() && window.username) {
            var dateString = getDateStringISOWithOffset($('#picker').val());
            dateString = dateString.slice(0, dateString.length - 1);
            var eventJson = JSON.stringify({
                "name": $('#eventName').val(),
                "description": $('#description').val(),
                "country": $('#country').val(),
                "city": $('#city').val(),
                "location": $('#location').val(),
                "eventTime": dateString
            })
            $.ajax({
                type: "PUT",
                url: "/event-app/event/" + urlParam("id"),
                contentType: "application/json; charset=utf-8",
                data: eventJson
            }).then(function (data, statusText, xhr) {
                if (xhr.status == 200) {
                    $('#successAlert').slideDown();
                    setTimeout(function () {
                        $('#successAlert').slideUp();
                    }, 3000);
                }
            }, function () {
                $('#errorUpdateAlert').slideDown();
                setTimeout(function () {
                    $('#errorUpdateAlert').slideUp();
                }, 3000);
            })
        }
    });
    $('#eventName').keyup(function () {
        if (!$('#eventName').val()) {
            var parent = $('#eventName').parent();
            parent.removeClass('has-success');
            parent.addClass('has-error');
            parent.find('.alert:first').show();
        } else {
            var parent = $('#eventName').parent();
            parent.removeClass('has-error');
            parent.addClass('has-success')
            parent.find('.alert:first').hide();
        }
    });

    $("#returnToEvent").click(function () {
        window.location.href = "/event-app/detail.html?id=" + urlParam("id");
    });

    $('input').keyup(function () {
        $('#successAlert').slideUp();
    });
    $('textarea').keyup(function () {
        $('#successAlert').slideUp();
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

//TODO  change all sending time to UTC in EA-38
function getDateStringISOWithOffset(commentDateString) {
    var timezoneOffset = new Date().getTimezoneOffset();
    var commentDate;
    if (commentDateString) {
        commentDate = new Date(commentDateString);
        //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
        commentDate = new Date(commentDate.getTime() - timezoneOffset * 60000).toISOString();
    }
    return commentDate;
}

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
    }).then(function () {
        var timezoneOffset = new Date().getTimezoneOffset();
        $.ajax({
            type: "GET",
            url: "/event-app/comment?eventId=" + urlParam("id") + "&commentTime=" + new Date(new Date().getTime() - timezoneOffset * 60000).toISOString()
        }).then(showComments);
    });
    $('#loadComments').click(function () {
        var lastCommentDate = $("#commentTime" + $(".commentRow:first").attr("id")).text();
        var timezoneOffset = new Date().getTimezoneOffset();
        if (lastCommentDate) {
            lastCommentDate = new Date(lastCommentDate);
            //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
            lastCommentDate = new Date(lastCommentDate.getTime() - timezoneOffset * 60000).toISOString();
        } else {
            lastCommentDate = new Date();
            lastCommentDate = new Date(lastCommentDate.getTime() - timezoneOffset * 60000).toISOString();
        }
        $.ajax({
            type: "GET",
            url: "/event-app/comment?eventId=" + urlParam("id") + "&commentTime=" + lastCommentDate
        }).then(showComments)
    });

});


function showComments(data) {
    for (var i = 0; i < data.commentVOList.length; i++) {
        arg = data.commentVOList[i];
        var comment = {};
        comment.id = arg.id;
        comment.remainingCommentsCount = data.remainingCommentsCount;
        comment.message = arg.message;
        comment.username = arg.username;
        comment.date = null;
        if (arg.timeStamp != null) {
            comment.date = new Date(arg.timeStamp.year, arg.timeStamp.monthValue - 1, arg.timeStamp.dayOfMonth,
                arg.timeStamp.hour, arg.timeStamp.minute, arg.timeStamp.second, arg.timeStamp.nano);
        }
        $('<div class="row commentRow"> <div class="col-md-2"> <div class="thumbnail"> ' +
            '<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png"> ' +
            '</div> </div> <div class="col-md-10"> <div class="panel panel-default"> <div class="panel-heading">' +
            ' <strong class= "commentUsername"></strong> <span class="text-muted commentTime"></span> </div> ' +
            '<div class="panel-body" ><span class="commentMessage"></span></div> </div> </div> </div> ').
            attr('id', comment.id).css("display", "none").prependTo('#addCommentPanel');

        $("#" + comment.id).find("strong.commentUsername").attr("id", 'commentUsername' + comment.id);
        $("#" + comment.id).find("span.commentTime").attr("id", 'commentTime' + comment.id);
        $("#" + comment.id).find("span.commentMessage").attr("id", 'commentMessage' + comment.id);
        $("#" + 'commentUsername' + comment.id).text(comment.username);
        $("#" + 'commentTime' + comment.id).text(comment.date.toString());
        $("#" + 'commentMessage' + comment.id).text(comment.message);
        $("#" + comment.id).slideDown();

        if (comment.remainingCommentsCount == 0) {
            $('#loadComments').hide();
        } else if (comment.remainingCommentsCount < 10) {
            $('#loadComments').text("Load previous " + comment.remainingCommentsCount + " comment(s)");
        } else {
            $('#loadComments').text("Load previous 10 comments of " + comment.remainingCommentsCount);
        }
    }
}

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

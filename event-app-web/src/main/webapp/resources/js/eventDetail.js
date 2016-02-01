$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "/event-app/event/" + urlParam("id")
    }).then(function (data) {

        window.timezoneOffset = new Date().getTimezoneOffset();

        var event = {};
        event.name = data.name;
        event.description = data.description != null ? data.description : "no description";
        event.date = null;
        if (data.timeStamp != null) {
            event.date = new Date(data.timeStamp[0], data.timeStamp[1] - 1, data.timeStamp[2],
                data.timeStamp[3], data.timeStamp[4]);
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
        $.ajax({
            type: "GET",
            url: "/event-app/comment?eventId=" + urlParam("id") + "&commentTime=" + new Date(new Date().getTime() - window.timezoneOffset * 60000).toISOString()
        }).then(showComments);
    });
    $('#loadComments').click(function () {
        var lastCommentDate = $("#commentTime" + $(".commentRow:first").attr("id")).text();
        if (lastCommentDate) {
            lastCommentDate = new Date(lastCommentDate);
            //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
            lastCommentDate = new Date(lastCommentDate.getTime() - window.timezoneOffset * 60000).toISOString();
        } else {
            lastCommentDate = new Date();
            lastCommentDate = new Date(lastCommentDate.getTime() - window.timezoneOffset * 60000).toISOString();
        }
        $.ajax({
            type: "GET",
            url: "/event-app/comment?eventId=" + urlParam("id") + "&commentTime=" + lastCommentDate
        }).then(showComments)
    });
    $('#loadCommentsPanel').mouseenter(function () {
        $(this).css("background-color", "red");
    });
    $('#addCommentButton').click(function () {
        var message = $('#commentArea').val();
        var firstCommentDate = $("#commentTime" + $(".commentRow:last").attr("id")).text();
        if (firstCommentDate) {
            firstCommentDate = new Date(firstCommentDate);
            //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
            firstCommentDate = new Date(firstCommentDate.getTime() - window.timezoneOffset * 60000).toISOString();
        } else {
            firstCommentDate = new Date();
            firstCommentDate = new Date(firstCommentDate.getTime() - window.timezoneOffset * 60000).toISOString();
        }
        if (message) {
            var commentTime = new Date(new Date().getTime() - window.timezoneOffset * 60000).toISOString();
            commentTime = commentTime.slice(0, commentTime.length - 1);
            $.ajax({
                type: "POST",
                url: "/event-app/comment?after=" + firstCommentDate,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    "eventId": urlParam("id"),
                    "message": message,
                    "commentTime": commentTime,
                    "username": "username"
                }),
            }).then(showNewComments).then(function () {
                $('#commentArea').val("");
            });
        }
    });

});


function showComments(data) {
    for (var i = 0; i < data.commentVOList.length; i++) {
        var comment = getComment(data.commentVOList[i]);
        $('<div class="row commentRow"> <div class="col-md-2"> <div class="thumbnail"> ' +
            '<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png"> ' +
            '</div> </div> <div class="col-md-10"> <div class="panel panel-default"> <div class="panel-heading">' +
            ' <strong class= "commentUsername"></strong> <span class="text-muted commentTime"></span> </div> ' +
            '<div class="panel-body" ><span class="commentMessage"></span></div> </div> </div> </div> ').
            attr('id', comment.id).css("display", "none").prependTo('#commentPanel');

        $("#" + comment.id).find("strong.commentUsername").attr("id", 'commentUsername' + comment.id);
        $("#" + comment.id).find("span.commentTime").attr("id", 'commentTime' + comment.id);
        $("#" + comment.id).find("span.commentMessage").attr("id", 'commentMessage' + comment.id);
        $("#" + 'commentUsername' + comment.id).text(comment.username);
        $("#" + 'commentTime' + comment.id).text(comment.date.toString());
        $("#" + 'commentMessage' + comment.id).text(comment.message);
        $("#" + comment.id).slideDown();
    }
    if (data.remainingCommentsCount == 0) {
        $('#loadComments').hide();
    } else {
        $('#loadComments').text("Load previous 10 comments of " + comment.remainingCommentsCount);
    }
}

function showNewComments(data) {
    for (var i = 0; i < data.length; i++) {
        var comment = getComment(data[i]);
        $('<div class="row commentRow"> <div class="col-md-2"> <div class="thumbnail"> ' +
            '<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png"> ' +
            '</div> </div> <div class="col-md-10"> <div class="panel panel-default"> <div class="panel-heading">' +
            ' <strong class= "commentUsername"></strong> <span class="text-muted commentTime"></span> </div> ' +
            '<div class="panel-body" ><span class="commentMessage"></span></div> </div> </div> </div> ').
            attr('id', comment.id).css("display", "none").appendTo("#commentPanel");

        $("#" + comment.id).find("strong.commentUsername").attr("id", 'commentUsername' + comment.id);
        $("#" + comment.id).find("span.commentTime").attr("id", 'commentTime' + comment.id);
        $("#" + comment.id).find("span.commentMessage").attr("id", 'commentMessage' + comment.id);
        $("#" + 'commentUsername' + comment.id).text(comment.username);
        $("#" + 'commentTime' + comment.id).text(comment.date.toString());
        $("#" + 'commentMessage' + comment.id).text(comment.message);
        $("#" + comment.id).slideDown();
    }
}

function getComment(data) {
    var comment = {};
    comment.id = data.id;
    comment.message = data.message;
    comment.username = data.username;
    comment.date = null;
    if (data.commentTime != null) {
        var year = data.commentTime[0];
        var month = data.commentTime[1];
        var day = data.commentTime[2];
        var hour = data.commentTime[3] ? data.commentTime[3] : 0;
        var minutes = data.commentTime[4] ? data.commentTime[4] : 0;
        var seconds = data.commentTime[5] ? data.commentTime[5] : 0;
        comment.date = new Date(year, month - 1, day, hour, minutes, seconds);
    }
    return comment;
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

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

        if (data.eventTime != null) {
            event.date = new Date(data.eventTime[0], data.eventTime[1] - 1, data.eventTime[2],
                data.eventTime[3], data.eventTime[4]);
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
            url: "/event-app/comment?eventId=" + urlParam("id") + "&before=" + getCommentDateOrNow()
        }).then(showComments);
    });
    $('#loadComments').click(function () {
        var lastCommentDate = getCommentDateOrNow($("#commentISOTime" + $(".commentRow:first").attr("id")).text());
        $.ajax({
            type: "GET",
            url: "/event-app/comment?eventId=" + urlParam("id") + "&before=" + lastCommentDate
        }).then(showComments)
    });
    $('#addCommentButton').click(function () {
        if (window.username) {
            var message = $('#commentArea').val();
            var firstCommentDateString = $("#commentISOTime" + $(".commentRow:last").attr("id")).text();
            var firstCommentDate = getCommentDateOrNow(firstCommentDateString);
            if (!firstCommentDateString) {
                firstCommentDate = new Date(new Date(firstCommentDate).getTime() - 60).toISOString();
            }
            if (message) {
                var commentTime = getCommentDateOrNow();
                commentTime = commentTime.slice(0, commentTime.length - 1);
                $.ajax({
                    type: "POST",
                    url: "/event-app/comment",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify({
                        "eventId": urlParam("id"),
                        "message": message,
                        "commentTime": commentTime
                    })
                }).then(function () {
                    $.ajax({
                        type: "GET",
                        url: "/event-app/comment/new?eventId=" + urlParam("id") + "&after=" + firstCommentDate
                    }).then(function (data) {
                        showNewComments(data);
                        $('#commentArea').val("");
                    })
                });
            }
        } else {
            $('#addCommentFailMessage').slideDown();
        }
    });

});

//return local date made from commentDateString or now if commentDateString == null
function getCommentDateOrNow(commentDateString) {
    var commentDate;
    if (commentDateString) {
        commentDate = new Date(commentDateString);
        //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
        commentDate = new Date(commentDate.getTime() - window.timezoneOffset * 60000).toISOString();
    } else {
        commentDate = new Date();
        commentDate = new Date(commentDate.getTime() - window.timezoneOffset * 60000).toISOString();
    }
    return commentDate;
}

//function for displaying list of comments
function showComments(data) {
    for (var i = 0; i < data.commentVOList.length; i++) {
        var comment = buildComment(data.commentVOList[i]);
        displayCommentary(comment, 1);
    }
    if (data.remainingCommentsCount == 0) {
        $('#loadComments').hide();
    } else if(data.remainingCommentsCount < 10) {
        $('#loadComments').text("Load previous " + data.remainingCommentsCount + " comment(s)");
    } else {
        $('#loadComments').text("Load previous 10 comments of " + data.remainingCommentsCount);
    }
}

//function for displaying list of new comments
function showNewComments(data) {
    for (var i = 0; i < data.length; i++) {
        var comment = buildComment(data[i]);
        displayCommentary(comment, 0);
    }
}


//function to add commentary html to page
//mode = 0 - add comment to the end of comment list
//mode = 1 - add comment to the top of comment list
function displayCommentary(comment, mode) {
    var commentaryHtml = $('<div class="row commentRow"> <div class="col-md-2"> <div class="thumbnail"> ' +
        '<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png"> ' +
        '</div> </div> <div class="col-md-10"> <div class="panel panel-default"> <div class="panel-heading">' +
        '<div class = "col-md-2"><strong class= "commentUsername"></strong></div><div class="col-md-8"><span class= "commentISOTime" hidden></span> <span class="text-muted commentTime">' +
        '</span></div><div class="col-md-offset-11"><a role="button" class="deleteCommentButton" style="color:transparent">Delete</a></div></div>' +
        '<div class="panel-body" ><span class="commentMessage"></span></div> </div> </div> </div> ').
        attr('id', comment.id).css("display", "none");

    if (mode == 0) {
        commentaryHtml.appendTo("#commentPanel");
    } else if (mode == 1) {
        commentaryHtml.prependTo('#commentPanel');
    }
    $("#" + comment.id).find("strong.commentUsername").attr("id", 'commentUsername' + comment.id);
    $("#" + comment.id).find("span.commentTime").attr("id", 'commentTime' + comment.id);
    $("#" + comment.id).find("span.commentISOTime").attr("id", 'commentISOTime' + comment.id);
    $("#" + comment.id).find("span.commentMessage").attr("id", 'commentMessage' + comment.id);
    $("#" + comment.id).find("a.deleteCommentButton").attr("id", 'deleteCommentButton' + comment.id);
    $("#" + 'commentUsername' + comment.id).text(comment.username);
    $("#" + 'commentTime' + comment.id).text(comment.date.toLocaleString());
    $("#" + 'commentISOTime' + comment.id).text(comment.date.toISOString());
    $("#" + 'commentMessage' + comment.id).text(comment.message);
    if (window.username == $('#username').text() || window.username == comment.username) {
        $("#deleteCommentButton" + comment.id).css("color", "black");
        $("#deleteCommentButton" + comment.id).bind("click", function () {
            var id = $(this).attr("id").slice('deleteCommentButton'.length, $(this).attr("id").length);
            var username = $("#commentUsername" + id).text();
            $.ajax({
                type: "DELETE",
                url: "/event-app/comment",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    "id": id,
                    "eventId": urlParam("id"),
                    "username": username
                })
            }).then(function (data, statusText, xhr) {
                if (xhr.status == 204) {
                    $("#" + id).slideUp();
                }
            })
        })
    }
    $("#" + comment.id).slideDown();
}

//function for building comment object from Json
function buildComment(data) {
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
        var nano = data.commentTime[6] ? data.commentTime[6] : 0;
        comment.date = new Date(year, month - 1, day, hour, minutes, seconds, nano / 1000000);
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

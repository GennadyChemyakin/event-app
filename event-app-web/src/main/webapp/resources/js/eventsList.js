$(document).ready(function () {
            var timezoneOffset = new Date().getTimezoneOffset();
            $.ajax({
                type: "GET",
                url: "/event-app/events.html?creationTime=" + new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString()
            }).then(showEvents);
});

function showEvents(data) {
    for(var i = 0; i < data.eventPreviewVOList.length; i++) {
        arg = data.eventPreviewVOList[i];
        var event = {};
        event.name = arg.name;
        event.creator = arg.creator;
        event.description = arg.description;
        event.country = arg.country;
        event.city = arg.city;
        event.location = arg.location;
        event.numberOfComments = arg.numberOfComments;
        event.picture = arg.picture;
        event.eventTime = null;
        if (arg.eventTime != null) {
            event.eventTime = new Date(arg.eventTime.year, arg.eventTime.monthValue - 1, arg.eventTime.dayOfMonth,
                            arg.eventTime.hour, arg.eventTime.minute, arg.eventTime.second, arg.eventTime.nano);
        }
        event.creationTime = null;
        if (arg.creationTime != null) {
            event.creationTime = new Date(arg.creationTime.year, arg.creationTime.monthValue - 1,
                            arg.creationTime.dayOfMonth, arg.creationTime.hour, arg.creationTime.minute,
                            arg.creationTime.second, arg.creationTime.nano);
        }
        $('<div class="row eventRow"> <div class="col-md-3"> <div class="thumbnail"> ' +
         '<img class="img-responsive event-photo" src="http://placehold.it/300x300"> </div> </div>' +
         '<div class="col-md-9"> ' +
            '<div class="col-md-3"> <div class="eventUsername"></div> </div> ' +
            '<div class="col-md-3"> <div class="eventTime"></div><div> ' +
            '<div class="col-md-1"> <div class="eventNumberOfComments"></div><div> ' +
         '<div>').
         attr("id", event.name).css("display", "none").prependTo("foo");

         $("#" + event.name).find("div.eventUsername").attr("id", 'eventUsername' + event.name);
         $("#" + event.name).find("div.eventTime").attr("id", 'eventTime' + event.name);
         $("#" + event.name).find("div.eventNumberOfComments").attr("id", 'eventNumberOfComments' + event.name);
         $("#" + 'eventUsername' + event.name).text(event.username);
         $("#" + 'eventTime' + event.name).text(event.date.toString());
         $("#" + 'eventNumberOfComments' + event.name).text(event.numberOfComments);
         $("#" + comment.id).slideDown();
    }
}
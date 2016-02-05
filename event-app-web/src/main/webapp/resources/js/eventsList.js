$(document).ready(function () {
            window.timezoneOffset = new Date().getTimezoneOffset();
            var lastEventDate = getEventDate($("#createTime" + $(".eventRow:first").attr("id")).text());
            $.ajax({
                type: "GET",
                url: "../../event-app/events/?creationTime=" + lastEventDate
            }).then(showEvents);
});

function showEvents(data) {
    for(var i = 0; i < data.eventPreviewVOList.length; i++) {
        arg = data.eventPreviewVOList[i];
        var event = {};
        event.id = arg.id != null ? arg.id : "";
        event.name = arg.name != null ? arg.name : "";
        event.creator = arg.creator != null ? arg.creator : "";
        event.description = arg.description != null ? arg.description : "Description will be added later...";
        event.country = arg.country != null ? arg.country : "";
        event.city = arg.city != null ? arg.city : "";
        event.location = arg.location != null ? arg.location : "";
        event.numberOfComments = arg.numberOfComments != null ? arg.numberOfComments : "0";
        event.picture = arg.picture;
        event.eventTime = null;
        if (arg.eventTime != null) {
            event.eventTime = new Date(arg.eventTime.year, arg.eventTime.monthValue - 1, arg.eventTime.dayOfMonth,
                            arg.eventTime.hour, arg.eventTime.minute, arg.eventTime.second, arg.eventTime.nano);
        }
        event.createTime = null;
        if (arg.createTime != null) {
            event.createTime = new Date(arg.createTime.year, arg.createTime.monthValue - 1,
                            arg.createTime.dayOfMonth, arg.createTime.hour, arg.createTime.minute,
                            arg.createTime.second, arg.createTime.nano);
        }

        $('<div class="row eventRow"> ' +
            '<div class="col-md-2">' +
            '<a href="" id="picture"><div class="thumbnail">' +
                '<img class="img-responsive event-photo" src="http://placehold.it/150x150">' +
            '</div></a>' +
            '</div>' +
            '<div class="panel panel-info col-md-10">' +
                '<div class="panel-heading">' +
                    '<h2>' +
                        '<a href="" id="name"></a> ' +
                    '</h2>' +
                '</div>' +
                '<div class="panel-footer">' +
                    '<h4 class="glyphicon glyphicon-time" id="eventTime"></h4>' +
                    '<h4 class="glyphicon glyphicon glyphicon-map-marker col-md-offset-3" id="address"></h4>' +
                '</div>' +
                '<div class="panel-body">' +
                    '<h4 id="description"></h4>' +
                '</div>' +
                    '<h4 class="glyphicon glyphicon-user" id="eventCreator"></h4>' +
                    '<h4 class="glyphicon glyphicon-comment col-md-offset-10" id="numberOfCommentsforEvent"></h4>' +

            '</div> ' +
        '</div> '

        ).attr("id", event.id).prependTo("#eventPanel");

        $("#" + event.id).find("#name").attr("id", 'name' + event.id);
        $("#" + event.id).find("#picture").attr("id", 'picture' + event.id);
        $("#" + event.id).find("#eventTime").attr("id", 'eventTime' + event.id);
        $("#" + event.id).find("#address").attr("id", 'address' + event.id);
        $("#" + event.id).find("#description").attr("id", 'description' + event.id);
        $("#" + event.id).find("#eventCreator").attr("id", "eventCreator" + event.id);
        $("#" + event.id).find("#numberOfCommentsforEvent").attr("id", 'numberOfCommentsforEvent' + event.id);
        $("#" + event.id).find("#createTime").attr("id", 'createTime' + event.id);
        $("#" + 'name' + event.id).text(event.name + ' ');
        $("#" + 'name' + event.id).attr("href", '../../event-app/event.html?id=' + event.id);
        $("#" + 'picture' + event.id).attr("href", '../../event-app/event.html?id=' + event.id);
        $("#" + 'eventTime' + event.id).text(event.eventTime.toLocaleDateString()+ " " + event.eventTime.toLocaleTimeString());
        $("#" + 'address' + event.id).text(" " + (event.country + " " + event.city + " " + event.location).trim());
        $("#" + 'description' + event.id).text(event.description);
        $("#" + 'eventCreator' + event.id).text(event.creator);
        $("#" + 'numberOfCommentsforEvent' + event.id).text(event.numberOfComments);
        $("#" + event.id).slideDown();
    }
}

function getEventDate(lastEventDateString) {
    var lastEventDate;
    if (lastEventDateString) {
        lastEventDate = new Date(lastEventDateString);
        //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
        lastEventDate = new Date(lastEventDate.getTime() - window.timezoneOffset * 60000).toISOString();
    } else {
        lastEventDate = new Date();
        lastEventDate = new Date(lastEventDate.getTime() - window.timezoneOffset * 60000).toISOString();
    }
    return lastEventDate;
}

// we bind the scroll event, with the 'flyout' namespace
// so we can unbind easily
$(window).bind('scroll.flyout', (function check() {

    // this function is defined only once
    // it is private to our event handler
    function getScrollTop() {
        // if one of these values evaluates to false, this picks the other
        return (document.documentElement.scrollTop||document.body.scrollTop);
    }

    // this is the actual event handler
    // it has the getScrollTop() in its scope
    return function() {
        if (getScrollTop() > (document.body.scrollHeight-$(window).height()) * 0.85) {
            // flyout
            // out of the event loop
            setTimeout(function() {
                var lastEventDate = getEventDate($("#createTime" + $(".eventRow:last").attr("id")).text());
                alert(lastEventDate);
                $.ajax({
                    type: "GET",
                    url: "../../event-app/events/?creationTime=" + lastEventDate
                }).then(showEvents);
            }, 1);

            // unbind the event handler
            // so that it wasn't called multiple times
            $(this).unbind('scroll.flyout');
            setTimeout(function() {
                $(window).bind('scroll.flyout', check());
            }, 1000);
        }
    };
})());


//$(function () {
//             var $win = $(window);
//
//             $win.scroll(function () {
//                 if ($win.scrollTop() == 0)
//                     alert('Scrolled to Page Top');
//                 else if ($win.height() + $win.scrollTop()
//                                == $(document).height()) {
//                     alert('Scrolled to Page Bottom');
//                 }
//             });
//         });
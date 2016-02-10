$(document).ready(function () {
        window.timezoneOffset = new Date().getTimezoneOffset();
        getEventsFromServer(true);
        $('#loadNewEvents').click(function() { getEventsFromServer(false); });
});

//Function for requesting events from server
function getEventsFromServer(isLess) {
    var lastEventDate = getEventDate($("#createTime" + $(".eventRow:last").attr("id")).text());
    var firstEventDate = getEventDate($("#createTime" + $(".eventRow:first").attr("id")).text());
    var tempEventDate = getEventDate(null);
    console.log(tempEventDate);
    if(isLess) {
        $.ajax({
            type: "GET",
            url: "../../event-app/events/?queryMode=LESS_THAN&newestTime=" + firstEventDate + "&oldestTime=" + lastEventDate
        }).then(function(data) { showEvents(data, true); });
    }
    else {
        $.ajax({
            type: "GET",
            url: "../../event-app/events/?queryMode=MORE_THAN&newestTime=" + tempEventDate + "&oldestTime=" + lastEventDate
        }).then(function(data) { showEvents(data, false); });
    }
}

//function for hiding or showing button for new events with message
function processNewEventsButton(numberOfNewEvents) {
        if (numberOfNewEvents != 0) {
            var buttonText;
            if(numberOfNewEvents <= 3) {
                buttonText = "Load all of" + numberOfNewEvents + " new events";
            }
            else {
                buttonText = "Load 3 of " + numberOfNewEvents + " new events";
            }
            $('#loadNewEvents').text(buttonText).show();
        } else {
            $('#loadNewEvents').hide();
        }
}

//function shows events from data on page
function showEvents(data, isOnBottom) {
    console.log(data);
    for(var i = 0; i < data.eventPreviewVOList.length; i++) {
        var emptyEvent = '' +
            '<div class="row eventRow"> ' +
                '<div class="col-md-2">' +
                    '<a href="" id="picture"><div class="thumbnail">' +
                        '<img class="img-responsive event-photo" src="http://lorempixel.com/400/400/animals/' + i + '">' +
                    '</div></a>' +
                '</div>' +
                '<div class="panel panel-success col-md-10">' +
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
                    '<h4 class="glyphicon glyphicon-comment col-md-offset-10" id="numberOfCommentsForEvent"></h4>' +
                    '<div id="createTime" style="display:none"></div>' +
                '</div>' +
            '</div>';

        var event = getEventFromModel(data.eventPreviewVOList[i]);
        if(isOnBottom ) {
            $(emptyEvent).attr("id", event.id).appendTo("#eventPanel");
        }
        else {
            $(emptyEvent).attr("id", event.id).prependTo("#eventPanel");
        }

        $("#" + event.id).find("#name").attr("id", 'name' + event.id);
        $("#" + event.id).find("#picture").attr("id", 'picture' + event.id);
        $("#" + event.id).find("#eventTime").attr("id", 'eventTime' + event.id);
        $("#" + event.id).find("#address").attr("id", 'address' + event.id);
        $("#" + event.id).find("#description").attr("id", 'description' + event.id);
        $("#" + event.id).find("#eventCreator").attr("id", "eventCreator" + event.id);
        $("#" + event.id).find("#numberOfCommentsForEvent").attr("id", 'numberOfCommentsForEvent' + event.id);
        $("#" + event.id).find("#createTime").attr("id", 'createTime' + event.id);
        $("#" + 'name' + event.id).text(event.name + ' ');
        $("#" + 'name' + event.id).attr("href", '../../event-app/event.html?id=' + event.id);
        $("#" + 'picture' + event.id).attr("href", '../../event-app/event.html?id=' + event.id);
        if(event.eventTime != null) {
            $("#" + 'eventTime' + event.id).text(event.eventTime.toLocaleDateString()+ " " + event.eventTime.toLocaleTimeString());
        }
        else {
            $("#" + 'eventTime' + event.id).text("Not specified");
        }
        $("#" + 'createTime' + event.id).text(event.createTime.toString());

        var address = (event.country + " " + event.city + " " + event.location).trim();
        if(address == "") {
            $("#" + 'address' + event.id).hide();
        }
        else {
            $("#" + 'address' + event.id).text(" " + address);
        }
        $("#" + 'description' + event.id).text(event.description);
        $("#" + 'eventCreator' + event.id).text(event.creator);
        $("#" + 'numberOfCommentsForEvent' + event.id).text(event.numberOfComments);
        $("#" + event.id).slideDown();

        processNewEventsButton(data.numberOfNewEvents);
    }
}

//function returns event object made from model from server
function getEventFromModel(previewVO) {
        var event = {};
        event.id = previewVO.id != null ? previewVO.id : "";
        event.name = previewVO.name != null ? previewVO.name : "";
        event.creator = previewVO.creator != null ? previewVO.creator : "";
        event.description = previewVO.description != null ? previewVO.description : "Description will be added later...";
        event.country = previewVO.country != null ? previewVO.country : "";
        event.city = previewVO.city != null ? previewVO.city : "";
        event.location = previewVO.location != null ? previewVO.location : "";
        event.numberOfComments = previewVO.numberOfComments != null ? previewVO.numberOfComments : "0";
        event.picture = previewVO.picture;
        event.eventTime = null;
        if (previewVO.eventTime != null) {
            event.eventTime = new Date(previewVO.eventTime.year, previewVO.eventTime.monthValue - 1, previewVO.eventTime.dayOfMonth,
                            previewVO.eventTime.hour, previewVO.eventTime.minute, previewVO.eventTime.second, previewVO.eventTime.nano/1000000);
        }
        event.createTime = null;
        if (previewVO.createTime != null) {
            event.createTime = new Date(previewVO.createTime.year, previewVO.createTime.monthValue - 1, previewVO.createTime.dayOfMonth,
                            previewVO.createTime.hour, previewVO.createTime.minute, previewVO.createTime.second, previewVO.createTime.nano/1000000);
        }
        return event;
}

//function for converting datetime to datetime + 3 hours ISO format
function convertToLocalTime(date) {
    return new Date(date.getTime() - window.timezoneOffset * 60000).toISOString();
}

//function returns ISO formatted datetime from dateString if it isn't null or current datetime otherwise
function getEventDate(dateString) {
    var date;
    if (dateString) {
        date = new Date(dateString);
        //getting local datetime in yyyy-MM-dd'T'HH:mm:ss.SSSZ format
        date = convertToLocalTime(date);
    } else {
        date = new Date();
        date = convertToLocalTime(date);
    }
    return date;
}


// we bind the scroll event, with the 'flyout' namespace
// so we can unbind easily
// TODO:enable autoscroll when page height is < than whole page
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
                var firstEventDate = getEventDate($("#createTime" + $(".eventRow:first").attr("id")).text());
                $.ajax({
                    type: "GET",
                    url: "/event-app/events/?queryMode=LESS_THAN&newestTime=" + firstEventDate + "&oldestTime=" + lastEventDate
                }).then(showEvents);
            }, 1);

            // unbind the event handler
            // so that it wasn't called multiple times
            $(this).unbind('scroll.flyout');
            //bind it again
            setTimeout(function() {
                $(window).bind('scroll.flyout', check());
            }, 300);
        }
    };
})());
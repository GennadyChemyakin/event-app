$(function () {
    var header = "<div id=\"header\" style='margin:50px 0px 0px 0px'></div>";
    var footer = "<div id=\"footer\" class='footer' style='margin:50px 0px 0px 0px'></div>";
    $('body').prepend(header);
    $('body').append(footer);
    $("#header").load("header.html");
    $("#footer").load("footer.html");
});

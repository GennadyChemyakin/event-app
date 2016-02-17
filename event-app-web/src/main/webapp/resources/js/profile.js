$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "user?name=" + urlParam("name")
    }).then(function (data) {

        var country = data.country == null ? 'Country: N/A'  : 'Country: ' + data.country;
        var city    = data.city == null ? 'City: N/A'  : 'City: ' + data.city;
        var gender  = data.gender == null ? 'Gender: N/A'  : 'Gender: ' + data.gender;
        var full_name = data.name == null && data.surname == null ? data.userName : data.name + ' ' + data.surname;
        var bio     = data.bio;
        var username = data.username;


        $('#username').text(username);
        $('#country').text(country);
        $('#fullname').text(full_name);
        $('#gender').text(gender);
        $('#bio').text(bio);
        $('#city').text(city);

    } );
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
$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "user?username=" + urlParam("username")
    }).then(function (data) {

        var country      = data.country == null ? 'Country: N/A'  : 'Country: ' + data.country;
        var city         = data.city == null ? 'City: N/A'  : 'City: ' + data.city;
        var gender       = data.gender == null ? 'Gender: N/A'  : 'Gender: ' + data.gender;
        var username     = data.username;
        var name         = data.name == null ? '' :  data.name;
        var surname      = data.surname == null ? '' : data.surname;
        var bio          = data.bio == null ? 'no description' : data.bio;



        $('#username').text(username);
        $('#country').text(country);
        $('#fullname').text(name + " " + surname);
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
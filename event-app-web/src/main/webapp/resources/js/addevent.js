
    //listeners for input fields
    //and submit button click

     $(document).ready(function() {

      var options = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
      };

      function success(pos) {
         crd = pos.coords;
         latitude = crd.latitude;
         longitude = crd.longitude;
      };

      function error(err) {

      };

      navigator.geolocation.getCurrentPosition(success, error, options);

      function addZero(val) {
        val = val.toString();
        if(val.length == 1) {
            return '0' + val;
        } else if (val.length == 0) {
            return '00'
        }

        return val;
      };

        $("#register_btn").click(function() {
        var name              = $("#title").val();
        var description       = $("#description").val();
        var dateObj           = new Date(Date.parse($('#picker').val()));
        var city              = $("#city").val();
        var country           = $("#country").val();
        var address           = $("#address").val();

        var dateStr = dateObj.getUTCFullYear() + "-" + addZero(1 + dateObj.getUTCMonth()) + "-" + addZero(dateObj.getUTCDay());
        var timeStr = addZero(dateObj.getUTCHours()) + ':' + addZero(dateObj.getUTCMinutes()) + ':' + addZero(dateObj.getUTCSeconds());
        var date = dateStr + "T" + timeStr;
        var data = JSON.stringify({
                        name: name,
                        description: description,
                        city: city,
                        timeStamp:  date,
                        country: country,
                        gpsLatitude: latitude,
                        gpsLongitude: longitude,
                        location: address
                    });

            $.ajax({
                type: "POST",
                url: "add_event",
                data: data,
                contentType: "application/json",
                success: function (data) {
                               setTimeout(function() {
                                  window.location.href = "/event-app/home.html"}, 2000);
                           },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert("bad");
                }
                });

        });

        $.datetimepicker.setLocale('en');
        var Dobj = new Date();
        var dateStr = Dobj.getFullYear() + "/" + Dobj.getMonth() + "/" + Dobj.getDay();
        var timeStr = Dobj.getHours() + ':' + Dobj.getMinutes();
        $('#picker').datetimepicker({
        	formatTime:'H:i',
        	formatDate:'Y:m:d',
        	defaultDate: dateStr, // it's my birthday
        	defaultTime: timeStr,
        	timepickerScrollbar:false //,

        });

        var logic = function( currentDateTime ){
        	if (currentDateTime && currentDateTime.getDay() == 6){
        		this.setOptions({
        			minTime:'11:00'
        		});
        	}else
        		this.setOptions({
        			minTime:'8:00'
        		});
        };

     });
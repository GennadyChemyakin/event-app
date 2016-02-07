
    //listeners for input fields
    //and submit button click

         function addErrorMessage(field, text) {
            var parentField = field.parent();
            var errorField  = parentField.find('.alert:first');
            errorField.text(text);
            errorField.show();
         }

     function checkTitle() {
          	var title    = $('#title').val();
            return title.replace(/\s+/g, '').length > 0;
     }

          function markField(field, isGreen) {

                    var parentField = field.parent();
                    var glyph = parentField.find('.glyphicon:first');
                    var errorField  = parentField.find('.alert:first').hide();
                    if(!isGreen) {
                       parentField.removeClass('has-success');
                       parentField.addClass('has-error');
                       glyph.removeClass('glyphicon-ok');
                       glyph.addClass('glyphicon-remove');
                    } else {
                       parentField.addClass('has-success');
                       parentField.removeClass('has-error');
                       glyph.removeClass('glyphicon-remove');
                       glyph.addClass('glyphicon-ok');
                     }
          }

     $(document).ready(function() {



      $('#title').keyup(function(){
           markField($('#title'), checkTitle());
      });

      $('#picker').keyup(function(){
            ('#picker').val(Date.parse(('#picker').val()));
      });


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

        var noMistakes = true;
        if (!checkTitle()) {
               markField($("#title"), false);
               addErrorMessage($("#title"), "Title should not be empty.");
               noMistakes = false;
        }

        if(noMistakes) {

            $("#register_btn").addClass('disabled').attr('disabled', 'disabled');

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
                    $('#register_btn').removeClass('disabled').prop("disabled", false);
                }
                });

        }
        }
        );



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
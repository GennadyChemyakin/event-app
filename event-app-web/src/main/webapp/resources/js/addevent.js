
    //adds error message in parent field
    function addErrorMessage(field, text) {
         var parentField = field.parent();
         var errorField  = parentField.find('.alert:first');
         errorField.text(text);
         errorField.show();
    }

    //checks if the title is not empty
     function isValidTitle() {
          	var title    = $('#title').val();
            return title.replace(/\s+/g, '').length > 0;
     }
     //checks if date is valid
     function isValidDate() {
        var date = Date.parse($('#picker').val());
        if(isNaN(date) || date.toString().replace(/\s+/g, '').length == 0) {
            return false;
        }

        return true;

     }

      //function adds error message in the middle of the page
          function addMainMessage(errormsg) {
          	var field = $('#msg_main');
            field.addClass('alert-danger').text(errormsg);
            field.show();
          }

    //mark necessary fields with green or red
          function markField(field, isGreen) {

                    var parentField = field.parent();
                    var glyph = parentField.find('.glyphicon:last');
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
           markField($('#title'), isValidTitle());
      });

      $('#picker').keyup(function(){

          $("#picker").parent().find('.alert:first').hide();

      });


      //reading coordinates if allowed
      navigator.geolocation.getCurrentPosition(successGettingCoordinates, errorGettingCoordinates, optionsGettingCoordinates);


      var optionsGettingCoordinates = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
      };

     //on success getting coordinates
      function successGettingCoordinates(pos) {
         crd = pos.coords;
         latitude = crd.latitude;
         longitude = crd.longitude;
      };

      //on error getting coordinates
      function errorGettingCoordinates(err) {
            console.log(err);
      };

        //button click handler
        $("#add_event_btn").click(function() {
        $('#msg_main').hide();
        var name              = $("#title").val();
        var description       = $("#description").val();
        var dateObj           = new Date(Date.parse($('#picker').val()));
        var city              = $("#city").val();
        var country           = $("#country").val();
        var address           = $("#address").val();
        var noMistakes = true;
        if (!isValidTitle()) {
            markField($("#title"), false);
            addErrorMessage($("#title"), "Title should not be empty.");
            noMistakes = false;
        }

        if(!isValidDate()) {
            addErrorMessage($("#picker").parent(), "Enter valid date & time");
            noMistakes = false;
        }

        var date              = dateObj.toISOString();
        var data = JSON.stringify({
              name: name,
              description:  description,
              city:         city,
              timeStamp:    date.substring(0,date.length-1),
              country:      country,
              gpsLatitude:  latitude,
              gpsLongitude: longitude,
              location:     address
           });


        if(noMistakes) {
            $("#msg_main").hide();
            $("#add_event_btn").addClass('disabled').attr('disabled', 'disabled');

            $.ajax({
                type: "POST",
                url: "event",
                data: data,
                contentType: "application/json",
                success: function (data) {

                    window.location.href = "/event-app/detail.html?id=" + data.id;
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    addMainMessage("Problem connecting to database. Please try again.");
                    $('#add_event_btn').removeClass('disabled').prop("disabled", false);
                }
                });

        }
        }
        );


        //instantiate datePicker with appropriate settings
        $.datetimepicker.setLocale('en');
        var Dobj = new Date();
        var dateStr = Dobj.getFullYear() + "/" + Dobj.getMonth() + "/" + Dobj.getDay();
        var timeStr = Dobj.getHours() + ':' + Dobj.getMinutes();
        $('#picker').datetimepicker({
        	formatTime:'H:i',
        	formatDate:'Y:m:d',
        	defaultDate: dateStr,
        	defaultTime: timeStr,
        	timepickerScrollbar:false

        });

     });
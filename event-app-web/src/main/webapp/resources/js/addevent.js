
    //listeners for input fields
    //and submit button click
     $(document).ready(function() {

        $("#register_btn").click(function() {
        var name              = $("#title").val();
        var description       = $("#description").val();
        var date              = $("#date").val();
        var city              = $("#city").val();
        var country           = $("#country").val();

        var data = JSON.stringify({
                        name: name,
                        description: description,
                        city: city,
                        date: date,
                        country: country

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
     });
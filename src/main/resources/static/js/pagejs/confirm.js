/**
 * Created by lubo08 on 19.3.2014.
 */
$(function()	{

    $( "#confirmsubmit" ).on( "click", function(e) {
        e.preventDefault();
        $( "#confirm-registration-form" ).submit();
        //alert( $( "#sing-up-registration-form" ).serialize() );
        /*
        $.post( "sign-up-my/registration", $( "#sing-up-registration-form" ).serialize(),'json')
            .done(function(data) {
                alert( "second success "+data );
            })
            .fail(function(error) {
                alert( "error: "+error );
            });
        */
        /*
        $.ajax({
            url: "sign-up-my/registration",
            data: //"{\"name\":\"test\",\"email\":\"x@x.sk\",\"password\":\"test\",\"confirmPassword\":\"xxx\",\"agreement\":\"fdfsdf\"}",
                JSON.stringify($( "#sing-up-registration-form" ).serializeObject()),
            type: "POST",
            beforeSend: function(xhr) {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.setRequestHeader("consumes", "application/json");
                xhr.setRequestHeader(header, token);
            }
        })
        .done(function(data) {
            if(data.responseCode == 0){
                $('#lightCustomModal .m-top-none').text(data.responseMessage);
                $('#lightCustomModal').popup('show');
            }else{
                $('#darkCustomModal .m-top-none').text(data.responseMessage);
                $('#darkCustomModal').popup('show');
            }
        })
        .fail(function(error) {


                $('#darkCustomModal .m-top-none').text(JSON.stringify(error));
                $('#darkCustomModal').popup('show');
        });

        */
    });

    //jQuery popup overlay
    $('#darkCustomModal').popup({
        pagecontainer: '.container',
        transition: 'all 0.3s'
    });


    $('#lightCustomModal').popup({
        pagecontainer: '.container',
        transition: 'all 0.3s'
    });
});
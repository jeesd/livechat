$(function()	{

    $( "#confirmEmailSubmit" ).on( "click", function(e) {
        e.preventDefault();
        $( "#confirm-email-form" ).submit();
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
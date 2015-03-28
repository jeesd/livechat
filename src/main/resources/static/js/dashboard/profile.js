define(['jquery','bootstrap','dashboard.app'], function($, app){

    var initialize = function(app) {

        $('body').on('click', '#profile-save-submit', function(e) {
            e.preventDefault();

            $('#loading').css('visibility','visible');
            $.ajax({
                url: $('#profile-save-form').attr('action'),
                type: 'POST',
                data: $('#profile-save-form').serialize(),
                statusCode : {
                    200: function(data) {
                        $('#profile-form-container').html(data);

                        app.setDashboardInfo();
                        app.showErrorPopUp(NlsMessages["global.profileChanged"], NlsMessages["global.close"], "#");
                    },
                    400: function(request) {
                        $('#profile-form-container').html(request.responseText);
                    }
                },
                complete: function() {
                    $('#loading').css('visibility','hidden');
                },
                error: function(jqXHR){
                    if (jqXHR.status != 200 && jqXHR.status != 400) {
                        app.showErrorPopUp(NlsMessages["global.error"], NlsMessages["global.tryagain"], "#");
                    }
                }
            });
        });

        $('body').on('click', '#password-update-submit', function(e) {
            e.preventDefault();

            $('#loading').css('visibility','visible');
            $.ajax({
                url: $('#password-update-form').attr('action'),
                type: 'POST',
                data: $('#password-update-form').serialize(),
                statusCode : {
                    200: function(data, status, request) {
                        $('#password-update-container').html(data);

                        app.showErrorPopUp(NlsMessages["global.profileChanged"], NlsMessages["global.close"], "#");
                    },
                    400: function(request) {
                        $('#password-update-container').html(request.responseText);
                    }
                },
                complete: function() {
                    $('#loading').css('visibility','hidden');
                },
                error: function(jqXHR){
                    if (jqXHR.status != 200 && jqXHR.status != 400) {
                        app.showErrorPopUp(NlsMessages["global.error"], NlsMessages["global.tryagain"], "#");
                    }
                }
            });
        });

        $('body').on('click', '#profileImage', function() {
            $('#profile-image-upload').click();

            $('#profile-image-upload').change(function() {
                var formData = new FormData($('#change-avatar-form')[0]);
                $.ajax({
                    url: '/change-avatar',
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    statusCode : {
                        200: function(data, status, request) {
                            $('#change-avatar-container').html(data);
                            app.setDashboardInfo();
                            app.showErrorPopUp(NlsMessages["global.avatarUploaded"], NlsMessages["global.close"], "#");
                        },
                        400: function(request) {
                            $('#change-avatar-container').html(request.responseText);
                            app.showErrorPopUp(NlsMessages["global.fileExist"], NlsMessages["global.tryagain"], "#");
                        }
                    },
                    error: function(jqXHR){
                        if (jqXHR.status != 200 && jqXHR.status != 400) {
                            app.showErrorPopUp(NlsMessages["global.error"], NlsMessages["global.tryagain"], "#");
                        }
                    }
                });
            });
        });

        $('body').on('click', '#account-save-submit', function(e) {
            e.preventDefault();

            $('#loading').css('visibility','visible');
            $.ajax({
                url: $('#account-save-form').attr('action'),
                type: 'POST',
                data: $('#account-save-form').serialize(),
                statusCode : {
                    200: function(data, status, request) {
                        $('#account-form-container').html(data);
                        app.setDashboardInfo();
                        app.showErrorPopUp(NlsMessages["global.accountChanged"], NlsMessages["global.close"], "#");
                    },
                    400: function(request) {
                        $('#account-form-container').html(request.responseText);
                    }
                },
                complete: function() {
                    $('#loading').css('visibility','hidden');
                },
                error: function(jqXHR){
                    if (jqXHR.status != 200 && jqXHR.status != 400) {
                        app.showErrorPopUp(NlsMessages["global.error"], NlsMessages["global.tryagain"], "#");
                    }
                }
            });
        });
    };

    return {
        initialize: initialize
    };
});
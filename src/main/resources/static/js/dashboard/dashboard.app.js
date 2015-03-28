/**
 * Created by lubo08 on 5.11.2014.
 */
define(['jquery','visitor_list','profile','layout'], function($,visitorList,profile,layout){

    var EventEmitter = function() {};

    EventEmitter.createInterface = function(space) {
        var methods = {};

        methods.on = function(name, fn) {
            if (typeof this[space] === 'undefined') {
                this[space] = {};
            }
            if (!this[space].hasOwnProperty(name)) {
                this[space][name] = [];
            }
            this[space][name].push(fn);
        };

        methods.off = function(name, fn) {
            if (typeof this[space] === 'undefined') return;
            if (this[space].hasOwnProperty(name)) {
                util.removeItem(fn, this[space][name]);
            }
        };

        methods.trigger = function(name) {
            if (typeof this[space] !== 'undefined' && this[space].hasOwnProperty(name)) {
                var args = Array.prototype.slice.call(arguments, 1);
                for (var i = 0; i < this[space][name].length; i++) {
                    this[space][name][i].apply(this[space][name][i], args);
                }
            }
        };

        return methods;
    };

    var pvt = EventEmitter.createInterface('handlers');
    EventEmitter.prototype.on = pvt.on;
    EventEmitter.prototype.off = pvt.off;
    EventEmitter.prototype.trigger = pvt.trigger;

    var util = {};

    util.extend = function(a, b) {
        if (typeof a === 'undefined' || !a) { a = {}; }
        if (typeof b === 'object') {
            for (var key in b) {
                if (b.hasOwnProperty(key)) {
                    a[key] = b[key];
                }
            }
        }
        return a;
    };

    var Dashboard = function() {
        this.initialize();
    };

    Dashboard.prototype.initialize = function () {
        var self = this;
        $('a.link').parents('li').removeClass('active');
        $('a[href="' + document.location.pathname.replace("/", "") + '"]').parents('li').addClass('active');
        $( "#logoutConfirm .btn-success" ).click(function() {
            self.trigger('setLogoutStatus');
            $( "#dashboard-logout" ).submit();
        });

        $('a.link').on("click", function(e){
            url = $(this).attr("href");
            $('.main-menu li').removeClass('active');
            $(this).parent().addClass('active');
            self.loadContent(url);
            window.history.pushState('', url, url);
            e.preventDefault();
        });

        window.onpopstate = function(event) {
            self.loadContent(location.href);
        };

        //Stop preloading animation
        Pace.stop();

        // Fade out the overlay div
        $('#overlay').fadeOut(800);

        $('body').removeAttr('class');

        //Enable animation
        $('#wrapper').removeClass('preload');

        //Collapsible Active Menu
        if(!$('#wrapper').hasClass('sidebar-mini'))	{
            $('aside').find('.active.openable').children('.submenu').slideDown();
        }

        this.initMainPage();
        this.changedStatus();

        $.extend($.gritter.options, {
            position: 'bottom-right' // defaults to 'top-right' but can be 'bottom-left', 'bottom-right', 'top-left', 'top-right' (added in 1.7.1)
        });
    };

    Dashboard.prototype.loadContent = function (url) {
        var self = this;
        $('#loading').css('visibility','visible');
        $("#main-container").html("");
        $("#main-container").css({"opacity":0});

        $.ajax({
            type : "GET",
            url  : url,
            success : function(data){
                var parsedContent = $(data).find("#main-container").html();
                $("#main-container").html(parsedContent).animate({
                    opacity: 1
                }, {
                    duration: 2000,
                    specialEasing: {
                        width: "linear",
                        height: "easeOutBounce"
                    }
                });
                self.initMainPage();
            }, error: function(xhr,status,error){
                self.errorMessage(xhr.responseJSON);
            }, complete: function(response) {
                $('#loading').css('visibility','hidden');
            }
        });
    };

    Dashboard.prototype.errorMessage = function (responseJSON) {
        $('#lightCustomModal H4').html(responseJSON.errorMessage);
        $('#lightCustomModal a').html(responseJSON.buttonText);
        $('#lightCustomModal a').attr("href",responseJSON.forwardUrl);
        $('#lightCustomModal').popup('show');
    };

    Dashboard.prototype.showErrorPopUp = function (message,buttonDescription,redirectUrl) {
        $('#lightCustomModal H4').html(message);
        $('#lightCustomModal a').html(buttonDescription);
        $('#lightCustomModal a').attr("href",redirectUrl!=null?redirectUrl:$(location).attr('href'));
        //$('#lightCustomModal a').attr("onClick","location.reload(true);");

        if (redirectUrl == null || redirectUrl == '#') {
            $('body').on('click', '#lightCustomModal a', function(e) {
                e.preventDefault();
                $('#lightCustomModal').popup('hide');
            });
        }

        $('#lightCustomModal').popup('show');
    };

    Dashboard.prototype.initMainPage = function(){
        if(document.location.pathname.replace("/", "") == "visitor-list"){
            visitorList.initialize();
        }
        if(document.location.pathname.replace("/", "") == "profile"){
            profile.initialize(Dashboard.prototype);
        }
        if(document.location.pathname.replace("/", "") == "layout"){
            layout.initialize();
        }
    };

    Dashboard.prototype.setOnlineStatus = function () {
        $('#statusButton .btn').first().html($('#statusButton a[href="online"]').html());
        $('#statusButton .btn').removeClass( "btn-success btn-danger btn-warning" );
        $('#statusButton .btn').addClass( "btn-success" );
        $('#profileTopMenu > i').removeClass( "fa-circle fa-circle-o fa-times-circle-o" );
        $('#profileTopMenu > i').addClass( "fa-circle" );
        this.trigger('setOnlineStatus');
    };

    Dashboard.prototype.setInvisibleStatus = function () {
        $('#statusButton .btn').first().html($('#statusButton a[href="invisible"]').html());
        $('#statusButton .btn').removeClass( "btn-success btn-danger btn-warning" );
        $('#statusButton .btn').addClass( "btn-warning" );
        $('#profileTopMenu > i').removeClass( "fa-circle fa-circle-o fa-times-circle-o" );
        $('#profileTopMenu > i').addClass( "fa-circle-o" );
        this.trigger('setInvisibleStatus');
    };

    Dashboard.prototype.setOfflineStatus = function () {
        $('#statusButton .btn').first().html($('#statusButton a[href="offline"]').html());
        $('#statusButton .btn').removeClass( "btn-success btn-danger btn-warning" );
        $('#statusButton .btn').addClass( "btn-danger" );
        $('#profileTopMenu > i').removeClass( "fa-circle fa-circle-o fa-times-circle-o" );
        $('#profileTopMenu > i').addClass( "fa-times-circle-o" );
        this.trigger('setOfflineStatus');
    };

    Dashboard.prototype.setLogoutStatus = function () {
        $('#statusButton .btn').first().html($('#statusButton a[href="logout"]').html());
        $('#statusButton .btn').removeClass( "btn-success btn-danger btn-warning" );
        $('#statusButton .btn').addClass( "btn-danger" );
        $('#profileTopMenu > i').removeClass( "fa-circle fa-circle-o fa-times-circle-o" );
        $('#profileTopMenu > i').addClass( "fa-times-circle-o" );
        $( "#dashboard-logout" ).submit();
        this.trigger('setLogoutStatus');

    };

    Dashboard.prototype.changedStatus = function () {
        var self = this;
        $('#statusButton a').on("click", function(e){
            url = $(this).attr("href");
            if(url == 'online'){
                self.setOnlineStatus();
            }else if(url == 'invisible'){
                self.setInvisibleStatus();
            }else if(url == 'offline'){
                self.setOfflineStatus();
            }else if(url == 'logout'){
                self.setLogoutStatus();
            }
            e.preventDefault();
        });
    };

    Dashboard.prototype.showErrorMessage = function (title,text) {
        $.gritter.add({
            title: '<i class="fa fa-times-circle"></i> '+title,
            text: text,
            sticky: false,
            time: '',
            class_name: 'gritter-danger'
        });
        return false;
    };

    Dashboard.prototype.showWarningMessage = function (title,text) {
        $.gritter.add({
            title: '<i class="fa fa-warning"></i> '+title,
            text: text,
            sticky: false,
            time: '',
            class_name: 'gritter-warning'
        });
        return false;
    };

    Dashboard.prototype.showInfoMessage = function (title,text) {
        $.gritter.add({
            title: '<i class="fa fa-check-circle"></i> '+title,
            text: text,
            sticky: false,
            time: '',
            class_name: 'gritter-success'
        });
        return false;
    };

    Dashboard.prototype.refreshVisitorsCount = function (count) {
        var currentVisitor = count;
        $({numberValue: 0}).animate({numberValue: currentVisitor}, {
            duration: 2500,
            easing: 'linear',
            step: function() {
                $('.visitor-count').text(Math.ceil(this.numberValue));
            }
        });
        //$('.visitor-count').html($('.visitor-count').html()+1);
    };

    Dashboard.prototype.refreshOperatorsCount = function (count) {
        var currentOperator = count;
        $({numberValue: 0}).animate({numberValue: currentOperator}, {
            duration: 2500,
            easing: 'linear',
            step: function() {
                $('.operator-count').text(Math.ceil(this.numberValue));
            }
        });
    };

    Dashboard.prototype.newNotification = function (type,message) {

    };

    Dashboard.prototype.newVisitorNotification = function (data) {
        this.showInfoMessage(data.goOnlineTitle,data.goOnlineText);
        this.refreshVisitorsCount(data.onlineUsersCount);
    };

    Dashboard.prototype.offlineVisitorNotification = function (data) {
        this.showErrorMessage(data.goOnlineTitle,data.goOnlineText);
        this.refreshVisitorsCount(data.onlineUsersCount);
    };

    Dashboard.prototype.newOperatorNotification = function (data) {
        this.showInfoMessage(data.goOnlineTitle,data.goOnlineText);
        this.refreshOperatorsCount(data.onlineUsersCount);
    };

    Dashboard.prototype.offlineOperatorNotification = function (data) {
        this.showErrorMessage(data.goOnlineTitle,data.goOnlineText);
        this.refreshOperatorsCount(data.onlineUsersCount);
    };

    Dashboard.prototype.setDashboardInfo = function () {

        $.ajax({
            type : "GET",
            url  : '/getUserSessionJson',
            success : function(response){
                $('.userName').html(response.firstName);
                $('.userSurname').html(response.lastName);
                $('.userFullName').html(response.firstName + " " + response.lastName);
                $('.userEmail').html(response.email);
                $('.userAvatar').attr('src', response.imgeUrl);
            }, error: function(xhr){
                self.errorMessage(xhr.responseJSON);
            }
        });
    };

    util.extend(Dashboard.prototype, EventEmitter.prototype);

    return new Dashboard();
});
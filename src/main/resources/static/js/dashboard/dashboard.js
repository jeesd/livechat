/**
 * Created by lubo08 on 28.3.2014.
 */
require({
    baseUrl: '/js/dashboard',
    paths: {
        jquery: '../jqs/jquery/jquery-2.1.3',
        org: '../jqs/org',
        "jquery.cookie": '../templatejs/jquery.cookie',
        "jquery.cometd": '../jqs/jquery/jquery.cometd',
        "jquery.cometd-reload": '../jqs/jquery/jquery.cometd-reload',
        "jquery.cometd-ack":'../jqs/jquery/jquery.cometd-ack',
        bootstrap: '../../bootstrap/js/bootstrap.min',
        modernizr: '../templatejs/modernizr.min',
        pace: '../templatejs/pace.min',
        "jquery.popupoverlay": '../templatejs/jquery.popupoverlay.min',
        "jquery.slimscroll": '../templatejs/jquery.slimscroll.min',
        "jquery.dataTables": '../templatejs/jquery.dataTables.min',
        "jquery.gritter": '../templatejs/jquery.gritter.min',
        endless: '../templatejs/endless/endless',
        //'wysihtml5': '../templatejs/wysihtml5-0.3.0.min'
        'wysihtml5': '../templatejs/uncompressed/wysihtml5-0.3.0',
        'bootstrap-wysihtml5':'../templatejs/uncompressed/bootstrap-wysihtml5'
        //'wysihtml5': '../templatejs/wysihtml5/bootstrap3-wysihtml5.all.min',
        //'wysihtml5.sk-SK': '../templatejs/wysihtml5/locales/bootstrap-wysihtml5.sk-SK'
    },
    shim: {
        'bootstrap': {
            deps: ['jquery']
        },
        'endless':{
            deps: ['jquery','jquery.cookie','jquery.popupoverlay','jquery.slimscroll','bootstrap','modernizr','pace']
        },
        'dashboard.app':{
            deps: ['pace','jquery.gritter']
        },
        'jquery.popupoverlay':{
            deps: ['jquery']
        },
        'jquery.slimscroll':{
            deps: ['jquery']
        },
        'jquery.gritter':{
            deps: ['jquery']
        },
        'jquery.cookie':{
            deps: ['jquery']
        },
        'wysihtml5':{
            deps: ['jquery']
        },
        'bootstrap-wysihtml5':{
            deps: ['jquery','wysihtml5']
        }
    }
},
['jquery','dashboard.app','connection.manager','jquery.cometd','jquery.cometd-reload','jquery.cometd-ack','bootstrap','modernizr','pace','jquery.popupoverlay',
    'jquery.slimscroll','jquery.dataTables','jquery.gritter','endless','wysihtml5','bootstrap-wysihtml5'
],
function($,app,connectionManager,cometd)
{
    $(function()	{

        var conn = connectionManager.getInstance();
        conn.bind(cometd);

        conn._on('_connectionEstablished',function (){
            app.setOnlineStatus();
        });
        conn._on('_connectionBroken',function (){
            app.setOfflineStatus();
            app.showErrorMessage('Status changed to offline','You where disconnected! wait while reconnect...');
        });
        conn._on('_timeOut',function (message){
            app.setOfflineStatus();
            app.showErrorPopUp(NlsMessages["session.timeout"],NlsMessages["error.go.login"]);
        });

        conn.on('VISITOR_ARRIVED',function (message){
            app.newVisitorNotification(message);
        });
        conn.on('VISITOR_LEFT',function (message){
            app.offlineVisitorNotification(message);
        });
        conn.on('OPERATOR_ARRIVED',function (message){
            app.newOperatorNotification(message);
        });
        conn.on('OPERATORS_COUNT',function (message){
            app.refreshOperatorsCount(message);
        });
        conn.on('VISITOR_CHAT_REQUEST',function (message){
            app.newVisitorNotification(message);
        });

        app.on('setLogoutStatus',function (){
            cometd.disconnect();
        });
        app.on('setOfflineStatus',function (){
            app.showErrorMessage('Status changed to offline','You where disconnected! wait while reconnect...');
        });
        app.on('setOnlineStatus',function (){
            if(cometd.isDisconnected()){
                app.showInfoMessage('Status changed to online','You are now online');
            };
        });
        app.on('setInvisibleStatus',function (){
            app.showWarningMessage('Status changed to invisible','You are now invisible. Other users will not able to see you');
        });

    });
});

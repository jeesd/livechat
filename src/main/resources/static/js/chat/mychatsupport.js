/**
 * Created by lubo08 on 11.12.2014.
 */
if (typeof require === 'undefined') {
    var myChatSupportScript = document.createElement('script');
    myChatSupportScript.type = "text/javascript";
    myChatSupportScript.src = "http://appchatserver.com:8080/js/templatejs/require.js";
    myChatSupportScript.onload = InitMyChatSupportLiveChat;
    myChatSupportScript.onreadystatechange = function () { if (this.readyState == 'complete' || this.readyState == 'loaded') InitMyChatSupportLiveChat(); }
    document.getElementsByTagName('head')[0].appendChild(myChatSupportScript);
} else {
    InitMyChatSupportLiveChat();
}
function InitMyChatSupportLiveChat()
{




    if(typeof jQuery !== 'undefined' && $.fn.jquery.split('.')[0] >= 2){
        define('jquery', [], function() {
            return jQuery;
        });
        require.config({
            baseUrl: '//appchatserver.com:8080/js/chat',
            paths: {
                org: '../jqs/org',
                "jquery.cookie": '../templatejs/jquery.cookie',
                "jquery.cometd": '../jqs/jquery/jquery.cometd',
                "jquery.cometd-reload": '../jqs/jquery/jquery.cometd-reload'
            },
            shim: {
                'jquery.cookie':{
                    deps: ['jquery']
                }
            }
        });
        console.log($.fn.jquery.split('.')[0]+' je jquery');
    }else{
        require.config({
            baseUrl: '//appchatserver.com:8080/js/chat',
            paths: {
                '*': { 'jquery': 'jquery.noconflict' },
                'jquery.noconflict': { 'jquery': 'jquery' },
                "jquery": '../jqs/jquery/jquery-2.1.3',
                org: '../jqs/org',
                "jquery.cookie": '../templatejs/jquery.cookie',
                "jquery.cometd": '../jqs/jquery/jquery.cometd',
                "jquery.cometd-reload": '../jqs/jquery/jquery.cometd-reload'
            },
            shim: {
                'jquery.cookie':{
                    deps: ['jquery']
                }
            }
        });
    }

    require(['jquery','connection.manager','jquery.cometd', 'jquery.cometd-reload'],
        function($,connection,cometd)
        {
            var version = $().jquery;
            console.log(version);

            $(document).ready(function () {

                var conn = connection.getInstance();
                conn.bind(cometd);
                // call online layout as connection was successful.
                conn._on('_connectionEstablished',function (){
                    cometd.remoteCall('/layout', {
                        style: "silver_chat",
                        component: "chat_bar_online"
                    }, 5000, function(response)
                    {
                        if (response.successful)
                        {
                            // The action was performed
                            var data = response.data;
                            $("div[data-widget='mychatsupport']").html(data).animate({
                                opacity: 1
                            }, {
                                duration: 2000,
                                specialEasing: {
                                    width: "linear",
                                    height: "easeOutBounce"
                                }
                            });
                        }
                    });
                });
                conn._on('_connectionBroken',function (){
                    $("div[data-widget='mychatsupport']").html(conn.widget.silver_chat.chat_bar_offline).animate({
                        opacity: 1
                    }, {
                        duration: 2000,
                        specialEasing: {
                            width: "linear",
                            height: "easeOutBounce"
                        }
                    });
                });
                $("div[data-widget='mychatsupport']").on('click',function () {
                    var component;
                    if(conn.connected === true){
                        component = '';
                    }

                    alert('clicked');
                });


            });

        }
    );
}
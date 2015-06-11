/**
 * Created by lubo08 on 9.11.2014.
 */
define(['jquery'], function($){

    var EventEmitter = function() {};
    // inspired by intercom.js
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

    var pvt = EventEmitter.createInterface('_handlers');
    EventEmitter.prototype._on = pvt.on;
    EventEmitter.prototype._off = pvt.off;
    EventEmitter.prototype._trigger = pvt.trigger;

    var pub = EventEmitter.createInterface('handlers');
    EventEmitter.prototype.on = function() {
        pub.on.apply(this, arguments);
        Array.prototype.unshift.call(arguments, 'on');
        this._trigger.apply(this, arguments);
    };
    EventEmitter.prototype.off = pub.off;
    EventEmitter.prototype.trigger = pub.trigger;

    var localStorage = window.localStorage;
    if (typeof localStorage === 'undefined') {
        localStorage = {
            getItem    : function() {},
            setItem    : function() {},
            removeItem : function() {}
        };
    }

    var util = {};

    util.guid = (function() {
        var S4 = function() {
            return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        };
        return function() {
            return S4() + S4() + S4() + S4() + S4() + S4() + S4() + S4();
        };
    })();

    util.throttle = function(delay, fn) {
        var last = 0;
        return function() {
            var now = (new Date()).getTime();
            if (now - last > delay) {
                last = now;
                fn.apply(this, arguments);
            }
        };
    };

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

    util.removeItem = function(item, array) {
        for (var i = array.length - 1; i >= 0; i--) {
            if (array[i] === item) {
                array.splice(i, 1);
            }
        }
        return array;
    };

    var LiveCommunicator = function() {
        var self = this;
        var now = (new Date()).getTime();
        var gClientId = util.guid();

        this.origin         = [];
        this.lastMessage    = now;
        this.bindings       = [];
        this.receivedIDs    = {};
        this.previousValues = {};
        this.connected = false;
        this.clientId = gClientId;

        var storageHandler = function() { self._onStorageEvent.apply(self, arguments); };
        if (window.attachEvent) { document.attachEvent('onstorage', storageHandler); }
        else { window.addEventListener('storage', storageHandler, false); }
    };

    LiveCommunicator.prototype._transaction = function(fn) {
        var TIMEOUT   = 1000;
        var WAIT      = 20;

        var self      = this;
        var executed  = false;
        var listening = false;
        var waitTimer = null;

        var lock = function() {
            if (executed) return;

            var now = (new Date()).getTime();
            var activeLock = parseInt(localStorage.getItem(INDEX_LOCK) || 0);
            if (activeLock && now - activeLock < TIMEOUT) {
                if (!listening) {
                    self._on('storage', lock);
                    listening = true;
                }
                waitTimer = window.setTimeout(lock, WAIT);
                return;
            }
            executed = true;
            localStorage.setItem(INDEX_LOCK, now);

            fn();
            unlock();
        };

        var unlock = function() {
            if (listening) { self._off('storage', lock); }
            if (waitTimer) { window.clearTimeout(waitTimer); }
            localStorage.removeItem(INDEX_LOCK);
        };

        lock();
    };

    LiveCommunicator.prototype._cleanup_emit = util.throttle(100, function() {
        var self = this;

        this._transaction(function() {
            var now = (new Date()).getTime();
            var threshold = now - THRESHOLD_TTL_EMIT;
            var changed = 0;

            var messages = JSON.parse(localStorage.getItem(INDEX_EMIT) || '[]');
            for (var i = messages.length - 1; i >= 0; i--) {
                if (messages[i].timestamp < threshold) {
                    messages.splice(i, 1);
                    changed++;
                }
            }
            if (changed > 0) {
                localStorage.setItem(INDEX_EMIT, JSON.stringify(messages));
            }
        });
    });

    LiveCommunicator.prototype._cleanup_once = util.throttle(100, function() {
        var self = this;

        this._transaction(function() {
            var timestamp, ttl, key;
            var table   = JSON.parse(localStorage.getItem(INDEX_ONCE) || '{}');
            var now     = (new Date()).getTime();
            var changed = 0;

            for (key in table) {
                if (self._once_expired(key, table)) {
                    delete table[key];
                    changed++;
                }
            }

            if (changed > 0) {
                localStorage.setItem(INDEX_ONCE, JSON.stringify(table));
            }
        });
    });

    LiveCommunicator.prototype._once_expired = function(key, table) {
        if (!table) return true;
        if (!table.hasOwnProperty(key)) return true;
        if (typeof table[key] !== 'object') return true;
        var ttl = table[key].ttl || THRESHOLD_TTL_ONCE;
        var now = (new Date()).getTime();
        var timestamp = table[key].timestamp;
        return timestamp < now - ttl;
    };

    LiveCommunicator.prototype._localStorageChanged = function(event, field) {
        if (event && event.key) {
            return event.key === field;
        }

        var currentValue = localStorage.getItem(field);
        if (currentValue === this.previousValues[field]) {
            return false;
        }
        this.previousValues[field] = currentValue;
        return true;
    };

    LiveCommunicator.prototype._onStorageEvent = function(event) {
        event = event || window.event;
        var self = this;

        if (this._localStorageChanged(event, INDEX_EMIT)) {
            this._transaction(function() {
                var now = (new Date()).getTime();
                var data = localStorage.getItem(INDEX_EMIT);
                var messages = JSON.parse(data || '[]');
                for (var i = 0; i < messages.length; i++) {
                    if (messages[i].origin === self.origin) continue;
                    if (messages[i].timestamp < self.lastMessage) continue;
                    if (messages[i].id) {
                        if (self.receivedIDs.hasOwnProperty(messages[i].id)) continue;
                        self.receivedIDs[messages[i].id] = true;
                    }
                    self.trigger(messages[i].name, messages[i].payload);
                }
                self.lastMessage = now;
            });
        }

        this._trigger('storage', event);
    };

    LiveCommunicator.prototype._emit = function(name, message, id) {
        id = (typeof id === 'string' || typeof id === 'number') ? String(id) : null;
        if (id && id.length) {
            if (this.receivedIDs.hasOwnProperty(id)) return;
            this.receivedIDs[id] = true;
        }

        var packet = {
            id        : id,
            name      : name,
            origin    : this.origin,
            timestamp : (new Date()).getTime(),
            payload   : message
        };

        var self = this;
        this._transaction(function() {
            var data = localStorage.getItem(INDEX_EMIT) || '[]';
            var delimiter = (data === '[]') ? '' : ',';
            data = [data.substring(0, data.length - 1), delimiter, JSON.stringify(packet), ']'].join('');
            localStorage.setItem(INDEX_EMIT, data);
            self.trigger(name, message);

            window.setTimeout(function() { self._cleanup_emit(); }, 50);
        });
    };

    LiveCommunicator.prototype.emit = function(name, message) {
        this._emit.apply(this, arguments);
        this._trigger('emit', name, message);
    };

    LiveCommunicator.prototype.once = function(key, fn, ttl) {
        if (!Intercom.supported) return;

        var self = this;
        this._transaction(function() {
            var data = JSON.parse(localStorage.getItem(INDEX_ONCE) || '{}');
            if (!self._once_expired(key, data)) return;

            data[key] = {};
            data[key].timestamp = (new Date()).getTime();
            if (typeof ttl === 'number') {
                data[key].ttl = ttl * 1000;
            }

            localStorage.setItem(INDEX_ONCE, JSON.stringify(data));
            fn();

            window.setTimeout(function() { self._cleanup_once(); }, 50);
        });
    };

    LiveCommunicator.prototype._connectionEstablished = function () {
        console.log('Connection established');
    };

    LiveCommunicator.prototype._connectionBroken = function () {
        console.log('Connection broken');
    };

    LiveCommunicator.prototype._connectionClosed = function () {
        console.log('Connection closed');
    };

    LiveCommunicator.prototype.bind = function(object) {
        for (var i = 0; i < LiveCommunicator.bindings.length; i++) {
            var binding = LiveCommunicator.bindings[i].factory(object, this);
            if (binding) { this.bindings.push(binding); }
        }
    };

    // instance construction
    util.extend(LiveCommunicator.prototype, EventEmitter.prototype);

    LiveCommunicator.bindings = [];
    LiveCommunicator.supported = (typeof localStorage !== 'undefined');

    var INDEX_EMIT = 'liveCommunicator';
    var INDEX_ONCE = 'liveCommunicator_once';
    var INDEX_LOCK = 'liveCommunicator_lock';

    var THRESHOLD_TTL_EMIT = 50000;
    var THRESHOLD_TTL_ONCE = 1000 * 3600;

    LiveCommunicator.destroy = function() {
        localStorage.removeItem(INDEX_LOCK);
        localStorage.removeItem(INDEX_EMIT);
        localStorage.removeItem(INDEX_ONCE);
    };

    LiveCommunicator.getInstance = (function() {
        var liveCommunicator = null;
        return function() {
            if (!liveCommunicator) {
                liveCommunicator = new LiveCommunicator();
                // Disconnect when the page unloads
            }
            return liveCommunicator;
        };
    })();


    var CometBinding = function(cometd, liveCommunicator, urlParam) {

        var config = {
            contextPath: $(location).attr('protocol')+'//localhost:'+($(location).attr('protocol')=='https:'?'8443':'8080')
        };
        var cometURL = config.contextPath+"/cometd";

        $(window).unload(function () {
            cometd.reload({
                cookieMaxAge: 30
            });
        });


        var params = { };

        if($("div[data-widget='mychatsupport']").attr("data-account-id")){
            params['accountId'] = $("div[data-widget='mychatsupport']").attr("data-account-id");
        }
        if($("div[data-widget='mychatsupport']").attr("data-department")){
            params['departmentKey'] = $("div[data-widget='mychatsupport']").attr("data-department");
        }
        if($("div[data-widget='mychatsupport']").attr("data-language")){
            params['language'] = $("div[data-widget='mychatsupport']").attr("data-language");
        }

        params['ci'] = liveCommunicator.clientId;

        var urlParams = "?"+$.param( params, true );




        var cometURL = config.contextPath
                //+ "/cometd"+urlParams
                + "/cometd"
            ;

        //$.cookie("ctest", "0");

        cometd.configure({
            url: cometURL
            //appendMessageTypeToURL: false
            //,requestHeaders: {'AccountId':$("div[data-widget='mychatsupport']").attr("data-account-id")}
            ,logLevel: 'debug'
        });
        /*
        if($.cookie("urljsessionid") != null){
            cometd.configure({
                url: cometURL,
                appendMessageTypeToURL: false
                ,requestHeaders: {'Cookie':"JSESSIONID="+handshake.ext.addJsessionIdToUrl}
                ,logLevel: 'debug'
            });
        }
        */

        cometd.onListenerException = function(exception, subscriptionHandle, isListener, message)
        {
            console.log(message);
            // Uh-oh, something went wrong, disable this listener/subscriber
            // Object "this" points to the CometD object
            //if (isListener)
                //this.removeListener(subscriptionHandle);
            //else
                //this.unsubscribe(subscriptionHandle);
        };

        cometd.addListener('/meta/handshake', function (handshake){
            if (handshake.successful === true) {
                liveCommunicator.origin = handshake.clientId;
                $.cookie("BID", handshake.ext.BID);
                cometd.batch(function () {
                    cometd.subscribe('/chat/'+handshake.ext.accountId, function (message) {
                        liveCommunicator._emit(message.data.type, message.data, message.data.id);
                    });
                    cometd.publish('/client/info', { department: $("div[data-widget='mychatsupport']").attr("data-department") });
                });
                console.log(handshake);
            }else if (handshake.successful === false && handshake.ext!= null && handshake.ext.auth.failureReason == 'not_available_connections') {
                cometd.disconnect();
            }
        });


        cometd.addListener('/meta/connect', function (message) {
            if (cometd.isDisconnected()) {
                liveCommunicator.connected = false;
                liveCommunicator._connectionClosed();
                liveCommunicator._trigger('_connectionClosed');
                return;
            }
            var wasConnected = liveCommunicator.connected;
            liveCommunicator.connected = message.successful === true;
            if (!wasConnected && liveCommunicator.connected) {
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

                liveCommunicator._connectionEstablished();
                liveCommunicator._trigger('_connectionEstablished');
            }
            else if (wasConnected && !liveCommunicator.connected) {
                liveCommunicator._connectionBroken();
                liveCommunicator._trigger('_connectionBroken');
            }
        });

        cometd.addListener('/meta/disconnect', function (message) {
            if (message.successful)
            {
                liveCommunicator.connected = false;
                liveCommunicator._connectionClosed();
                liveCommunicator._trigger('_timeOut');
            }
        });
        var languageCode =   $("div[data-widget='mychatsupport']").attr("data-language");
        var accountId = $("div[data-widget='mychatsupport']").attr("data-account-id");

        $.ajax({
            type : "GET",
            url  : config.contextPath+"/chat/template/html/silver_chat/chat_bar_offline"+urlParams,
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success : function(data){
                var parsedContent = $(data).html();
                $("div[data-widget='mychatsupport']").html(parsedContent).animate({
                    opacity: 1
                }, {
                    duration: 2000,
                    specialEasing: {
                        width: "linear",
                        height: "easeOutBounce"
                    }
                });

            }, error: function(xhr,status,error){
                console.log(xhr.responseJSON);
            }, complete: function(response) {
                cometd.handshake({
                    ext: {
                        client: {
                            accountID: accountId,
                            BID: $.cookie("BID"),
                            lang: languageCode,
                            wh: $( window ).width()+"x"+$( window ).height()
                        }
                    }
                });
            }
        });
    };

    CometBinding.factory = function(object, liveCommunicator) {
        if (typeof object.handshake === 'undefined') { return false }
        
        return new CometBinding(object, liveCommunicator);
    };

    LiveCommunicator.bindings.push(CometBinding);

    return LiveCommunicator;
});

package org.mylivedata.app.connection;

import org.cometd.server.transport.JSONPTransport;
import org.cometd.server.transport.JSONTransport;
import org.cometd.websocket.server.AbstractWebSocketTransport;

/**
 * Created by lubo08 on 11.11.2014.
 */
public class AsyncConnectionManager implements ConnectionManager {

    public static final int MAX_WEBSOCKET_CONNECTIONS_PER_BROWSER_AND_DOMAIN = 5;
    public static final int MAX_LONG_POOLING_CONNECTIONS_PER_BROWSER = 1;


    @Override
    public boolean canConnect(BrowserConnectionManager browserConnectionManager) {
        if(browserConnectionManager.getConnectionType().equals(AbstractWebSocketTransport.NAME)
                && browserConnectionManager.getSessionInfos().size() <= AsyncConnectionManager.MAX_WEBSOCKET_CONNECTIONS_PER_BROWSER_AND_DOMAIN){
            return true;
        }else if(browserConnectionManager.getConnectionType().equals(JSONPTransport.NAME)
                && browserConnectionManager.getSessionInfos().size() <= AsyncConnectionManager.MAX_LONG_POOLING_CONNECTIONS_PER_BROWSER){
            return true;
        }else if(browserConnectionManager.getConnectionType().equals(JSONTransport.NAME)
                && browserConnectionManager.getSessionInfos().size() <= AsyncConnectionManager.MAX_LONG_POOLING_CONNECTIONS_PER_BROWSER){
            return true;
        }
        return false;
    }

    @Override
    public int availableConnections(BrowserConnectionManager browserConnectionManager) {
        return 0;
    }

    @Override
    public int maxConnectionsPerBrowser(BrowserConnectionManager browserConnectionManager) {
        return 0;
    }

    @Override
    public int maxConnectionsPerBrowserAndDomain(BrowserConnectionManager browserConnectionManager) {
        return 0;
    }
}

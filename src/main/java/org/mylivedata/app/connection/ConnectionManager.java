package org.mylivedata.app.connection;

/**
 * Created by lubo08 on 11.11.2014.
 */
public interface ConnectionManager {

    public boolean canConnect(BrowserConnectionManager browserConnectionManager);

    public int availableConnections(BrowserConnectionManager browserConnectionManager);

    public int maxConnectionsPerBrowser(BrowserConnectionManager browserConnectionManager);

    public int maxConnectionsPerBrowserAndDomain(BrowserConnectionManager browserConnectionManager);



}

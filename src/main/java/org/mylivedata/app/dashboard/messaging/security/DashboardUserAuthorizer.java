package org.mylivedata.app.dashboard.messaging.security;

import java.util.EnumSet;

import org.cometd.bayeux.ChannelId;
import org.cometd.bayeux.server.Authorizer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;

/**
 * Created by lubo08 on 10.12.2014.
 */
public class DashboardUserAuthorizer  implements Authorizer  {
    /**
     * Grants {@link Operation#CREATE} authorization
     */
    public static final DashboardUserAuthorizer GRANT_CREATE = new DashboardUserAuthorizer(EnumSet.of(Operation.CREATE));

    /**
     * Grants {@link Operation#SUBSCRIBE} authorization
     */
    public static final DashboardUserAuthorizer GRANT_SUBSCRIBE = new DashboardUserAuthorizer(EnumSet.of(Operation.SUBSCRIBE));

    /**
     * Grants {@link Operation#PUBLISH} authorization
     */
    public static final DashboardUserAuthorizer GRANT_PUBLISH = new DashboardUserAuthorizer(EnumSet.of(Operation.PUBLISH));

    /**
     * Grants {@link Operation#CREATE} and {@link Operation#SUBSCRIBE} authorization
     */
    public static final DashboardUserAuthorizer GRANT_CREATE_SUBSCRIBE = new DashboardUserAuthorizer(EnumSet.of(Operation.CREATE, Operation.SUBSCRIBE));

    /**
     * Grants {@link Operation#SUBSCRIBE} and {@link Operation#PUBLISH} authorization
     */
    public static final DashboardUserAuthorizer GRANT_SUBSCRIBE_PUBLISH = new DashboardUserAuthorizer(EnumSet.of(Operation.SUBSCRIBE, Operation.PUBLISH));

    /**
     * Grants {@link Operation#CREATE}, {@link Operation#SUBSCRIBE} and {@link Operation#PUBLISH} authorization
     */
    public static final DashboardUserAuthorizer GRANT_ALL = new DashboardUserAuthorizer(EnumSet.allOf(Operation.class));

    /**
     * Grants no authorization, the authorization request is ignored
     */
    public static final DashboardUserAuthorizer GRANT_NONE = new DashboardUserAuthorizer(EnumSet.noneOf(Operation.class));

    private final EnumSet<Authorizer.Operation> _operations;

    private DashboardUserAuthorizer(final EnumSet<Authorizer.Operation> operations)
    {
        _operations = operations;
    }

    @Override
    public Result authorize(Operation operation, ChannelId channel, ServerSession session, ServerMessage message) {

        VisitorPrincipal secUser =  (VisitorPrincipal)session.getAttribute("user");
        if(!session.isLocalSession() && (secUser == null || !secUser.getSecureUser().getAccountIdentity().equals(channel.toString().replace(channel.getParent()+"/",""))))
            return Result.ignore();

        if (_operations.contains(operation))
            return Result.grant();
        return Result.ignore();
    }
}

package org.mylivedata.app.connection.integration.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.connection.BrowserConnectionManager;
import org.mylivedata.app.connection.SessionInfo;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by lubo08 on 18.1.2015.
 */
@MessageEndpoint
public class AuthorizationService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionUtils sessionUtils;


    public Message<VisitorPrincipal> validateUserHashId (Message<?> message, @Header("BID") String bid, @Header("CSID") String currentCometSessionId) {
        BrowserConnectionManager browserConnectionManager = sessionUtils.getBrowserConnectionsManager(bid);
        VisitorPrincipal visitorPrincipal = (VisitorPrincipal)message.getPayload();
        for(SessionInfo sessionInfo: browserConnectionManager.getSessionInfos()){
            if(!sessionInfo.getCometSessionId().equals(currentCometSessionId)){
                if(!sessionInfo.getUserHashId().equals(visitorPrincipal.getUserHashId())){
                    visitorPrincipal.setUserHashId(sessionInfo.getUserHashId());
                    sessionUtils.updateChatSession(bid,currentCometSessionId,sessionInfo.getUserHashId());
                }
            }
        }
        return MessageBuilder.fromMessage(message).withPayload(visitorPrincipal).build();
    }


    public boolean validateDomainVsAccount(VisitorPrincipal visitorPrincipal) {

        try {
            if(!accountService.checkAccountIdVsUrl(visitorPrincipal.getOrigin(),visitorPrincipal.getAccountIdHash()))
                return false;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return true;
        /*
        //server.getContext().getURL()
        SecureUser user = userService.getSecureUserByHash(visitorPrincipal);
        Map<String,Object> chaUserData = new HashMap<>();
        chaUserData.put("Operator",false);
        chaUserData.put("AccountId",user.getAccountId().longValue());
        chaUserData.put("HttpSessionOnline",false);
        chaUserData.put("ConnectedAt",new Date());
        chaUserData.put("Status", UserStatus.OFFLINE);
        chaUserData.put("Department",visitorPrincipal.getDepartment());
        chaUserData.put("ScreenWH",visitorPrincipal.getScreenWH());

        sessionUtils.createOrModifyUser(user.getId(),chaUserData);

        user.setLocale(visitorPrincipal.getLocale());

        return user;
        */
    }

}

package org.mylivedata.app.connection.integration.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.mylivedata.app.connection.MessageType;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.messaging.DashboardService;
import org.mylivedata.app.dashboard.repository.service.SessionService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.mylivedata.app.webchat.domain.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.GrantedAuthority;

import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * Created by lubo08 on 30.10.2014.
 */
@MessageEndpoint
public class SessionLogService {

    private final Logger LOGGER = LoggerFactory.getLogger(SessionLogService.class);

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    public Message<?> convertUserAgentHeader (Message<?> message,
                                        @Header(value = "USER_AGENT", required = false) String userAgent) {
        return MessageBuilder.fromMessage(message).setHeader("USER_AGENT", userAgent != null ? UserAgent.parseUserAgentString(
                userAgent) : null).build();
    }


    public Message<?> logUserSession (Message<?> message,
                                   @Header(value ="REMOTE_ADDRESS", required = false) String remoteAddress,
                                   @Header("CREATION_TIME") long creationTime,
                                   @Header(value = "USER_AGENT", required = false) UserAgent userAgent,
                                   @Header(value = "DEPARTMENT", required = false) String departmentKey) {
        SecureUser userDetails = (SecureUser)message.getPayload();
        UserSessionsEntity sessionsEntity = new UserSessionsEntity();
        sessionsEntity.setSessionStart(new Timestamp(creationTime));
        sessionsEntity.setIp(remoteAddress);
        sessionsEntity.setUserId(userDetails.getId().intValue());
        if(userAgent != null) {
            sessionsEntity.setBrowser(userAgent.getBrowser().getName());
            sessionsEntity.setSystem(userAgent.getOperatingSystem().getName());
        }
        try {
            sessionsEntity = sessionService.registerNewSession(sessionsEntity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return MessageBuilder.fromMessage(message).setHeader("USER_SESSION",sessionsEntity).build();
    }

    public Message<?> registerChatUserIntoCache (Message<?> message,
                                             @Header(value ="REMOTE_ADDRESS", required = false) String ip,
                                             @Header("CREATION_TIME") long creationTime,
                                             @Header(value = "USER_AGENT", required = false) UserAgent userAgent,
                                             @Header(value = "DEPARTMENT", required = false) String departmentKey,
                                             @Header("USER_SESSION") UserSessionsEntity sessionsEntity,
                                             @Header(value = "CHAT_SESSION_ID") String chatSessionId) {

        SecureUser userDetails = (SecureUser)message.getPayload();
        BrowserType browserType = null;
        boolean isMobile = false;
        if(userAgent != null) {
            if(userAgent.getBrowser().getBrowserType() == BrowserType.MOBILE_BROWSER) isMobile = true;
            browserType = userAgent.getBrowser().getBrowserType();
        }
        boolean isOperator = false;
        for(Object role: userDetails.getAuthorities()){
            GrantedAuthority auth = (GrantedAuthority)role;
            if(auth.getAuthority().equals("ROLE_ADMIN"))
                isOperator = true;
        }
        ChatUser chatUser;
        Map<String,Object> chaUserData = new HashMap<>();
        chaUserData.put("Mobile",isMobile);
        chaUserData.put("BrowserType",browserType);
        chaUserData.put("Operator",isOperator);
        chaUserData.put("AccountId",userDetails.getAccountId().longValue());
        chaUserData.put("SessionId",sessionsEntity.getSessionId().longValue());
        chaUserData.put("Ip",ip);
        chaUserData.put("Browser",sessionsEntity.getBrowser());
        chaUserData.put("System",sessionsEntity.getSystem());
        chaUserData.put("CountryCode",sessionsEntity.getCountryCode());
        chaUserData.put("CountryName",sessionsEntity.getCountryName());
        chaUserData.put("HttpSessionOnline",true);
        chaUserData.put("ConnectedAt",new Date());
        chaUserData.put("Status", UserStatus.ONLINE);
        chaUserData.put("Department",departmentKey);
        chaUserData.put("chatSessionId",chatSessionId);
        chatUser = sessionUtils.createOrModifyUser(userDetails.getId(), chaUserData);

        List<Object> resp = new ArrayList<>();
        resp.add(chatUser);
        resp.add(userDetails);

        return MessageBuilder.fromMessage(message).withPayload(resp).build();
    }
    /*
    public void logOnlineUser (SecureUser userDetails,
                               @Header("USER_SESSION") UserSessionsEntity userSession) {
        try {
            userService.registerOnlineUser(userDetails.getId().intValue(), userDetails.getAuthorities(),userSession.getSessionId(),true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    */

    public Message<ChatUser> removeChatSession (Message<?> message,
                                      @Header("CHAT_SESSION_ID") String chatSessionId) {
        SecureUser userDetails = (SecureUser)message.getPayload();
        ChatUser chatUser = sessionUtils.removeSession(userDetails,chatSessionId);
        return MessageBuilder.fromMessage(message).withPayload(chatUser).setHeaderIfAbsent("USER",userDetails).build();
    }

    public Message<?> removeChatUser (Message<?> message) {
        ChatUser userDetails = (ChatUser)message.getPayload();
        sessionUtils.removeChatUser(userDetails);
        return message;
    }

    public Message<?> endUserSession (Message<?> message) {
        ChatUser userDetails = (ChatUser)message.getPayload();
        sessionUtils.updateFinishedSession(new Long(userDetails.getSessionId()).intValue());
        return message;
    }
    /*
    //@ServiceActivator(inputChannel = "logHttpSession")
    public void logHttpSession (SecureUser userDetails,
                                @Header("REMOTE_ADDRESS") String remoteAddress,
                                @Header("CREATION_TIME") long creationTime,
                                @Header(value = "USER_AGENT", required = false) String userAgent,
                                @Header(value = "DEPARTMENT", required = false) String departmentKey)
    {
            sessionUtils.logItAll(creationTime, userDetails, remoteAddress, userAgent, departmentKey);
            LOGGER.debug("Http session for user "+userDetails.getFirstName()+" stored");
    }
    */
    @ServiceActivator(inputChannel = "logChatSession", outputChannel = "newUserNotificationFilter")
    public SecureUser logChatSession (SecureUser user,
                                @Header("CHAT_SESSION_ID") String chatSessionId)
    {
        sessionUtils.logIsOnlineChat(user, chatSessionId);
        LOGGER.debug("Websocket session for user "+user.getFirstName()+" stored");
        return user;
    }

    //@ServiceActivator(inputChannel = "newUserNotification")
    public void notifyAboutNewUserOnline (SecureUser user,
                                @Header("CHAT_SESSION_ID") String chatSessionId)
    {
        boolean isOperator = false;
        for(Object role: user.getAuthorities()){
            GrantedAuthority auth = (GrantedAuthority)role;
            if(auth.getAuthority().equals("ROLE_ADMIN"))
                isOperator = true;
        }
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("type", isOperator?MessageType.OPERATOR_ARRIVED:MessageType.VISITOR_ARRIVED);
        message.put("id", UUID.randomUUID().toString());
        message.put("userID", user.getId());
        message.put("userName", user.getFirstName());
        message.put("onlineUsersCount",
                isOperator?sessionUtils.getOnlineOperatorsCount(user.getAccountId().longValue()):sessionUtils.getOnlineVisitorsCount(user.getAccountId().longValue()));
        message.put("goOnlineTitle",
                messageSource.getMessage("dashboard.notification.new.user.title",
                        new Object [] {user.getFirstName()},
                        user.getLocale()));
        message.put("goOnlineText", messageSource.getMessage("dashboard.notification.new.user.text",
                new Object [] {user.getFirstName()},
                user.getLocale()));

        dashboardService.sendMessageToChannel("/visitor/"+user.getAccountIdentity(),message);

        LOGGER.debug("new user notification "+user.getFirstName()+" sent");
    }

    //@ServiceActivator(inputChannel = "userGoOffline")
    public void userGoOfflineNotification(Message<?> msg,@Header("USER") SecureUser user){
        boolean isOperator = false;
        for(Object role: user.getAuthorities()){
            GrantedAuthority auth = (GrantedAuthority)role;
            if(auth.getAuthority().equals("ROLE_ADMIN"))
                isOperator = true;
        }
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("type", isOperator?MessageType.OPERATOR_LEFT:MessageType.VISITOR_LEFT);
        message.put("id", UUID.randomUUID().toString());
        message.put("userID", user.getId());
        message.put("userName", user.getFirstName());
        message.put("onlineUsersCount",
                isOperator?sessionUtils.getOnlineOperatorsCount(user.getAccountId().longValue()):sessionUtils.getOnlineVisitorsCount(user.getAccountId().longValue()));
        message.put("goOnlineTitle",
                messageSource.getMessage("dashboard.notification.user.offline.title",
                        new Object [] {user.getFirstName()},
                        user.getLocale()));
        message.put("goOnlineText", messageSource.getMessage("dashboard.notification.user.offline.text",
                new Object [] {user.getFirstName()},
                user.getLocale()));

        dashboardService.sendMessageToChannel("/visitor/"+user.getAccountIdentity(),message);

        LOGGER.debug("User Go offline notification /"+user.getFirstName());
    }

    @ServiceActivator(inputChannel = "disconnectChat")
    public void sendMessageAboutHttpSessionTimeOut(SecureUser secUser){
        ChatUser user = sessionUtils.getChatUser(secUser.getId());
        dashboardService.timOutMessage(user);
        LOGGER.debug("Sent disconnect message");
    }

}

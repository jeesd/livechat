package org.mylivedata.app.dashboard.integration.filter;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Created by lubo08 on 16.12.2014.
 */
@MessageEndpoint
public class NotificationFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationFilter.class);

    @Autowired
    private SessionUtils sessionUtils;

    //@Filter(inputChannel = "userGoOfflineFilter", outputChannel = "userGoOffline")
    public boolean notifyOnlyIfLastConnectionClosed(SecureUser secureUser) {
        ChatUser user = sessionUtils.getChatUser(secureUser.getId());
        if(user == null || user.getChatSessionIds() == null || user.getChatSessionIds().isEmpty())
            return true;
        LOGGER.debug(secureUser.getFirstName()+": Offline message not sent yet");
        return false;
    }

    @Filter(inputChannel = "newUserNotificationFilter", outputChannel = "newUserNotification")
    public boolean notifyOnlyIfFirstConnectionEstablishedIsOnline(SecureUser secureUser) {
        ChatUser user = sessionUtils.getChatUser(secureUser.getId());
        if(user != null && user.getChatSessionIds() != null && user.getChatSessionIds().size()==1)
            return true;
        LOGGER.debug(secureUser.getFirstName()+": Online message not sent yet");
        return false;
    }

    @Filter(inputChannel = "changeDepartmentFilter", outputChannel = "changeDepartment")
    public boolean ifDepartmentChangedNotify(SecureUser secureUser, @Header(value = "DEPARTMENT", required = false) String departmentKey){
        ChatUser user = sessionUtils.getChatUser(secureUser.getId());
        if(departmentKey != null && user != null && !departmentKey.equals(user.getDepartment())) {
            LOGGER.debug(departmentKey+": Will be changed for user: "+secureUser.getFirstName());
            return true;
        }
        return false;
    }

    public boolean isNoSession (Object message) {
        ChatUser user = (ChatUser)message;
        if(user != null && user.getChatSessionIds() != null && user.getChatSessionIds().size()==0)
            return true;
        return false;
    }

}

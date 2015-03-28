package org.mylivedata.app.dashboard;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.mylivedata.app.dashboard.domain.custom.DataTablesRequest;
import org.mylivedata.app.dashboard.domain.custom.DataTablesResponse;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.util.DataTablesUtils;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lubo08 on 14.3.2014.
 */
@RestController
public class DashBoardRestController {

    private final Logger LOGGER = LoggerFactory.getLogger(DashBoardRestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SessionUtils sessionUtils;
    
    @RequestMapping(value="/visitor-list-table", method= RequestMethod.POST)
    public @ResponseBody
    DataTablesResponse<ChatUser> loadVisitorsListTable(@RequestBody DataTablesRequest dtReq,HttpSession session) {

        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();

        DataTablesResponse<ChatUser> resp = new DataTablesResponse();
        resp.setDraw(dtReq.getDraw());
        Page<OnlineUsersView> onlineUserList = null;
        //dtReq.getOrder()
        List<ChatUser> rs = null;
        try {
            //onlineUserList = userService.getOnlineUsers(secUser.getAccountId(), DataTablesUtils.getPageRequest(dtReq));
            rs =  sessionUtils.getMyVisitors(secUser.getAccountId().longValue(), DataTablesUtils.getPageRequest(dtReq));

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setRecordsTotal(sessionUtils.getOnlineVisitorsCount(secUser.getAccountId().longValue()));
        resp.setRecordsFiltered(sessionUtils.getOnlineVisitorsCount(secUser.getAccountId().longValue()));
        resp.setData(rs);
        return resp;
    }

    @RequestMapping(value="/getUserSessionJson", method=RequestMethod.GET)
    public @ResponseBody SecureUser getUserSessionJson(HttpSession session) throws Exception {
        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();

        return secUser;
    }
}

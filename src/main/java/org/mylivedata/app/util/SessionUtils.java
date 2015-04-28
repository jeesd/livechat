package org.mylivedata.app.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.mylivedata.app.connection.BrowserConnectionManager;
import org.mylivedata.app.connection.SessionInfo;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.SessionService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.mylivedata.app.webchat.domain.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.Model;

import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Created by lubo08 on 18.9.2014.
 */


@Singleton
public class SessionUtils {

    private final Logger LOGGER = LoggerFactory.getLogger(SessionUtils.class);

    public static final String USER_CACHE_NAME = "usersCache";
    public static final String CONNECTION_CACHE_NAME = "connectionsCache";

    @Autowired
    private static AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private EhCacheCacheManager cacheManager;

    private Cache cache;
    private Cache connectionsCache;

    @PostConstruct
    public void afterInit(){
        cache = cacheManager.getCache(SessionUtils.USER_CACHE_NAME);
        connectionsCache = cacheManager.getCache(SessionUtils.CONNECTION_CACHE_NAME);
    }


    //@Async
    public void logItAll(long creationTime, SecureUser userDetails, String ip, String userAgentHeader, String departmentKey) {
        LOGGER.debug("session log start");
        UserSessionsEntity sessionsEntity = new UserSessionsEntity();
        //Device currentDevice = DeviceUtils.getCurrentDevice(request);
        sessionsEntity.setSessionStart(new Timestamp(creationTime));
        sessionsEntity.setIp(ip);
        sessionsEntity.setUserId(userDetails.getId().intValue());
        int sessionId = -1;
        BrowserType browserType = null;
        boolean isMobile = false;
        if(userAgentHeader != null) {
            UserAgent userAgent = UserAgent.parseUserAgentString(
                    userAgentHeader);
            sessionsEntity.setBrowser(userAgent.getBrowser().getName());
            sessionsEntity.setSystem(userAgent.getOperatingSystem().getName());
            if(userAgent.getBrowser().getBrowserType() == BrowserType.MOBILE_BROWSER) isMobile = true;
            browserType = userAgent.getBrowser().getBrowserType();
        }
        try {
            UserSessionsEntity userSession = sessionService.registerNewSession(sessionsEntity);
            userService.registerOnlineUser(userDetails.getId().intValue(), userDetails.getAuthorities(),userSession.getSessionId(),true);
            sessionId =  userSession.getSessionId();
            //session.setAttribute("sessionId", userSession.getSessionId());
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
            //chatUser.setOperator(isOperator);
            chaUserData.put("AccountId",userDetails.getAccountId().longValue());
            //chatUser.setAccountId(userDetails.getAccountId().longValue());
            //chatUser.setUserId(userDetails.getId());
            chaUserData.put("SessionId",userSession.getSessionId().longValue());
            //chatUser.setSessionId(userSession.getSessionId());
            chaUserData.put("Ip",ip);
            //chatUser.setIp(ip);
            chaUserData.put("Browser",userSession.getBrowser());
            //chatUser.setBrowser(userSession.getBrowser());
            chaUserData.put("System",userSession.getSystem());
            //chatUser.setSystem(userSession.getSystem());
            chaUserData.put("CountryCode",userSession.getCountryCode());
            //chatUser.setCountryCode(userSession.getCountryCode());
            chaUserData.put("CountryName",userSession.getCountryName());
            //chatUser.setCountryName(userSession.getCountryName());
            chaUserData.put("HttpSessionOnline",true);
            //chatUser.setHttpSessionOnline(true);
            chaUserData.put("ConnectedAt",new Date());
            //chatUser.setConnectedAt(new Date());
            chaUserData.put("Status",UserStatus.OFFLINE);
            //chatUser.setStatus(UserStatus.OFFLINE);
            chaUserData.put("Department",departmentKey);
            //chatUser.setDepartment(departmentKey);
            chatUser = createOrModifyUser(userDetails.getId(),chaUserData);
            //cacheManager.getCacheManager().getTransactionController().begin();
            //cacheManager.getTransactionController().begin();
            //cache.put(chatUser.getUserId(),chatUser);
            //cacheManager.getCacheManager().getTransactionController().commit();
        } catch (Exception e) {
            //cacheManager.getCacheManager().getTransactionController().rollback();
            LOGGER.debug(e.getMessage());
        }
        LOGGER.debug("session log end");

    }

    public ChatUser createOrModifyUser(Long userId,Map<String,Object> userData) {
        ChatUser chatUserFromCache = null;

        try {
        assert userData.isEmpty() : "User data mas be field even with one of field for update otherwise doesn't make sense call this";

            cacheManager.getCacheManager().getTransactionController().begin();
            chatUserFromCache = cache.get(userId)!=null?(ChatUser)cache.get(userId).get():null;
            if(chatUserFromCache == null){
                chatUserFromCache = new ChatUser();
                chatUserFromCache.setUserId(userId);
            }
            if(userData.get("Operator") != null) chatUserFromCache.setOperator((Boolean)userData.get("Operator"));
            if(userData.get("AccountId") != null) chatUserFromCache.setAccountId((Long)userData.get("AccountId"));
            if(userData.get("SessionId") != null) chatUserFromCache.setSessionId((Long)userData.get("SessionId"));
            if(userData.get("Ip") != null) chatUserFromCache.setIp((String)userData.get("Ip"));
            if(userData.get("Browser") != null) chatUserFromCache.setBrowser((String)userData.get("Browser"));
            if(userData.get("System") != null) chatUserFromCache.setSystem((String)userData.get("System"));
            if(userData.get("CountryCode") != null) chatUserFromCache.setCountryCode((String)userData.get("CountryCode"));
            if(userData.get("CountryName") != null) chatUserFromCache.setCountryName((String)userData.get("CountryName"));
            if(userData.get("HttpSessionOnline") != null) chatUserFromCache.setHttpSessionOnline((Boolean)userData.get("HttpSessionOnline"));
            if(userData.get("ConnectedAt") != null) chatUserFromCache.setConnectedAt((Date)userData.get("ConnectedAt"));
            if(userData.get("Status") != null) {
                chatUserFromCache.setStatus((UserStatus) userData.get("Status"));
                chatUserFromCache.setChatOnline(true);
            } else {
                chatUserFromCache.setStatus(UserStatus.OFFLINE);
                chatUserFromCache.setChatOnline(false);
            }
            if(userData.get("Department") != null) chatUserFromCache.setDepartment((String)userData.get("Department"));
            if(userData.get("Mobile") != null) chatUserFromCache.setMobile((Boolean) userData.get("Mobile"));
            if(userData.get("BrowserType") != null) chatUserFromCache.setBrowserType((BrowserType) userData.get("BrowserType"));
            if(userData.get("ScreenWH") != null) chatUserFromCache.setScreenWH((String) userData.get("ScreenWH"));
            if(userData.get("chatSessionId") != null) chatUserFromCache.addSessionId((String)userData.get("chatSessionId"));

            cache.put(chatUserFromCache.getUserId(),chatUserFromCache);
            cacheManager.getCacheManager().getTransactionController().commit();

            } catch (Exception e) {
                cacheManager.getCacheManager().getTransactionController().rollback();
                LOGGER.error("createOrModifyUser->"+e.getMessage());
            }

        return chatUserFromCache;
    }

    //@Async
    public void logIsOnlineChat(SecureUser userDetails, String chatSessionId){
        try {
            cacheManager.getCacheManager().getTransactionController().begin();
            userService.registerOnlineUser(userDetails.getId().intValue(), userDetails.getAuthorities(),null,false);
            ChatUser chatUser = (ChatUser)cache.get(userDetails.getId()).get();
            chatUser.setChatOnline(true);
            chatUser.setStatus(UserStatus.ONLINE);
            chatUser.addSessionId(chatSessionId);
            cache.put(chatUser.getUserId(),chatUser);
            cacheManager.getCacheManager().getTransactionController().commit();
        } catch (Exception e) {
            cacheManager.getCacheManager().getTransactionController().rollback();
            LOGGER.error(e.getMessage());
        }
    }

    @Async
    public void updateFinishedSession(Integer sessionId){
        try {
            sessionService.endSession(sessionId.intValue());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("session finished");
    }

    public ChatUser removeSession (SecureUser ud, String chatSessionId) {
        cacheManager.getCacheManager().getTransactionController().begin();
        ChatUser chatUser = (ChatUser)cache.get(ud.getId()).get();
        if(chatUser != null){
            chatUser.removeSessionId(chatSessionId);
        }
        cache.put(chatUser.getUserId(),chatUser);
        cacheManager.getCacheManager().getTransactionController().commit();
        return chatUser;
    }

    public void removeChatUser (ChatUser chatUser) {
        try {
            cacheManager.getCacheManager().getTransactionController().begin();
            cache.evict(chatUser.getUserId());
            cacheManager.getCacheManager().getTransactionController().commit();
        } catch (Exception e) {
            cacheManager.getCacheManager().getTransactionController().rollback();
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("Online user removed from ehcache");
    }

    //@Async
    public void unregisterOnlineUser(SecureUser ud,boolean isHttpSession,String chatSessionId){
        try {

            cacheManager.getCacheManager().getTransactionController().begin();
            ChatUser chatUser = (ChatUser)cache.get(ud.getId()).get();
            if(chatUser != null && isHttpSession){
                if(!chatUser.isChatOnline() && chatUser.getChatSessionIds().isEmpty()){
                    cache.evict(chatUser.getUserId());
                    userService.unregisterOnlineUser(ud,isHttpSession,true);
                }else{
                    chatUser.setHttpSessionOnline(false);
                    cache.put(chatUser.getUserId(),chatUser);
                    userService.unregisterOnlineUser(ud,isHttpSession,false);
                }
            }else if(chatUser != null && !isHttpSession){
                if(!chatUser.isHttpSessionOnline()){
                    chatUser.removeSessionId(chatSessionId);
                    if(chatUser.getChatSessionIds().isEmpty()) {
                        cache.evict(chatUser.getUserId());
                        userService.unregisterOnlineUser(ud,isHttpSession,true);
                    }
                }else{
                    chatUser.removeSessionId(chatSessionId);
                    if(chatUser.getChatSessionIds().isEmpty()) {
                        chatUser.setChatOnline(false);
                        chatUser.setStatus(UserStatus.OFFLINE);
                        userService.unregisterOnlineUser(ud,isHttpSession,false);
                    }
                    cache.put(chatUser.getUserId(),chatUser);
                }
            }
            cacheManager.getCacheManager().getTransactionController().commit();
        } catch (Exception e) {
            cacheManager.getCacheManager().getTransactionController().rollback();
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("Online user unregistered");
    }
    /* will be removed
    public void setDashboardInfo(Model m, HttpSession session, HttpServletRequest request, boolean loadFromDb) {

        if (loadFromDb) {
            setDashboardInfoFromDb(m, session);
        } else {
            setDashboardInfo(m, session, request);
        }
    }

    public void setDashboardInfo(Model m, HttpSession session, HttpServletRequest request) {
        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();

        String userImageUrl = "img/user.jpg";

//        if(securityContext.getAuthentication() instanceof SocialAuthenticationToken) {
//            SocialAuthenticationToken socialAuthToken = (SocialAuthenticationToken)securityContext.getAuthentication();
//            Connection<?> connection = socialAuthToken.getConnection();
//            userImageUrl = connection.createData().getImageUrl();
//        }


        m.addAttribute("langCountry", RequestContextUtils.getLocale(request));
        m.addAttribute("userName", secUser.getFirstName()+" "+(secUser.getLastName()!=null?secUser.getLastName():""));
        m.addAttribute("userImageUrl",userImageUrl);
        m.addAttribute("userEmail",secUser.getEmail());
        int visitorsCount = getOnlineVisitorsCount(secUser.getAccountId().longValue());
        int operatorsCount = getOnlineOperatorsCount(secUser.getAccountId().longValue());
        m.addAttribute("visitorsCount", visitorsCount);
        m.addAttribute("operatorsCount", operatorsCount);
    }
    */
    public void setDashboardInfoFromDb(Model m, HttpSession session) {

        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();

        UsersEntity user = new UsersEntity();
        try {
            user = userService.getUserById(Integer.parseInt(secUser.getId().toString()));
            session.setAttribute("user", user);
        } catch (Exception e) {
            LOGGER.error("setDashboardInfoFromDb - cannot get user by ID data from DB " + e);
        }
        //int visitorsCount = getOnlineVisitorsCount(user.getAccountId().longValue());
        m.addAttribute("userId", user.getUserId());
        m.addAttribute("userName", user.getName());
        m.addAttribute("userSurname", user.getSurrname());
        m.addAttribute("userFullName", user.getName()+" "+(user.getSurrname()!=null?user.getSurrname():""));
        m.addAttribute("userImageUrl", user.getImageUrl());
        m.addAttribute("userEmail",user.getEmail());
        //m.addAttribute("visitorsCount", visitorsCount);
    }

    public void addChatSession(String browserId, String sessionId,String transportName, String userHashId){
        cacheManager.getCacheManager().getTransactionController().begin();
        BrowserConnectionManager browserConnection = connectionsCache.get(browserId)!=null?(BrowserConnectionManager)connectionsCache.get(browserId).get():null;
        if(browserConnection == null){
            browserConnection = new BrowserConnectionManager();
        }
        browserConnection.addSessionId(new SessionInfo(sessionId,userHashId));
        browserConnection.setConnectionType(transportName);
        connectionsCache.put(browserId,browserConnection);
        cacheManager.getCacheManager().getTransactionController().commit();
    }
    public void  removeChatSession(String browserId, String sessionId){
        cacheManager.getCacheManager().getTransactionController().begin();
        BrowserConnectionManager browserConnection = connectionsCache.get(browserId)!=null?(BrowserConnectionManager)connectionsCache.get(browserId).get():null;
        if(browserConnection != null) {
            browserConnection.removeSessionId(sessionId);
            connectionsCache.put(browserId, browserConnection);
        }
        cacheManager.getCacheManager().getTransactionController().commit();
    }
    public void  updateChatSession(String browserId, String sessionId, String userHashId){
        BrowserConnectionManager browserConnection = connectionsCache.get(browserId)!=null?(BrowserConnectionManager)connectionsCache.get(browserId).get():null;
        if(browserConnection != null) {
            browserConnection.updateUserHashId(sessionId,userHashId);
            connectionsCache.put(browserId, browserConnection);
        }
    }
    public BrowserConnectionManager getBrowserConnectionsManager(String browserId){
        BrowserConnectionManager browserConnection = connectionsCache.get(browserId)!=null?(BrowserConnectionManager)connectionsCache.get(browserId).get():null;
        return browserConnection;
    }
    public List<Result> getMyOperators(Long accountId){
        List<Result> operators = new ArrayList<>();
        Ehcache nativeCache = (Ehcache)cache.getNativeCache();
        Attribute<Long> userAccountId = nativeCache.getSearchAttribute("accountId");
        Attribute<Boolean> isOperator = nativeCache.getSearchAttribute("isOperator");
        Query query = nativeCache.createQuery();
        Results results = query.includeValues().addCriteria(userAccountId.eq(accountId).and(isOperator.eq(true))).execute();
        operators = results.all();
        return operators;
    }

    public List<ChatUser> getMyVisitors(Long accountId,PageRequest request){
        List<ChatUser> visitors = new ArrayList<>();
        Ehcache nativeCache = (Ehcache)cache.getNativeCache();
        Attribute<Long> userAccountId = nativeCache.getSearchAttribute("accountId");
        Attribute<Boolean> isOperator = nativeCache.getSearchAttribute("isOperator");
        Query query = nativeCache.createQuery();
        List<Result> results = query.includeValues().includeKeys()
                .addCriteria(userAccountId.eq(accountId).and(isOperator.eq(false)))
                .execute().range(request.getOffset(),request.getPageSize());
        for(Result r: results){
            visitors.add((ChatUser)r.getValue());
        }
        return visitors;
    }

    public ChatUser getChatUser(long userId) {
        cacheManager.getCacheManager().getTransactionController().begin();
        ChatUser chatUser = cache.get(userId)!=null?(ChatUser)cache.get(userId).get():null;
        cacheManager.getCacheManager().getTransactionController().commit();
        return chatUser;
    }

    public Integer getOnlineOperatorsCount(Long accountId) {
        List<Result> operators = new ArrayList<>();
        Ehcache nativeCache = (Ehcache)cache.getNativeCache();
        Attribute<Long> userAccountId = nativeCache.getSearchAttribute("accountId");
        Attribute<Boolean> isOperator = nativeCache.getSearchAttribute("isOperator");
        Attribute<Boolean> isChatOnline = nativeCache.getSearchAttribute("isChatOnline");
        Query query = nativeCache.createQuery();
        Results results = query.includeValues().addCriteria(
                userAccountId.eq(accountId)
                        .and(isChatOnline.eq(true))
                        .and(isOperator.eq(true))).execute();
        operators = results.all();
        return operators.size();
    }

    public Integer getOnlineVisitorsCount(Long accountId) {

        List<Result> visitors = new ArrayList<>();
        Ehcache nativeCache = (Ehcache)cache.getNativeCache();
        Attribute<Long> userAccountId = nativeCache.getSearchAttribute("accountId");
        Attribute<Boolean> isOperator = nativeCache.getSearchAttribute("isOperator");
        Attribute<Boolean> isChatOnline = nativeCache.getSearchAttribute("isChatOnline");
        Query query = nativeCache.createQuery();
        Results results = query.includeValues().addCriteria(
                userAccountId.eq(accountId)
                        .and(isChatOnline.eq(true))
                        .and(isOperator.eq(false)
                        )
        ).execute();
        visitors = results.all();

        return visitors.size();
    }

}

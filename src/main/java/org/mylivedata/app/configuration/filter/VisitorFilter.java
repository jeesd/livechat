package org.mylivedata.app.configuration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;
import org.mylivedata.app.connection.domain.VisitorPrincipal;

/**
 * Created by lubo08 on 2.7.2014.
 */
public class VisitorFilter extends GenericFilterBean {

    private final Logger LOGGER = LoggerFactory.getLogger(VisitorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ((HttpServletResponse) response).setHeader("p3p", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
        HttpSession session =
                httpRequest.getSession();
        //VisitorPrincipal principal = new VisitorPrincipal(null,null,null);

        // try set only tracking cookie.
        if(httpRequest.getRequestURL().toString().contains("chat/template")) {
            String userIdentityHash = null;

            // check if tracking cookie exist if not set it.
            if(httpRequest.getCookies() != null)
                for (Cookie cookie : httpRequest.getCookies()) {
                    if ("cuhi".equals(cookie.getName().trim()))
                        userIdentityHash = cookie.getValue();
                }

            if (userIdentityHash == null) {

                String uuid = UUID.randomUUID().toString();
                userIdentityHash = uuid;
                Cookie cookie = new Cookie("cuhi",userIdentityHash);
                cookie.setMaxAge(28800000);
                cookie.setHttpOnly(true);
                ((HttpServletResponse) response).addCookie(cookie);

            }
            session.setAttribute("userIdentityHash",userIdentityHash);
            //principal.setUserHashId(userIdentityHash);
        }

            //sessionLogGateway.userReloadInfo(secUser,httpRequest.getParameter("departmentKey"),originUrl);
            //LOGGER.debug("department changed to "+httpRequest.getParameter("departmentKey")+" user: "+secUser.getFirstName());

            URL originUrl = null;
            URL refererUrl = null;
            try {
                if(httpRequest.getHeader("Origin") != null){
                    originUrl = new URL(httpRequest.getHeader("Origin"));
                }
                if(httpRequest.getHeader("Referer") != null){
                    refererUrl = new URL(httpRequest.getHeader("Referer"));
                }
            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }

            //principal.setOrigin(originUrl!=null?originUrl.getHost():null);
            //principal.setReferrer(refererUrl!=null?refererUrl.getHost():null);
            //principal.setAccountIdHash(httpRequest.getParameter("accountId"));
            //principal.setDepartment(httpRequest.getParameter("departmentKey"));
            //principal.setLocale(httpRequest.getParameter("language")!=null?(new Locale(httpRequest.getParameter("language"))):RequestContextUtils.getLocale(httpRequest));
            //principal.setRemoteAddress(httpRequest.getRemoteAddr());
            //session.setAttribute("principal",principal);


        chain.doFilter(request, response);
    }
}

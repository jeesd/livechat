package org.mylivedata.app.configuration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lubo08 on 24.10.2014.
 */
public class AjaxRedirectFilter  extends GenericFilterBean {

    private final Logger LOGGER = LoggerFactory.getLogger(AjaxRedirectFilter.class);
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    public static final int ACCESS_DENIED_AJAX_ERROR_CODE = 901;
    public static final int SESSION_EXPIRED_AJAX_ERROR_CODE = 902;
    public static final int MISSING_CSRF_TOKEN_AJAX_ERROR_CODE = 903;
    public static final int INVALID_CSRF_TOKEN_AJAX_ERROR_CODE = 904;
    public static final int INVALID_AUTHENTICATION_AJAX_ERROR_CODE = 905;
    public static final int OTHER_AJAX_ERROR_CODE = 909;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (IOException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            String ajaxHeader = ((HttpServletRequest) servletRequest).getHeader("X-Requested-With");

            if("XMLHttpRequest".equals(ajaxHeader)) {
                Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);

                int responceAjaxExceptionCode = AjaxRedirectFilter.OTHER_AJAX_ERROR_CODE;
                if (throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain) != null) {
                    AccessDeniedException accessDeniedException =
                            (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
                    if (accessDeniedException instanceof MissingCsrfTokenException) {
                        responceAjaxExceptionCode = AjaxRedirectFilter.MISSING_CSRF_TOKEN_AJAX_ERROR_CODE;
                    } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
                        responceAjaxExceptionCode = AjaxRedirectFilter.INVALID_CSRF_TOKEN_AJAX_ERROR_CODE;
                    } else if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                        responceAjaxExceptionCode = AjaxRedirectFilter.SESSION_EXPIRED_AJAX_ERROR_CODE;
                    } else {
                        responceAjaxExceptionCode = AjaxRedirectFilter.ACCESS_DENIED_AJAX_ERROR_CODE;
                    }
                } else if (throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain) != null) {
                    responceAjaxExceptionCode = AjaxRedirectFilter.INVALID_AUTHENTICATION_AJAX_ERROR_CODE;
                }

                LOGGER.debug("Ajax error response code: "+responceAjaxExceptionCode);

                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                String contentType = "application/json";
                resp.setContentType(contentType);
                resp.setStatus(responceAjaxExceptionCode);
                // forward to error page.
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/ajax-error");
                dispatcher.forward(servletRequest, resp);

            }else{
                throw ex;
            }
        }
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer
    {
        /**
         * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
         */
        protected void initExtractorMap()
        {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor()
            {
                public Throwable extractCause(Throwable throwable)
                {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }
}

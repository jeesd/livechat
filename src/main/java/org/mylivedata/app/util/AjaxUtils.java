package org.mylivedata.app.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AjaxUtils {

    public boolean isAjaxRequest(HttpServletRequest webRequest) {
        String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
    }

    public boolean isAjaxUploadRequest(HttpServletRequest webRequest) {
        return webRequest.getParameter("ajaxUpload") != null;
    }
}

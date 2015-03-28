package org.mylivedata.app.dashboard;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mylivedata.app.dashboard.domain.custom.ErrorMessageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Created by lubo08 on 28.3.2014.
 */
@Controller
public class LoginPageController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="/signin", method= {RequestMethod.GET,RequestMethod.POST})
    public String loadLoginPage(Model m,HttpServletRequest request, HttpServletResponse response) {
        ErrorMessageView error = new ErrorMessageView();
        if(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null){
            error.setErrorMessage(messageSource.getMessage("login.fail", null, RequestContextUtils.getLocale(request)));
            m.addAttribute("error", error);
        }else if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            error.setErrorMessage(messageSource.getMessage("login.fail.token", null, RequestContextUtils.getLocale(request)));
            m.addAttribute("error", error);
        }

        return "login";
    }


    @RequestMapping(value="/password/reset", method=RequestMethod.GET)
    public String resetPassword (Model m) throws Exception {

        m.addAttribute("messageType", "pswResetLinkSent");

        return "login";
    }

    @RequestMapping(value="/safix", method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody Object
    safariFix (HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map data = new HashMap();
        //m.addAttribute("referer", request.getHeader("Referer"));
        data.put("jsessionid",request.getSession().getId());

        //response.setHeader("X-Frame-Options","allow");
        return data;
    }

}

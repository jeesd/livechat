package org.mylivedata.app.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mylivedata.app.configuration.filter.AjaxRedirectFilter;
import org.mylivedata.app.dashboard.domain.custom.ErrorMessageView;
import org.mylivedata.app.exceptions.RegistrationNotFountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Created by lubo08 on 9.4.2014.
 */
@ControllerAdvice
@Controller
public class HTTPErrorController {

    @Autowired
    private MessageSource messageSource;

    //@ExceptionHandler(org.springframework.web.servlet.handler.PageNotFound.class)
    @RequestMapping("/error404")
    public ModelAndView error404() {
        return new ModelAndView("error404");
    }

    @RequestMapping("/error403")
    public ModelAndView error403(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("error403");
        ErrorMessageView message = new ErrorMessageView();
        message.setErrorCode(response.getStatus());
        if(response.getStatus() == AjaxRedirectFilter.MISSING_CSRF_TOKEN_AJAX_ERROR_CODE || response.getStatus() == AjaxRedirectFilter.INVALID_CSRF_TOKEN_AJAX_ERROR_CODE)
            message.setErrorMessage(messageSource.getMessage("error.message."+response.getStatus(), null, RequestContextUtils.getLocale(request)));
        else
            message.setErrorMessage(messageSource.getMessage("error.403.message", null, RequestContextUtils.getLocale(request)));

        message.setForwardUrl(request.getRequestURL().toString());
        message.setButtonText(messageSource.getMessage("error.go.back", null, RequestContextUtils.getLocale(request)));
        model.addObject("errorMsg", message);
        return model;
    }


    @ExceptionHandler(RegistrationNotFountException.class)
    public ModelAndView handleCustomException(RegistrationNotFountException ex, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("customerror");
        //model.addObject("errCode", ex.getErrCode());
        String errorMessage = messageSource.getMessage("error.registration.not.found", null, RequestContextUtils.getLocale(request));
        model.addObject("errMsg", errorMessage);
        model.addObject("returnUrl", request.getRequestURL());
        return model;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex, HttpServletRequest request) {

        ModelAndView model = new ModelAndView("customerror");
        model.addObject("errMsg", ex.getMessage());
        model.addObject("returnUrl", request.getRequestURL());
        return model;

    }

    @ExceptionHandler(MailException.class)
    public ModelAndView handleMailException(RegistrationNotFountException ex, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("customerror");
        //model.addObject("errCode", ex.getErrCode());
        //String errorMessage = messageSource.getMessage("error.registration.not.found", null, RequestContextUtils.getLocale(request));
        model.addObject("errMsg", "Mail client not work !!!!");
        model.addObject("returnUrl", request.getRequestURL());
        return model;
    }

    @RequestMapping("/ajax-error")
    public @ResponseBody
    ErrorMessageView handleAjaxRequestErrorMessage(HttpServletRequest request, HttpServletResponse response){
        ErrorMessageView message = new ErrorMessageView();
        message.setErrorCode(response.getStatus());
        message.setErrorMessage(messageSource.getMessage("error.message."+response.getStatus(), null, RequestContextUtils.getLocale(request)));
        message.setForwardUrl("/");
        message.setButtonText(messageSource.getMessage("global.refresh", null, RequestContextUtils.getLocale(request)));
        return message;
    }

}

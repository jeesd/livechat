package org.mylivedata.app.dashboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mylivedata.app.dashboard.domain.custom.AccountFormView;
import org.mylivedata.app.dashboard.domain.custom.CreateUserFormDataView;
import org.mylivedata.app.dashboard.domain.custom.ProfileFormView;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.util.AjaxUtils;
import org.mylivedata.app.util.ImageUploadUtil;
import org.mylivedata.app.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfilePageController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProfilePageController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionUtils sessionInfoUtils;
    @Autowired
    private ImageUploadUtil imageUploadUtil;
    @Autowired
    private AjaxUtils ajaxUtils;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String loadProfilePage(Model m, HttpSession session) throws Exception {
        SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
        SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();
        sessionInfoUtils.setDashboardInfo(m,session,true);

        ProfileFormView profileFormView = new ProfileFormView();
        profileFormView.setEmail(secUser.getEmail());
        profileFormView.setName(secUser.getFirstName());
        profileFormView.setSurrname(secUser.getLastName());
        m.addAttribute("profileFormView", profileFormView);
        m.addAttribute("createUserFormDataView", new CreateUserFormDataView());
        m.addAttribute("accountFormView", new AccountFormView());
        return "profile";
    }

    @RequestMapping(value="/profile", method=RequestMethod.POST)
    public String saveProfileForm(Model m, @Valid ProfileFormView profile, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        m.addAttribute("createUserFormDataView", new CreateUserFormDataView());
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxUtils.isAjaxRequest(request) ? "profile :: profile-save-form" : "profile";
        }

        sessionInfoUtils.setDashboardInfo(m,session);
        try {
            SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
            SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();
            profile.setUserId(secUser.getId().intValue());
            userService.updateUserProfile(profile);
            secUser.setEmail(profile.getEmail());
            secUser.setFirstName(profile.getName());
            secUser.setLastName(profile.getSurrname());
        } catch(Exception e) {
            LOGGER.error("Cannot update user profile " + e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return ajaxUtils.isAjaxRequest(request) ? "profile :: profile-save-form" : "profile";
    }

    @RequestMapping(value="/change-password", method=RequestMethod.POST)
    public String changePassword(Model m, @Valid CreateUserFormDataView password, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        m.addAttribute("profileFormView", new ProfileFormView());
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxUtils.isAjaxRequest(request) ? "profile :: password-update-form" : "profile";
        }

        sessionInfoUtils.setDashboardInfo(m,session);
        try {
            userService.updateUserPassword(password.getUserId(), password.getPassword());
        } catch(Exception e) {
            LOGGER.error("Cannot update user password " + e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return ajaxUtils.isAjaxRequest(request) ? "profile :: password-update-form" : "profile";
    }

    @RequestMapping(value="/change-avatar", method=RequestMethod.POST)
    public String changeAvatar(Model m, @RequestParam("imageUrl") MultipartFile imageUrl, HttpSession session,
                               HttpServletRequest request, HttpServletResponse response) {

        sessionInfoUtils.setDashboardInfo(m,session);
        if (!imageUrl.isEmpty()) {
            try {
                SecurityContext securityContext = (SecurityContext)session.getAttribute("SPRING_SECURITY_CONTEXT");
                SecureUser secUser = (SecureUser)securityContext.getAuthentication().getPrincipal();

                imageUploadUtil.saveImage("img/avatar/" + imageUrl.getOriginalFilename(), imageUrl);
                userService.updateUserAvatar(secUser.getId().intValue(), "img/avatar/" + imageUrl.getOriginalFilename());
            } catch(Exception e) {
                LOGGER.error("Cannot upload user avatar " + e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return ajaxUtils.isAjaxRequest(request) ? "profile :: change-avatar-form" : "profile";
    }

    @RequestMapping(value="/change-account", method=RequestMethod.POST)
    public String saveAccountForm(Model m, @Valid AccountFormView account, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        sessionInfoUtils.setDashboardInfo(m,session);
        //m.addAttribute("createUserFormDataView", new CreateUserFormDataView());
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return ajaxUtils.isAjaxRequest(request) ? "profile :: account-save-form" : "profile";
        }

        try {
            accountService.updateAccountFormView(account);
            response.setHeader("responseMessage", messageSource.getMessage("global.accountChanged", null, request.getLocale()));
        } catch(Exception e) {
            LOGGER.error("Cannot update user account " + e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return ajaxUtils.isAjaxRequest(request) ? "profile :: account-save-form" : "profile";
    }
}

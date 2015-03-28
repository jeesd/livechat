package org.mylivedata.app.dashboard;

import java.util.UUID;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.ConfirmEmailView;
import org.mylivedata.app.dashboard.domain.custom.CreateUserFormDataView;
import org.mylivedata.app.dashboard.domain.custom.RegistrationFormDataView;
import org.mylivedata.app.dashboard.repository.AccountsEntityRepository;
import org.mylivedata.app.dashboard.repository.UserConnectionRepository;
import org.mylivedata.app.dashboard.repository.UsersEntityRepository;
import org.mylivedata.app.dashboard.repository.service.SignUpAccountService;
import org.mylivedata.app.dashboard.repository.service.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Created by lubo08 on 18.3.2014.
 */
@SessionAttributes("myAccount")
@Controller
public class RegistrationPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationPageController.class);
    
    @Autowired
    private SignUpAccountService signUpAccountService;
    
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AccountsEntityRepository accountService;
    @Autowired
	private UserConnectionRepository userConnectionRepository;
	@Autowired
	private final UsersEntityRepository userRepository;

    private final ProviderSignInUtils providerSignInUtils;

	@Inject
	public RegistrationPageController(UsersEntityRepository userRepository) {
		this.userRepository = userRepository;
		this.providerSignInUtils = new ProviderSignInUtils();
	}
	
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String loadFormPage(Model m, WebRequest request) throws Exception {
    	
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);

        if (connection != null) {
			if (connection.fetchUserProfile().getEmail() != null) {
				try {
					signUpService.signUpByUserSocial(connection, connection.fetchUserProfile().getEmail());
				} catch (Exception e) {
		    		LOGGER.error("Error sign up by social! " + e);
		            throw new Exception(e);
				}
				return "redirect:/";
			} else {
				m.addAttribute("confirmEmailView", new ConfirmEmailView());
				return "confirmemail";
			}
		} else {
			m.addAttribute("registrationFormDataView", new RegistrationFormDataView());
			return "register";
		}
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String submitSignUpForm(@Valid RegistrationFormDataView subscriber,
                                   BindingResult result,
                                   Model m,
                                   HttpServletRequest request) throws Exception {

        if(result.hasErrors()) {
            return "register";
        }
        UsersEntity user = signUpService.getUserByEmail(subscriber.getEmail());
        if(user != null){
            m.addAttribute("message", "userAlreadyExist");
            return "register";
        }
        //signUpService.signUpNewUser(subscriber);
        final Context ctx = new Context(RequestContextUtils.getLocale(request));
        ctx.setVariable("test", subscriber.getEmail());
        ctx.setVariable("mail_registration_account_verify",
                messageSource.getMessage("mail.registration.account.verify", null, RequestContextUtils.getLocale(request)));
        ctx.setVariable("mail_registration_text",
                messageSource.getMessage("mail.registration.text",
                        null,
                        RequestContextUtils.getLocale(request)));
        ctx.setVariable("mail_registration_verify_button",
                messageSource.getMessage("mail.registration.verify.button",null,RequestContextUtils.getLocale(request)));
        ctx.setVariable("mail_registration_name",
                messageSource.getMessage("mail.registration.name",
                        new Object [] {subscriber.getName().trim()!=null?subscriber.getName().trim():subscriber.getEmail().split("@")[0]},
                        RequestContextUtils.getLocale(request)));
        ctx.setVariable("mail_registration_visit_site",
                messageSource.getMessage("mail.registration.visit.site",null,RequestContextUtils.getLocale(request)));
        ctx.setVariable("mail_registration_unsubscribe",
                messageSource.getMessage("mail.registration.unsubscribe",null,RequestContextUtils.getLocale(request)));

        String regUuid = UUID.randomUUID().toString();
        String confirmUrl = String.format("%s://%s:%d%s/confirm/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath(),
                regUuid);
        String unregisterUrl = String.format("%s://%s:%d%s/unsubscribe/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath(),
                regUuid);
        //ctx.setVariable("base_url", request.getHeader("origin")+"/registration/confirm/"+regUuid); unregister_url
        ctx.setVariable("base_url", confirmUrl);
        ctx.setVariable("unregister_url", unregisterUrl);

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(messageSource.getMessage("mail.registration.subject",null,RequestContextUtils.getLocale(request)));
        message.setFrom("it.lubo@gmail.com");
        message.setTo(subscriber.getEmail());

        final String htmlContent = templateEngine.process("/mail/registration_confirm", ctx);
        message.setText(htmlContent, true); // true = isHtml

        signUpAccountService.signUpNewAccount(subscriber,regUuid);

        mailSender.send(mimeMessage);

        m.addAttribute("message", "registered");
        return "register";

    }

    @RequestMapping(value="/confirmemail", method=RequestMethod.POST)
    public String confirmEmail(@Valid ConfirmEmailView subscriber,
    							BindingResult result,
					            Model m,
					            WebRequest request) throws Exception {
    	
        if(result.hasErrors()) {
            return "confirmemail";
        }
        
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
		if (connection != null && subscriber.getEmail() != null) {
			LOGGER.info("Confirm Email: " + subscriber.getEmail());
			signUpService.signUpByUserSocial(connection, subscriber.getEmail());

			return "redirect:/";
		}
		
    	return "redirect:/signup";
    }

    @RequestMapping(value="/confirm/{registrationCode}", method=RequestMethod.GET)
    public String confirmAccount(Model m,
                                 @PathVariable String registrationCode) throws Exception {
        m.addAttribute("createUserFormDataView", new CreateUserFormDataView());
        AccountsEntity account = null;

        account = signUpService.getRegistrationByTokenAndVerify(registrationCode);
        m.addAttribute("myAccount", account);

        return "confirm";
    }

    @RequestMapping(value="/confirm", method=RequestMethod.POST)
    public String createAccountUser(@Valid CreateUserFormDataView newUser,
                                    BindingResult result,
                                    Model m,
                                    @ModelAttribute("myAccount") AccountsEntity myAccount) throws Exception {

        if(result.hasErrors()) {
            return "confirm";
        }

        signUpService.confirmRegistrationAndCreateUser(newUser,myAccount);

        return "redirect:/signin";
    }

    @RequestMapping(value="/unsubscribe/{registrationCode}", method=RequestMethod.GET)
    public String unsubscribeAccount(Model m,
                                 @PathVariable String registrationCode) throws Exception {
        AccountsEntity account = null;
        account = signUpService.getRegistrationByTokenAndVerify(registrationCode);
        signUpService.removeAccount(account);
        return "redirect:/signup";
    }

    //@ModelAttribute("myAccount") MyCounter myCounter
}

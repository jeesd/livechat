package org.mylivedata.app.webchat.messaging;

import org.cometd.annotation.RemoteCall;
import org.cometd.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Named;
import javax.inject.Singleton;

import java.util.Map;

/**
 * Created by lubo08 on 25.1.2015.
 */
@Named
@Singleton
@Service
public class RemoteCallService {
    
    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @RemoteCall("layout")
    public void retrieveContacts(final RemoteCall.Caller caller, final Object data)
    {
        // Perform a lengthy query to the database to
        // retrieve the contacts in a separate thread.
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Map<String, Object> arguments = (Map<String, Object>)data;
                    String style = (String)arguments.get("style");
                    String component = (String)arguments.get("component");
                    final Context ctx = new Context(LocaleContextHolder.getLocale());
                    final String chat = templateEngine.process("db:html:"+style+":"+component, ctx);

                    // We got the contacts, respond.
                    caller.result(chat);
                }
                catch (Exception x)
                {
                    // Uh-oh, something went wrong.
                    caller.failure(x.getMessage());
                }
            }
        }).start();
    }
}

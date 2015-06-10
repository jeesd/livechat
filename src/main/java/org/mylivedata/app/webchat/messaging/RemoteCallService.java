package org.mylivedata.app.webchat.messaging;

import org.cometd.annotation.RemoteCall;
import org.cometd.annotation.Service;
import org.springframework.stereotype.Component;

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
                    String userId = (String)arguments.get("userId");
                    //List<String> contacts = retrieveContactsFromDatabase(userId);

                    // We got the contacts, respond.
                    caller.result("OK");
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

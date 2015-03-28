package org.mylivedata.app.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SignInUtil {

    @Autowired
    private static AuthenticationManager authenticationManager;

	public static void signIn(String userId) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
        //Authentication res = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, null, null));
        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, null));
	}
	
}

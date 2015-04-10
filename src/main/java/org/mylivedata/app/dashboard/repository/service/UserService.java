package org.mylivedata.app.dashboard.repository.service;

import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.mylivedata.app.dashboard.domain.custom.ProfileFormView;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * Created by lubo08 on 2.4.2014.
 */
public interface UserService {

    public SecureUser getSecureUserByHash(VisitorPrincipal principal)  throws UsernameNotFoundException;

    public void registerOnlineUser(Integer userId, Collection<?> authorities, Integer sessionId, boolean isHttpSession) throws Exception;
	
	public UsersEntity save(UsersEntity user) throws Exception;
	
	public void delete(UsersEntity user);

    public UsersEntity getUserByEmail(String email) throws Exception;

    public UsersEntity getUserById(int id) throws Exception;

    public SecureUser getSecureUserByEmail(String email) throws UsernameNotFoundException;

    public void unregisterOnlineUser(SecureUser user, boolean isHttpSessionEvent, boolean isDelete) throws Exception;

    public SecureUser getSecureUserById(int id) throws UsernameNotFoundException;

    public Page<OnlineUsersView> getOnlineUsers(Integer id,PageRequest request) throws Exception;
    
    public void updateUserProfile(ProfileFormView profile) throws Exception;

    public void updateUserPassword(int userId, String password) throws Exception;

    public void updateUserAvatar(int userId, String imageUrl) throws Exception;
}

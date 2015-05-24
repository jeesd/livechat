package org.mylivedata.app.dashboard.repository.service.implementation;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.dashboard.domain.AccountsEntity;
import org.mylivedata.app.dashboard.domain.AssocGroupRoleEntity;
import org.mylivedata.app.dashboard.domain.AssocUserGroupsEntity;
import org.mylivedata.app.dashboard.domain.DomainsEntity;
import org.mylivedata.app.dashboard.domain.OnlineUsersEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.mylivedata.app.dashboard.domain.custom.ProfileFormView;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.AssocUserGroupsEntityRepository;
import org.mylivedata.app.dashboard.repository.DomainsEntityRepository;
import org.mylivedata.app.dashboard.repository.OnlineUsersRepository;
import org.mylivedata.app.dashboard.repository.UsersEntityRepository;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by lubo08 on 2.4.2014.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory
            .getLogger(UserServiceImpl.class);

    //@Qualifier("usersEntityRepository")
    @Autowired
    private UsersEntityRepository usersEntityRepository;

    //@Qualifier("domainsEntityRepository")
    @Autowired
    private DomainsEntityRepository domainsEntityRepository;

    //@Qualifier("assocUserGroupsEntityRepository")
    @Autowired
    private AssocUserGroupsEntityRepository assocUserGroupsEntityRepository;

    //@Qualifier("onlineUsersRepository")
    @Autowired
    private OnlineUsersRepository onlineUsersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UsersEntity save(UsersEntity user) throws Exception {

        try {
            user = usersEntityRepository.saveAndFlush(user);
        }catch (Exception e){
            LOGGER.info("Cannot save user " + e.getMessage());
            throw new Exception(e);
        }
        
        return user;
    }
    
	@Override
	public void delete(UsersEntity user) {
	}
	
    @Override
    public UsersEntity getUserByEmail(String email) throws Exception {
        List<UsersEntity> users = null;
        try {
            users = usersEntityRepository.findByEmail(email);
        }catch (Exception e){
            LOGGER.info("Cannot get user by email " + e.getMessage());
            throw new Exception(e);
        }

        if(users != null && !users.isEmpty())
            return users.get(0);
        else
            return null;
    }

    @Override
    public UsersEntity getUserById(int id) throws Exception {
        UsersEntity users = null;
        try {
            users = usersEntityRepository.findByUserId(id);
        }catch (Exception e){
            LOGGER.info("Cannot get user by id " + e.getMessage());
            throw new Exception(e);
        }

        return users;
    }

    public UsersEntity createNewVisitor(AccountsEntity account, String identity) {

        UsersEntity visitor = new UsersEntity();
        visitor.setName(getGeneratedVisitorName(account.getAccountId()));
        visitor.setAccountId(account.getAccountId());
        visitor.setIsCredentialsNonExpired("true");
        visitor.setLastLogin(new Timestamp(new Date().getTime()));
        visitor.setRegistration(new Timestamp(new Date().getTime()));
        visitor.setIdentificationHash(identity);
        visitor.setPassword(UUID.randomUUID().toString());
        visitor.setAccountsByAccountId(account);
        visitor = usersEntityRepository.saveAndFlush(visitor);

        AssocUserGroupsEntity assocUserGroupsEntity = new AssocUserGroupsEntity();
        assocUserGroupsEntity.setUserId(visitor.getUserId());
        assocUserGroupsEntity.setGroupId(3);
        assocUserGroupsEntity.setUsersByUserId(visitor);
        assocUserGroupsEntity = assocUserGroupsEntityRepository.saveAndFlush(assocUserGroupsEntity);

        //List<AssocUserGroupsEntity> auge =  new ArrayList<AssocUserGroupsEntity>();
        //auge.add(assocUserGroupsEntity);
        //visitor.setAssocUserGroupsesByUserId(auge);

        return visitor;
    }

    private String getGeneratedVisitorName(int accountId){
        String name = null;
        List<UsersEntity> visitor = null;

        name = "Visitor"+Math.round(Math.random() * 100000 + 1);

        while(true) {
            try {
                visitor = usersEntityRepository.findByNameAndAccountId(name, accountId);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
            if(visitor.isEmpty()){
                break;
            }else{
                name = "Visitor"+Math.floor(Math.random()*100000+1);
            }
        }

        return name;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void registerOnlineUser(Integer userId, Collection<?> authorities, Integer sessionId, boolean isHttpSession) throws Exception {
        OnlineUsersEntity onlineUser = null;
        if(!onlineUsersRepository.exists(userId)){
            onlineUser = new OnlineUsersEntity();
            onlineUser.setStatus(0);
            onlineUser.setUserId(userId);
            if(isHttpSession){
                onlineUser.setIsHttpOnline(1);
                onlineUser.setIsOnline(0);
            }else{
                onlineUser.setIsHttpOnline(0);
                onlineUser.setIsOnline(1);
            }
            onlineUser.setSessionId(sessionId);
            boolean isOperator = false;
            for(Object role: authorities){
                GrantedAuthority auth = (GrantedAuthority)role;
                if(auth.getAuthority().equals("ROLE_ADMIN"))
                    isOperator = true;
            }
            onlineUser.setIsOperator(isOperator?1:0);
            onlineUsersRepository.saveAndFlush(onlineUser);
        }else{
            if(isHttpSession) {
                updateHttpOnline(userId,sessionId);
            }else {
                updateOnlineUser(userId);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public void unregisterOnlineUser(SecureUser user, boolean isHttpSessionEvent, boolean isDelete) throws Exception {
        OnlineUsersEntity onlineUser = onlineUsersRepository.getUserById(user.getId().intValue());
        if (isDelete) {
            onlineUsersRepository.delete(onlineUser);
        } else if(isHttpSessionEvent) {
            onlineUser.setIsHttpOnline(0);
            onlineUsersRepository.saveAndFlush(onlineUser);
        } else {
            onlineUser.setIsOnline(0);
            onlineUsersRepository.saveAndFlush(onlineUser);
        }
    }

    private void updateOnlineUser(int userId) throws Exception {
        onlineUsersRepository.updateIsOnline(1,userId);
    }

    private void updateHttpOnline(int userId, Integer sessionId) throws Exception {
        onlineUsersRepository.updateIsHttpOnline(1,userId,sessionId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public SecureUser getSecureUserByEmail(String email) throws UsernameNotFoundException {
        List<UsersEntity> userList = usersEntityRepository.findByEmail(email);
        if(userList == null || userList.isEmpty())
            throw new UsernameNotFoundException("No user found: " + email);
        UsersEntity user = userList.get(0);
        return convertUserEntityToSecureUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public SecureUser getSecureUserById(int id) throws UsernameNotFoundException {
        UsersEntity user = usersEntityRepository.findByUserId(id);
        if(user == null)
            throw new UsernameNotFoundException("No user found: " + id);
        return convertUserEntityToSecureUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Exception.class)
    public SecureUser getSecureUserByHash(VisitorPrincipal principal)  throws UsernameNotFoundException{
        List<UsersEntity> visitor = null;
        List<DomainsEntity> domains;

        try{
            URL originUrl = null;
            if(principal.getOrigin() != null){
                originUrl = new URL(principal.getOrigin());
            }
            domains = domainsEntityRepository.findByDomain(originUrl.getHost());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }

        if(domains.isEmpty()) {
            throw new UsernameNotFoundException("Domain is not registered");
        }
        assert (principal.getUserHashId()==null);
        try{
            visitor = usersEntityRepository.findByIdentificationHash(principal.getUserHashId());
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        if(visitor != null && !visitor.isEmpty())
            return convertUserEntityToSecureUser(visitor.get(0));
        else
            return convertUserEntityToSecureUser(createNewVisitor(domains.get(0).getAccountsByAccountId(), principal.getUserHashId()));
    }

    private SecureUser convertUserEntityToSecureUser(UsersEntity user){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(user.getAssocUserGroupsesByUserId() != null)
            for(AssocUserGroupsEntity age: user.getAssocUserGroupsesByUserId()){
                for(AssocGroupRoleEntity agre: age.getGroupsByGroupId().getAssocGroupRolesByGroupId()){
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(agre.getRolesByRoleId().getRoleName());
                    authorities.add(authority);
                }
            }
        else {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_VISITOR");
            authorities.add(authority);
        }
        
        if (StringUtils.isEmpty(user.getName())) {
        	user.setName(user.getEmail().split("@")[0]);
        }
        
        SecureUser secureUser = new SecureUser(user.getName(),user.getPassword()==null?UUID.randomUUID().toString():user.getPassword(),authorities);
        secureUser.setCredentialsNonExpired(user.getIsCredentialsNonExpired().equals("true")?true:false);
        secureUser.setLastName(user.getSurrname());
        secureUser.setUserId(user.getUserId().longValue());
        secureUser.setImageUrl(user.getImageUrl());
        secureUser.setEmail(user.getEmail());
        secureUser.setAccountId(user.getAccountId());
        secureUser.setAccountIdentity(user.getAccountsByAccountId().getAccountIdentity());
        return secureUser;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public Page<OnlineUsersView> getOnlineUsers(Integer id,PageRequest request) throws Exception {

        //Page<OnlineUsersView> onlineUsers = onlineUsersRepository.getByAccountId(id,request);
        //Page<OnlineUsersView> onlineUsers = onlineUsersRepository.findAll(OnlineUsersSpecifications.onlineUsersByAccountId(id),request);

        return onlineUsersRepository.getByAccountId(id,request);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
	public void updateUserProfile(ProfileFormView profile) throws Exception {
        try{
    		usersEntityRepository.updateUserProfile(profile.getUserId(), profile.getName(), profile.getSurrname(), profile.getEmail());
        }catch (Exception e){
            LOGGER.error("Cannot update user profile " + e.getMessage());
            throw new Exception(e);
        }
	}

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public void updateUserPassword(int userId, String password) throws Exception {
        try{
            usersEntityRepository.updateUserPassword(userId, bCryptPasswordEncoder.encode(password));
        }catch (Exception e){
            LOGGER.error("Cannot update user password " + e.getMessage());
            throw new Exception(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly=true, rollbackFor=Exception.class)
    public void updateUserAvatar(int userId, String imageUrl) throws Exception {
        try{
            usersEntityRepository.updateUserAvatar(userId, imageUrl);
        }catch (Exception e){
            LOGGER.error("Cannot update user avatar " + e.getMessage());
            throw new Exception(e);
        }
    }
}

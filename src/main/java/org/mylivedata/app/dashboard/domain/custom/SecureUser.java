package org.mylivedata.app.dashboard.domain.custom;

import java.util.Collection;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUser;

/**
 * Created by lubo08 on 1.4.2014.
 */
public class SecureUser extends SocialUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Locale locale;

    private Integer accountId;

    private String accountIdentity;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String imageUrl;

    private boolean credentialsNonExpired;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean enabled = true;

    public SecureUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.firstName = username;
        this.password = password;

    }
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getImageUrl(){
        return this.imageUrl;
    }
    public void setUserId(Long userId){
        this.id = userId;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setCredentialsNonExpired(boolean credentialsNonExpired){
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public void setAccountNonExpired(boolean accountNonExpired){
        this.accountNonExpired = accountNonExpired;
    }
    public void setAccountNonLocked(boolean accountNonLocked){
        this.accountNonLocked = accountNonLocked;
    }
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public Long getId(){
        return this.id;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /*
    public SecureUser(UsersEntity user) {

        if(user != null)
        {
            this.userId = user.getUserId().longValue();
            this.firstName = user.getName();
            this.setEmail(user.getEmail());
            this.setPassword(user.getPassword());
            this.setIsCredentialsNonExpired(user.getIsCredentialsNonExpired());

            if(user.getAssocUserGroupsesByUserId() != null)
                for(AssocUserGroupsEntity age: user.getAssocUserGroupsesByUserId()){
                    for(AssocGroupRoleEntity agre: age.getGroupsByGroupId().getAssocGroupRolesByGroupId()){
                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(agre.getRolesByRoleId().getRoleName());
                        authoritiesIn.add(authority);
                    }
                }
            else {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_VISITOR");
                authoritiesIn.add(authority);
                this.setIsCredentialsNonExpired("true");
            }


        }

    }
    */
    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    */
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getAccountIdentity() {
        return accountIdentity;
    }

    public void setAccountIdentity(String accountIdentity) {
        this.accountIdentity = accountIdentity;
    }
}

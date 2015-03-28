package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by lubo08 on 21.10.2014.
 */
@StaticMetamodel(UsersEntity.class)
public class UsersEntity_ {
    public static volatile SingularAttribute<UsersEntity, Integer> userId;
    public static volatile SingularAttribute<UsersEntity, String> name;
    public static volatile SingularAttribute<UsersEntity, String> surrname;
    public static volatile SingularAttribute<UsersEntity, String> email;
    public static volatile SingularAttribute<UsersEntity, String> password;
    public static volatile SingularAttribute<UsersEntity, Timestamp> registration;
    public static volatile SingularAttribute<UsersEntity, Timestamp> lastLogin;
    public static volatile SingularAttribute<UsersEntity, String> lastIp;
    public static volatile SingularAttribute<UsersEntity, String> identificationHash;
    public static volatile SingularAttribute<UsersEntity, Integer> accountId;
    public static volatile SingularAttribute<UsersEntity, Collection<AccountsEntity>> accountsesByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<AssocUserDomainEntity>> assocUserDomainsByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<AssocUserGroupsEntity>> assocUserGroupsesByUserId;
    public static volatile SingularAttribute<UsersEntity, AccountsEntity> accountsByAccountId;
    public static volatile SingularAttribute<UsersEntity, String> isCredentialsNonExpired;
    public static volatile SingularAttribute<UsersEntity, OnlineUsersEntity> onlineUsersByUserId;
    public static volatile SingularAttribute<UsersEntity, String> imageUrl;
    public static volatile SingularAttribute<UsersEntity, Collection<UserGeoDataEntity>> userGeoDatasByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<UserPagesEntity>> userPagesesByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<UserSessionsEntity>> userSessionsesByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<UsersChatsEntity>> usersChatsesByUserId;
    public static volatile SingularAttribute<UsersEntity, Collection<UsersChatsEntity>> usersChatsesByUserId_0;
}

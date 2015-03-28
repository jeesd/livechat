package org.mylivedata.app.dashboard.domain;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by lubo08 on 21.10.2014.
 */
@StaticMetamodel(UserSessionsEntity.class)
public class UserSessionsEntity_ {
    public static volatile SingularAttribute<UserSessionsEntity,Integer> sessionId;
    public static volatile SingularAttribute<UserSessionsEntity, String> ip;
    public static volatile SingularAttribute<UserSessionsEntity, Timestamp> sessionStart;
    public static volatile SingularAttribute<UserSessionsEntity, Timestamp> sessionEnd;
    public static volatile SingularAttribute<UserSessionsEntity, String> browser;
    public static volatile SingularAttribute<UserSessionsEntity, String> screenResolution;
    public static volatile SingularAttribute<UserSessionsEntity, String> system;
    public static volatile SingularAttribute<UserSessionsEntity, String> countryCode;
    public static volatile SingularAttribute<UserSessionsEntity, String> countryName;
    public static volatile SingularAttribute<UserSessionsEntity, Integer> userId;
    public static volatile SingularAttribute<UserSessionsEntity, Collection<OnlineUsersEntity>> onlineUsersesBySessionId;
    public static volatile SingularAttribute<UserSessionsEntity, Collection<UserGeoDataEntity>> userGeoDatasBySessionId;
    public static volatile SingularAttribute<UserSessionsEntity, Collection<UserPagesEntity>> userPagesesBySessionId;
    public static volatile SingularAttribute<UserSessionsEntity, UsersEntity> usersByUserId;
}

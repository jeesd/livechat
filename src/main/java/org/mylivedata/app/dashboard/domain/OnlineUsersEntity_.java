package org.mylivedata.app.dashboard.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by lubo08 on 21.10.2014.
 */
@StaticMetamodel(OnlineUsersEntity.class)
public class OnlineUsersEntity_ {
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> userId;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> status;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> isOnline;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> departmentId;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> isChatHistory;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> isInChat;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> isOperator;
    public static volatile SingularAttribute<OnlineUsersEntity, UsersEntity> usersByUserId;
    public static volatile SingularAttribute<OnlineUsersEntity, DepartmentsEntity> departmentsByDepartmentId;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> sessionId;
    public static volatile SingularAttribute<OnlineUsersEntity, UserSessionsEntity> userSessionsBySessionId;
    public static volatile SingularAttribute<OnlineUsersEntity, Integer> isHttpOnline;

}

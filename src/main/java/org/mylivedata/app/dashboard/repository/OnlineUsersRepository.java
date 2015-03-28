package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.OnlineUsersEntity;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface OnlineUsersRepository extends JpaRepository<OnlineUsersEntity, Integer>, JpaSpecificationExecutor<OnlineUsersView>, CustomOnlineUsersRepository {

    @Modifying
    @Query("Update OnlineUsersEntity t SET t.isOnline = :online WHERE t.userId=:id")
    public void updateIsOnline(@Param("online") Integer isOnline, @Param("id") Integer userId);

    @Modifying
    @Query("Update OnlineUsersEntity t SET t.isHttpOnline = :online, t.sessionId = :sessionId WHERE t.userId=:id")
    public void updateIsHttpOnline(@Param("online") Integer isOnline, @Param("id") Integer userId, @Param("sessionId") Integer sessionId);

    @Modifying
    @Query("Update OnlineUsersEntity t SET t.departmentId = :departmentId WHERE t.userId=:id")
    public void updateDepartmentId(@Param("departmentId") Integer departmentId, @Param("id") Integer userId);

    @Query("select t from OnlineUsersEntity t WHERE t.userId=:id")
    public OnlineUsersEntity getUserById(@Param("id") Integer id);

    //@Fetch(FetchMode.JOIN)
    @Query(value = "select new  org.mylivedata.app.dashboard.domain.custom.OnlineUsersView(t.status,t.isOnline,t.departmentId," +
            "t.isChatHistory,t.isInChat,t.isOperator,t.isHttpOnline,s.ip,s.sessionStart,s.sessionEnd,s.browser," +
            "s.screenResolution,s.system,s.countryCode,s.countryName) " +
            "from UsersEntity as e LEFT JOIN e.onlineUsersByUserId as t LEFT JOIN t.userSessionsBySessionId as s " +
            "where e.accountId=:id AND e.userId=t.userId AND t.sessionId = s.sessionId")
    /*@Query("select new  com.mylivedata.app.dashboard.view.OnlineUsersView(t.status,t.isOnline,t.departmentId," +
            "t.isChatHistory,t.isInChat,t.isOperator,t.isHttpOnline,s.ip,s.sessionStart,s.sessionEnd,s.browser," +
            "s.screenResolution,s.system,s.countryCode,s.countryName) " +
            "from UsersEntity e,OnlineUsersEntity t, UserSessionsEntity s " +
            "where e.accountId=:id and e.userId = t.userId and s.sessionId = t.sessionId ")*/
    public Page<OnlineUsersView> getByAccountId(@Param("id") Integer id, Pageable pageable);

}

package org.mylivedata.app.dashboard.repository;

import java.sql.Timestamp;

import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface UserSessionsRepository extends JpaRepository<UserSessionsEntity, Integer> {

    @Modifying
    @Query("Update UserSessionsEntity t SET t.sessionEnd = :time WHERE t.sessionId=:id")
    void updateSessionEndTimestamp(@Param("time") Timestamp sessionEnd,@Param("id") Integer sessionId);

}

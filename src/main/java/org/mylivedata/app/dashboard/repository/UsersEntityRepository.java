package org.mylivedata.app.dashboard.repository;

import java.util.List;

import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface UsersEntityRepository extends JpaRepository<UsersEntity, Integer> {

    List<UsersEntity> findByEmail(String email);
    List<UsersEntity> findByIdentificationHash(String hash);
    List<UsersEntity> findByNameAndAccountId(String name, int accountId);
    UsersEntity findByUserId(int id);
    
    @Modifying
    @Query("Update UsersEntity t SET t.name=:name, t.surrname=:surrname, t.email=:email WHERE t.userId=:userId")
    public void updateUserProfile(@Param("userId") int userId, @Param("name") String name, @Param("surrname") String surrname, 
    								@Param("email") String email);

    @Modifying
    @Query("Update UsersEntity t SET t.password=:password WHERE t.userId=:userId")
    public void updateUserPassword(@Param("userId") int userId, @Param("password") String password);

    @Modifying
    @Query("Update UsersEntity t SET t.imageUrl=:imageUrl WHERE t.userId=:userId")
    public void updateUserAvatar(@Param("userId") int userId, @Param("imageUrl") String imageUrl);
}

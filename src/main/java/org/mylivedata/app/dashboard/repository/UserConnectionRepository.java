package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Long> {

}

package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.Ip2LocationDb11Entity;
import org.mylivedata.app.dashboard.domain.Ip2LocationDb11EntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface Ip2LocationDb11Repository extends JpaRepository<Ip2LocationDb11Entity, Ip2LocationDb11EntityPK> {

    @Query("select t from Ip2LocationDb11Entity t WHERE t.ipFrom <= :ip AND t.ipTo >= :ip")
    Ip2LocationDb11Entity findGeoLocation(@Param("ip") Long ip);
}

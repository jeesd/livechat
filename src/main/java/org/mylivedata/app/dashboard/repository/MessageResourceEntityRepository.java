package org.mylivedata.app.dashboard.repository;

import org.mylivedata.app.dashboard.domain.MessageResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lubo08 on 20.3.2014.
 */
//@Repository
public interface MessageResourceEntityRepository extends JpaRepository<MessageResourceEntity, Integer> {

    //List<MessageResourceEntity> findByLangIsoCodeAndCountryIsoCode(String lanIsoCode, String countryIsoCode);

}

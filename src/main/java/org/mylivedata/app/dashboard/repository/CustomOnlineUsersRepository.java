package org.mylivedata.app.dashboard.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by lubo08 on 21.10.2014.
 */
public interface CustomOnlineUsersRepository {

    public Page<OnlineUsersView> getByAccountId(Integer id, PageRequest pageRequest);

    public Long getRowCount(CriteriaQuery<?> criteriaQuery,CriteriaBuilder criteriaBuilder,Root<?> root);
}

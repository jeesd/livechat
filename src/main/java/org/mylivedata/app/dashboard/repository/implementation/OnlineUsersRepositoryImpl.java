package org.mylivedata.app.dashboard.repository.implementation;

import org.hibernate.jpa.criteria.path.SingularAttributePath;
import org.mylivedata.app.dashboard.domain.OnlineUsersEntity;
import org.mylivedata.app.dashboard.domain.OnlineUsersEntity_;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity_;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity_;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.mylivedata.app.dashboard.repository.CustomOnlineUsersRepository;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by lubo08 on 21.10.2014.
 */
public class OnlineUsersRepositoryImpl implements CustomOnlineUsersRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<OnlineUsersView> getByAccountId(Integer id, PageRequest pageRequest) {

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OnlineUsersView> cq = criteriaBuilder.createQuery(OnlineUsersView.class);

        Root<UsersEntity> user = cq.from(UsersEntity.class);

        Join<UsersEntity, OnlineUsersEntity> userToOnlineUser =
                user.join(UsersEntity_.onlineUsersByUserId, JoinType.INNER);

        Join<OnlineUsersEntity, UserSessionsEntity> onlineUsersToUserSession =
                userToOnlineUser.join(OnlineUsersEntity_.userSessionsBySessionId, JoinType.INNER);

        cq.select(
                criteriaBuilder.construct(
                        OnlineUsersView.class,
                        userToOnlineUser.get(OnlineUsersEntity_.status),
                        userToOnlineUser.get(OnlineUsersEntity_.isOnline),
                        userToOnlineUser.get(OnlineUsersEntity_.departmentId),
                        userToOnlineUser.get(OnlineUsersEntity_.isChatHistory),
                        userToOnlineUser.get(OnlineUsersEntity_.isInChat),
                        userToOnlineUser.get(OnlineUsersEntity_.isOperator),
                        userToOnlineUser.get(OnlineUsersEntity_.isHttpOnline),
                        onlineUsersToUserSession.get(UserSessionsEntity_.ip),
                        onlineUsersToUserSession.get(UserSessionsEntity_.sessionStart),
                        onlineUsersToUserSession.get(UserSessionsEntity_.sessionEnd),
                        onlineUsersToUserSession.get(UserSessionsEntity_.browser),
                        onlineUsersToUserSession.get(UserSessionsEntity_.screenResolution),
                        onlineUsersToUserSession.get(UserSessionsEntity_.system),
                        onlineUsersToUserSession.get(UserSessionsEntity_.countryCode),
                        onlineUsersToUserSession.get(UserSessionsEntity_.countryName)
                )
        );

        Predicate predicate = criteriaBuilder.equal(user.get(UsersEntity_.accountId), id);

        cq.where(predicate)
                .orderBy(getOrders(pageRequest.getSort(), criteriaBuilder, cq));

        PageImpl page = new PageImpl(
                this.entityManager.createQuery(cq)
                        .setFirstResult(pageRequest.getPageNumber()*pageRequest.getPageSize())
                        .setMaxResults(pageRequest.getPageSize())
                        .getResultList(),
                pageRequest,
                getRowCount(cq,criteriaBuilder,user)
        );

        return page;
    }

    public Long getRowCount(CriteriaQuery<?> criteriaQuery,CriteriaBuilder criteriaBuilder,Root<?> root){
        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        Root<?> entityRoot = countCriteria.from(root.getJavaType());
        entityRoot.alias(root.getAlias());
        doJoins(root.getJoins(),entityRoot);
        countCriteria.select(criteriaBuilder.count(entityRoot));
        countCriteria.where(criteriaQuery.getRestriction());
        return this.entityManager.createQuery(countCriteria).getSingleResult();
    }

    private void doJoins(Set<? extends Join<?, ?>> joins,Root<?> root_){
        for(Join<?,?> join: joins){
            Join<?,?> joined = root_.join(join.getAttribute().getName(),join.getJoinType());
            doJoins(join.getJoins(), joined);
        }
    }

    private void doJoins(Set<? extends Join<?, ?>> joins,Join<?,?> root_){
        for(Join<?,?> join: joins){
            Join<?,?> joined = root_.join(join.getAttribute().getName(),join.getJoinType());
            doJoins(join.getJoins(),joined);
        }
    }

    private List<Order> getOrders(Sort sort, CriteriaBuilder criteriaBuilder, CriteriaQuery criteriaQuery){
        List<Order> orders = new ArrayList<>();
        for(Sort.Order order: sort){
            for(Object sel: criteriaQuery.getSelection().getCompoundSelectionItems()){
                SingularAttributePath attrSel = (SingularAttributePath)sel;
                if(attrSel.getAttribute().getName().equals(order.getProperty())){
                    orders.add(order.isAscending()?criteriaBuilder.asc(attrSel):criteriaBuilder.desc(attrSel));
                    break;
                }
            }
        }
        return orders;
    }

}

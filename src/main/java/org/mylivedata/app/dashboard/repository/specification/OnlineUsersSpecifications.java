package org.mylivedata.app.dashboard.repository.specification;

import org.mylivedata.app.dashboard.domain.OnlineUsersEntity;
import org.mylivedata.app.dashboard.domain.OnlineUsersEntity_;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity;
import org.mylivedata.app.dashboard.domain.UserSessionsEntity_;
import org.mylivedata.app.dashboard.domain.UsersEntity;
import org.mylivedata.app.dashboard.domain.UsersEntity_;
import org.mylivedata.app.dashboard.domain.custom.OnlineUsersView;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by lubo08 on 21.10.2014.
 */
public class OnlineUsersSpecifications {

    public static Specification<OnlineUsersView> onlineUsersByAccountId(final Integer id) {
        return new Specification<OnlineUsersView>() {
            @Override
            public Predicate toPredicate(Root<OnlineUsersView> onlineUsersEntityRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                CriteriaQuery<OnlineUsersView> cq = (CriteriaQuery<OnlineUsersView>)criteriaQuery;
                Root<OnlineUsersEntity> onlineUsers = cq.from(OnlineUsersEntity.class);
                Root<UsersEntity> user = cq.from(UsersEntity.class);
                Root<UserSessionsEntity> userSession = cq.from(UserSessionsEntity.class);
                Join<OnlineUsersEntity, UsersEntity> onlineUserToUsers = onlineUsers.join(OnlineUsersEntity_.usersByUserId, JoinType.INNER);
                onlineUsers.join(OnlineUsersEntity_.userSessionsBySessionId, JoinType.INNER);


                //Root<Project> c = q.from(Project.class);

                Selection<OnlineUsersView> aa =  cq.select(
                    criteriaBuilder.construct(
                        OnlineUsersView.class,
                            onlineUsers.get(OnlineUsersEntity_.status),
                            onlineUsers.get(OnlineUsersEntity_.isOnline),
                            onlineUsers.get(OnlineUsersEntity_.departmentId),
                            onlineUsers.get(OnlineUsersEntity_.isChatHistory),
                            onlineUsers.get(OnlineUsersEntity_.isInChat),
                            onlineUsers.get(OnlineUsersEntity_.isOperator),
                            onlineUsers.get(OnlineUsersEntity_.isHttpOnline),
                            userSession.get(UserSessionsEntity_.ip),
                            userSession.get(UserSessionsEntity_.sessionStart),
                            userSession.get(UserSessionsEntity_.sessionEnd),
                            userSession.get(UserSessionsEntity_.browser),
                            userSession.get(UserSessionsEntity_.screenResolution),
                            userSession.get(UserSessionsEntity_.system),
                            userSession.get(UserSessionsEntity_.countryCode),
                            userSession.get(UserSessionsEntity_.countryName)
                    )
                ).getSelection();



                cq.where(criteriaBuilder.equal(onlineUserToUsers.get(UsersEntity_.accountId),id));
                //criteriaQuery.select(onlineUsers);
                //predicate = criteriaQuery.from(OnlineUsersEntity.class).join(OnlineUsersEntity_.usersByUserId, JoinType.INNER)
                //criteriaQuery.where(criteriaBuilder.equal(OnlineUsersEntity_.userId,id));
                //predicate = criteriaBuilder.and(predicate, cq.getRestriction());
                predicate = criteriaBuilder.and(predicate,cq.getRestriction());


                return predicate;
            }
        };
    }


}

package io.swit.ctsimapserver.data.query.com;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swit.ctsimapserver.model.entity.com.QUserEntity;
import io.swit.ctsimapserver.model.entity.com.UserEntity;
import io.swit.ctsimapserver.model.vo.com.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UserQuery {
    private JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public UserVo getUserByUsername(String username) throws Exception {
        UserVo user = new UserVo();
        QUserEntity cu = new QUserEntity("cu");
        JPAQuery<UserEntity> query = queryFactory.selectFrom(cu);
        user = query.select(
                Projections.bean(UserVo.class,
                        cu.userIdx,
                        cu.username,
                        cu.password,
                        cu.nickName,
                        cu.phoneNo,
                        cu.email,
                        cu.createdAt,
                        cu.createdBy,
                        cu.updatedAt,
                        cu.updatedBy,
                        cu.roles)
        ).where(cu.username.eq(username)).fetchFirst();
        return user;
    }
}

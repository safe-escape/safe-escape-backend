package team.safe.escape.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.user.entity.Member;
import team.safe.escape.user.enumeration.MemberRole;
import team.safe.escape.user.repository.MemberRepositoryCustom;

import static team.safe.escape.user.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Member findUserByEmail(String email) {
        return queryFactory
                .selectFrom(member)
                .where(member.email.eq(email).and(member.role.eq(MemberRole.USER)))
                .fetchFirst();
    }

    @Override
    public Member findAdminByEmail(String email) {
        return queryFactory
                .selectFrom(member)
                .where(member.email.eq(email).and(member.role.eq(MemberRole.ADMIN)))
                .fetchFirst();
    }

    @Override
    public Member findUserById(Long id) {
        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id).and(member.role.eq(MemberRole.USER)))
                .fetchFirst();
    }

    @Override
    public Member findAdminById(Long id) {
        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(id).and(member.role.eq(MemberRole.ADMIN)))
                .fetchFirst();
    }

}

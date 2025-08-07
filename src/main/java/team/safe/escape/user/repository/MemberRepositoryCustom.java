package team.safe.escape.user.repository;

import team.safe.escape.user.entity.Member;

public interface MemberRepositoryCustom {
    Member findUserByEmail(String email);

    Member findAdminByEmail(String email);

    Member findUserById(Long id);

    Member findAdminById(Long id);
}

package team.safe.escape.user.repository;

import team.safe.escape.user.entity.User;

public interface UserRepositoryCustom {
    User findUserByEmail(String email);
    User findAdminByEmail(String email);
}

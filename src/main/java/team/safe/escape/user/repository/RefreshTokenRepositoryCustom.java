package team.safe.escape.user.repository;

import team.safe.escape.user.entity.RefreshToken;

public interface RefreshTokenRepositoryCustom {
    void deleteByUserId(Long userId);
    RefreshToken findByToken(String token);
}

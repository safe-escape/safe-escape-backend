package team.safe.escape.user.repository;

import team.safe.escape.user.entity.RefreshToken;

public interface RefreshTokenRepositoryCustom {
    void deleteByMemberId(Long memberId);

    RefreshToken findByToken(String token);
}

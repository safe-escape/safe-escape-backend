package team.safe.escape.user.repository;

public interface RefreshTokenRepositoryCustom {
    void deleteByUserId(Long userId);
}

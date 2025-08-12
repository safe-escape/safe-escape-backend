package team.safe.escape.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.entity.Member;
import team.safe.escape.user.entity.RefreshToken;
import team.safe.escape.user.repository.MemberRepository;
import team.safe.escape.user.repository.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    @Transactional
    public Member getAuthenticationUser(String email) {
        return Optional.ofNullable(memberRepository.findByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.USER_NOT_FOUND, email));
    }

    @Transactional
    public void refreshToken(Long memberId, String refreshToken, LocalDateTime expiresAt) {
        refreshTokenRepository.deleteByMemberId(memberId);
        refreshTokenRepository.save(RefreshToken.builder()
                .memberId(memberId)
                .token(refreshToken)
                .expiresAt(expiresAt)
                .build());
    }

    public void setBlackList(Date expiration, String token) {
        long now = System.currentTimeMillis();
        long expireInMs = expiration.getTime() - now;
        if (expireInMs > 0) {
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", expireInMs, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }


}

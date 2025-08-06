package team.safe.escape.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import team.safe.escape.user.entity.RefreshToken;
import team.safe.escape.user.entity.User;
import team.safe.escape.user.repository.RefreshTokenRepository;
import team.safe.escape.user.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ROLE = "role";
    private static final String TYPE = "type";
    private static final String BLACKLIST_PREFIX = "blacklist:";

    private Key secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessTokenByAdmin(String name) {
        return createAccessToken(name, Role.ADMIN);
    }

    public String createAccessTokenByUser(String name) {
        return createAccessToken(name, Role.USER);
    }

    private String createAccessToken(String name, Role role) {
        return createToken(name, jwtProperties.getAccessTokenValidityInMs(), TokenType.ACCESS, role);
    }

    public String createRefreshTokenByAdmin(String name, Long userId) {
        return createRefreshToken(name, userId, Role.ADMIN);
    }

    public String createRefreshTokenByUser(String name, Long userId) {
        return createRefreshToken(name, userId, Role.USER);
    }

    public String createRefreshToken(String name, Long userId, Role role) {
        String refreshToken = createToken(name, jwtProperties.getRefreshTokenValidityInMs(), TokenType.REFRESH, role);
        refreshTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(userId)
                .token(refreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(jwtProperties.getRefreshTokenValidityInSeconds()))
                .build());
        return refreshToken;
    }

    public boolean validateToken(String token) {
        if (isBlacklisted(token)) {
            return false;
        }

        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token).getBody();
            return body.get(TYPE).equals(TokenType.ACCESS.name()) && !body.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        User user = Optional.ofNullable(userRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTH_HEADER);
        if (bearer != null && bearer.startsWith(BEARER_PREFIX)) {
            return bearer.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public void blacklistToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        long now = System.currentTimeMillis();
        long expireInMs = expiration.getTime() - now;
        if (expireInMs > 0) {
            redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", expireInMs, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }

    private String createToken(String name, long validityInMs, TokenType tokenType, Role role) {
        Claims claims = Jwts.claims().setSubject(name);
        claims.put(TYPE, tokenType);
        claims.put(ROLE, role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
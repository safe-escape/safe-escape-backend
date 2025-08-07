package team.safe.escape.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.config.jwt.JwtTokenProvider;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.response.LoginResponse;
import team.safe.escape.user.dto.response.TokenResponse;
import team.safe.escape.user.dto.response.UserResponseDto;
import team.safe.escape.user.entity.RefreshToken;
import team.safe.escape.user.entity.User;
import team.safe.escape.user.enumeration.UserRole;
import team.safe.escape.user.repository.RefreshTokenRepository;
import team.safe.escape.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String resolveToken(HttpServletRequest request) {
        return jwtTokenProvider.resolveToken(request);
    }

    public void logout(String token) {
        blacklistAccessToken(token);
    }

    public TokenResponse register(String email, String name, String password) {
        if (userRepository.existsUserByEmail(email)) {
            throw new EscapeException(ErrorCode.EMAIL_ALREADY_REGISTERED, email);
        }

        User user = userRepository.save(User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(UserRole.USER)
                .build());

        return createToken(user);
    }

    public LoginResponse loginByUser(String email, String password) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!user.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        return LoginResponse.of(createToken(user), UserResponseDto.ofUser(user));
    }

    public LoginResponse loginByAdmin(String email, String password) {
        User user = Optional.ofNullable(userRepository.findAdminByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!user.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        return LoginResponse.of(createToken(user), UserResponseDto.ofUser(user));
    }

    public TokenResponse refreshToken(String refreshToken, String accessToken, Long userId, UserRole role) {
        User user = validUserByIdAndReturn(userId, role);
        RefreshToken refresh = validateUserByIdAndReturn(refreshToken, userId);
        blacklistAccessToken(accessToken);
        refresh.expired();
        return createToken(user);
    }

    private User validUserByIdAndReturn(Long userId, UserRole role) {
        User user = null;
        if (role == UserRole.USER) {
            user = userRepository.findUserById(userId);
        } else if (role == UserRole.ADMIN) {
            user = userRepository.findAdminById(userId);
        }
        if (user == null) {
            throw new EscapeException(ErrorCode.USER_NOT_FOUND, userId);
        }
        return user;
    }

    private RefreshToken validateUserByIdAndReturn(String refreshToken, Long userId) {
        RefreshToken refresh = Optional.ofNullable(refreshTokenRepository.findByToken(refreshToken))
                .orElseThrow(() -> new EscapeException(ErrorCode.REFRESH_TOKEN_NOT_FOUND, refreshToken));

        if (!refresh.getUserId().equals(userId)) {
            throw new EscapeException(ErrorCode.TOKEN_USER_MISMATCH);
        }

        if (refresh.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EscapeException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        return refresh;
    }

    private void blacklistAccessToken(String accessToken) {
        if ((accessToken != null) && jwtTokenProvider.validateToken(accessToken)) {
            jwtTokenProvider.blacklistToken(accessToken);
        }
        // TODO 로깅 처리
    }

    private TokenResponse createToken(User user) {
        String newAccessToken = jwtTokenProvider.createAccessTokenByUser(user.getEmail());
        String newRefreshToken = jwtTokenProvider.createRefreshTokenByUser(user.getEmail(), user.getId());
        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

}

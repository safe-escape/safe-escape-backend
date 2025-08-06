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
import team.safe.escape.user.entity.User;
import team.safe.escape.user.enumeration.UserRole;
import team.safe.escape.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String resolveToken(HttpServletRequest request) {
        return jwtTokenProvider.resolveToken(request);
    }

    public void logout(String token) {
        if (token != null && jwtTokenProvider.validateToken(token)) {
            jwtTokenProvider.blacklistToken(token);
        }
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

        String accessToken = jwtTokenProvider.createAccessTokenByUser(email);
        String refreshToken = jwtTokenProvider.createRefreshTokenByUser(email, user.getId());
        return TokenResponse.of(accessToken, refreshToken);
    }

    public LoginResponse loginByUser(String email, String password) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!user.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtTokenProvider.createAccessTokenByUser(email);
        String refreshToken = jwtTokenProvider.createRefreshTokenByUser(email, user.getId());
        return LoginResponse.of(accessToken, refreshToken, UserResponseDto.ofUser(user));
    }

    public LoginResponse loginByAdmin(String email, String password) {
        User user = Optional.ofNullable(userRepository.findAdminByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!user.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtTokenProvider.createAccessTokenByAdmin(email);
        String refreshToken = jwtTokenProvider.createRefreshTokenByAdmin(email, user.getId());
        return LoginResponse.of(accessToken, refreshToken, UserResponseDto.ofUser(user));
    }
}

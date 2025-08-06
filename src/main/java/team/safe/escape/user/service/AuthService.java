package team.safe.escape.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.config.jwt.JwtTokenProvider;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.response.RegisterResponse;
import team.safe.escape.user.entity.User;
import team.safe.escape.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public RegisterResponse register(String email, String name, String password) {
        if (userRepository.existsUserByEmail(email)) {
            throw new EscapeException(ErrorCode.EMAIL_ALREADY_REGISTERED, email);
        }

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build());

        String accessToken = jwtTokenProvider.createAccessTokenByUser(name);
        String refreshToken = jwtTokenProvider.createRefreshTokenByUser(name);
        return RegisterResponse.of(accessToken, refreshToken);
    }

}

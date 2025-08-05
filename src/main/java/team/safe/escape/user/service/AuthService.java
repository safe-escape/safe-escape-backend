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

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public RegisterResponse register(String email, String name, String password) {
        Optional.ofNullable(userRepository.findByEmail(email))
                .ifPresent(user -> {
                    throw new EscapeException(ErrorCode.EMAIL_ALREADY_REGISTERED, email);
                });

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createAccessToken(name);
        String refreshToken = jwtTokenProvider.createRefreshToken(name);
        return RegisterResponse.of(accessToken, refreshToken);
    }

}

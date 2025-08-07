package team.safe.escape.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.response.UserResponseDto;
import team.safe.escape.user.entity.User;
import team.safe.escape.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getAdminUser(String email) {
        User user = Optional.ofNullable(userRepository.findAdminByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.USER_NOT_FOUND, email));
        return UserResponseDto.ofUser(user);
    }

    public UserResponseDto getUser(String email) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.USER_NOT_FOUND, email));
        return UserResponseDto.ofUser(user);
    }

}

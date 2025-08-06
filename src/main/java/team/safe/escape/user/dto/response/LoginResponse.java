package team.safe.escape.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.user.dto.UserDto;

@Getter
@NoArgsConstructor
public class LoginResponse extends TokenResponse {
    private UserDto user;

    public static LoginResponse of(String accessToken, String refreshToken, UserDto user) {
        LoginResponse instance = new LoginResponse();
        instance.accessToken = accessToken;
        instance.refreshToken = refreshToken;
        instance.user = user;
        return instance;
    }

}

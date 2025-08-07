package team.safe.escape.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse extends TokenResponse {
    private UserResponseDto user;

    public static LoginResponse of(TokenResponse tokenResponse, UserResponseDto user) {
        LoginResponse instance = new LoginResponse();
        instance.accessToken = tokenResponse.getAccessToken();
        instance.refreshToken = tokenResponse.getRefreshToken();
        instance.user = user;
        return instance;
    }

    public static LoginResponse of(String accessToken, String refreshToken, UserResponseDto user) {
        LoginResponse instance = new LoginResponse();
        instance.accessToken = accessToken;
        instance.refreshToken = refreshToken;
        instance.user = user;
        return instance;
    }

}

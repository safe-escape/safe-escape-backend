package team.safe.escape.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse extends TokenResponse {
    private MemberResponseDto user;

    public static LoginResponse of(TokenResponse tokenResponse, MemberResponseDto user) {
        LoginResponse instance = new LoginResponse();
        instance.accessToken = tokenResponse.getAccessToken();
        instance.refreshToken = tokenResponse.getRefreshToken();
        instance.user = user;
        return instance;
    }

}

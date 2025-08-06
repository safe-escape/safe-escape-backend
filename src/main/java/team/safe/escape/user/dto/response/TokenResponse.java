package team.safe.escape.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    protected String accessToken;
    protected String refreshToken;

    public static TokenResponse of(String accessToken, String refreshToken) {
        TokenResponse instance = new TokenResponse();
        instance.accessToken = accessToken;
        instance.refreshToken = refreshToken;
        return instance;
    }

}

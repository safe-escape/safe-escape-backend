package team.safe.escape.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class RegisterResponse {
    private String accessToken;
    private String refreshToken;
}

package team.safe.escape.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshRequest {
    @NotNull(message = "refreshToken은 필수 항목입니다.")
    private String refreshToken;
}

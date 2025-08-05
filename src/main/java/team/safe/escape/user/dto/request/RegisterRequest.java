package team.safe.escape.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotNull(message = "이름은 필수 항목입니다.")
    private String name;

    @NotNull(message = "비밀번호는 필수 항목입니다.")
    private String password;
}

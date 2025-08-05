package team.safe.escape.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
}

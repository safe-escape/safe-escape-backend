package team.safe.escape.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.user.entity.User;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;

    public static UserResponseDto ofUser(User user) {
        UserResponseDto instance = new UserResponseDto();
        instance.id = user.getId();
        instance.name = user.getName();
        instance.email = user.getEmail();
        return instance;
    }
}

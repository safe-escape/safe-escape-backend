package team.safe.escape.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserResponseDto {
    private Long id;
    private String name;
}

package team.safe.escape.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.user.entity.Member;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;

    public static MemberResponseDto ofUser(Member member) {
        MemberResponseDto instance = new MemberResponseDto();
        instance.id = member.getId();
        instance.name = member.getName();
        instance.email = member.getEmail();
        return instance;
    }
}

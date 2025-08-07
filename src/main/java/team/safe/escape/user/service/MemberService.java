package team.safe.escape.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.response.MemberResponseDto;
import team.safe.escape.user.entity.Member;
import team.safe.escape.user.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getAdminUser(String email) {
        Member member = Optional.ofNullable(memberRepository.findAdminByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.USER_NOT_FOUND, email));
        return MemberResponseDto.ofUser(member);
    }

    public MemberResponseDto getUser(String email) {
        Member member = Optional.ofNullable(memberRepository.findUserByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.USER_NOT_FOUND, email));
        return MemberResponseDto.ofUser(member);
    }

}

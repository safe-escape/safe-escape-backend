package team.safe.escape.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.config.jwt.JwtTokenProvider;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.user.dto.response.LoginResponse;
import team.safe.escape.user.dto.response.MemberResponseDto;
import team.safe.escape.user.dto.response.TokenResponse;
import team.safe.escape.user.entity.Member;
import team.safe.escape.user.entity.RefreshToken;
import team.safe.escape.user.enumeration.MemberRole;
import team.safe.escape.user.repository.MemberRepository;
import team.safe.escape.user.repository.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String resolveToken(HttpServletRequest request) {
        return jwtTokenProvider.resolveToken(request);
    }

    public void logout(String token) {
        blacklistAccessToken(token);
    }

    public TokenResponse register(String email, String name, String password, MemberRole role) {
        if (memberRepository.existsUserByEmail(email)) {
            throw new EscapeException(ErrorCode.EMAIL_ALREADY_REGISTERED, email);
        }

        Member member = memberRepository.save(Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build());

        return createToken(member);
    }

    public LoginResponse loginByUser(String email, String password) {
        Member member = Optional.ofNullable(memberRepository.findUserByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!member.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        return LoginResponse.of(createToken(member), MemberResponseDto.ofUser(member));
    }

    public LoginResponse loginByAdmin(String email, String password) {
        Member member = Optional.ofNullable(memberRepository.findAdminByEmail(email))
                .orElseThrow(() -> new EscapeException(ErrorCode.EMAIL_DOES_NOT_EXIST, email));

        if (!member.getPassword().equals(password)) {
            throw new EscapeException(ErrorCode.PASSWORD_MISMATCH);
        }

        return LoginResponse.of(createToken(member), MemberResponseDto.ofUser(member));
    }

    public TokenResponse refreshToken(String refreshToken, String accessToken, Long memberId, MemberRole role) {
        Member member = validUserByIdAndReturn(memberId, role);
        RefreshToken refresh = validateUserByIdAndReturn(refreshToken, memberId);
        blacklistAccessToken(accessToken);
        refresh.expired();
        return createToken(member);
    }

    private Member validUserByIdAndReturn(Long memberId, MemberRole role) {
        Member member = null;
        if (role == MemberRole.USER) {
            member = memberRepository.findUserById(memberId);
        } else if (role == MemberRole.ADMIN) {
            member = memberRepository.findAdminById(memberId);
        }
        if (member == null) {
            throw new EscapeException(ErrorCode.USER_NOT_FOUND, memberId);
        }
        return member;
    }

    private RefreshToken validateUserByIdAndReturn(String refreshToken, Long memberId) {
        RefreshToken refresh = Optional.ofNullable(refreshTokenRepository.findByToken(refreshToken))
                .orElseThrow(() -> new EscapeException(ErrorCode.REFRESH_TOKEN_NOT_FOUND, refreshToken));

        if (!refresh.getMemberId().equals(memberId)) {
            throw new EscapeException(ErrorCode.TOKEN_USER_MISMATCH);
        }

        if (refresh.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EscapeException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        return refresh;
    }

    private void blacklistAccessToken(String accessToken) {
        if ((accessToken != null) && jwtTokenProvider.validateToken(accessToken)) {
            jwtTokenProvider.blacklistToken(accessToken);
        }
        // TODO 로깅 처리
    }

    private TokenResponse createToken(Member member) {
        final String email = member.getEmail();
        final Long memberId = member.getId();
        String newAccessToken;
        String newRefreshToken;

        if (member.getRole() == MemberRole.USER) {
            newAccessToken = jwtTokenProvider.createAccessTokenByUser(email);
            newRefreshToken = jwtTokenProvider.createRefreshTokenByUser(email, memberId);
        } else {
            newAccessToken = jwtTokenProvider.createAccessTokenByAdmin(email);
            newRefreshToken = jwtTokenProvider.createRefreshTokenByAdmin(email, memberId);
        }

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

}

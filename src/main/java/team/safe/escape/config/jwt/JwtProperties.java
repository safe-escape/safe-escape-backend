package team.safe.escape.config.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class JwtProperties {

    @Getter
    @Value("${JWT_SECRET}")
    public String secret;

    @Value("${JWT_ACCESS_VALIDITY_SECONDS}")
    long accessTokenValidityInSeconds;

    @Value("${JWT_REFRESH_VALIDITY_SECONDS}")
    long refreshTokenValidityInSeconds;

    public long getAccessTokenValidityInMs() {
        return convertSecondsToMs(this.accessTokenValidityInSeconds);
    }

    public long getRefreshTokenValidityInMs() {
        return convertSecondsToMs(this.refreshTokenValidityInSeconds);
    }

    private long convertSecondsToMs(long seconds) {
        return seconds * 1000L;
    }
}

package team.safe.escape.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoUtilsTest {

    @Test
    @DisplayName("같은 좌표는 거리가 0이다.")
    void same_location_test() {
        // given
        double lat = 37.5665;
        double lon = 126.9780;

        // when
        double distance = GeoUtils.distanceInMeters(lat, lon, lat, lon);

        // then
        assertEquals(0, distance, "같은 좌표는 거리가 0이어야 합니다.");
    }

    @Test
    void know_location_test() {
        // 서울 광화문(37.5665, 126.9780) <-> 서울역(37.5547, 126.9706) : 대략 1.5km 거리
        // given
        double lat1 = 37.5665;
        double lon1 = 126.9780;
        double lat2 = 37.5547;
        double lon2 = 126.9706;
        double distance = GeoUtils.distanceInMeters(lat1, lon1, lat2, lon2);

        // when
        boolean isValidDistance = distance > 1450 && distance < 1550;

        // then
        assertTrue(isValidDistance, "거리 계산 결과가 예상 범위 내에 있어야 합니다.");
    }
}
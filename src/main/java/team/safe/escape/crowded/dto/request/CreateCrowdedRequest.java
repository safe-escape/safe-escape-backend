package team.safe.escape.crowded.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import team.safe.escape.common.util.GeoUtils;
import team.safe.escape.crowded.dto.LocationDto;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateCrowdedRequest {
    private static final int MINIMUM_LOCATIONS = 2;

    @NotNull(message = "혼잡지역은 필수 항목입니다.")
    private List<CreateLocationRequest> crowdedLocationList;

    @NotNull(message = "비상구는 필수 항목입니다.")
    private List<CreateLocationRequest> exitLocationList;

    public void valid() {
        validateMinimumCount(exitLocationList, ErrorCode.EXIT_MINIMUM_REQUIRED);
        validateMinimumCount(crowdedLocationList, ErrorCode.CROWDED_MINIMUM_REQUIRED);
        validateLocations(crowdedLocationList);
        validateLocations(exitLocationList);
    }

    public List<LocationDto> toCrowdedLocationDtoList() {
        return toLocationDtoList(this.crowdedLocationList);
    }

    public List<LocationDto> toExitLocationDtoList() {
        return toLocationDtoList(this.exitLocationList);
    }

    private List<LocationDto> toLocationDtoList(List<CreateLocationRequest> locationRequestList) {
        return locationRequestList.stream()
                .map(loc -> LocationDto.ofCreate(loc.getLatitude(), loc.getLongitude()))
                .toList();
    }

    private void validateMinimumCount(List<?> list, ErrorCode errorCode) {
        if (list == null || list.size() < MINIMUM_LOCATIONS) {
            throw new EscapeException(errorCode);
        }
    }

    private void validateLocations(List<CreateLocationRequest> locationList) {
        boolean isInvalid = locationList.stream()
                .anyMatch(loc -> !GeoUtils.isValidLocation(loc.getLatitude(), loc.getLongitude()));
        if (isInvalid) {
            throw new EscapeException(ErrorCode.INVALID_LOCATION);
        }
    }
}

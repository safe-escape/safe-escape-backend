package team.safe.escape.crowded.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.exit.dto.EmergencyExitDto;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrowdedAreaDto {
    private long id;
    private List<LocationDto> locationList;
    private List<EmergencyExitDto> exitList;
}

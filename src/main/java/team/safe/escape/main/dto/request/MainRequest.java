package team.safe.escape.main.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.crowded.dto.request.LocationRequest;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainRequest {
    private List<LocationRequest> locationList;
}

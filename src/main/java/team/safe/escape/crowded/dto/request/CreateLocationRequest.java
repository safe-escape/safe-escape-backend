package team.safe.escape.crowded.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateLocationRequest {
    @NotNull(message = "latitude 는 필수 항목입니다.")
    private double latitude;
    @NotNull(message = "longitude 는 필수 항목입니다.")
    private double longitude;
}

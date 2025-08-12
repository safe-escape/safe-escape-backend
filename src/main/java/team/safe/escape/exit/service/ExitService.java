package team.safe.escape.exit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.common.util.GeoUtils;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.exit.entity.EmergencyExit;
import team.safe.escape.exit.repository.EmergencyExitRepository;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.entity.Shelter;
import team.safe.escape.shelter.repository.ShelterRepository;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

@Service
@RequiredArgsConstructor
public class ExitService {

    private final EmergencyExitRepository emergencyExitRepository;
    private final ShelterRepository shelterRepository;

    public ShelterResponse getNearByShelter(Long exitId) {
        EmergencyExit emergencyExit = getEmergencyExitById(exitId);
        Shelter nearByShelter = findNearShelterByExit(emergencyExit);
        return ShelterResponse.ofShelter(nearByShelter);
    }

    private Shelter findNearShelterByExit(EmergencyExit emergencyExit) {
        return shelterRepository.findAll()
                .stream()
                .min(Comparator.comparingDouble(getDistanceForExit(emergencyExit)))
                .orElseThrow(() -> new EscapeException(ErrorCode.NEAR_EXIT_NOT_FOUND));
    }

    private EmergencyExit getEmergencyExitById(Long exitId) {
        return emergencyExitRepository
                .findById(exitId)
                .orElseThrow(() -> new EscapeException(ErrorCode.EXIT_NOT_FOUND, exitId));
    }

    private ToDoubleFunction<Shelter> getDistanceForExit(EmergencyExit emergencyExit) {
        return shelter -> GeoUtils.distanceInMeters(
                emergencyExit.getLatitude(),
                emergencyExit.getLongitude(),
                shelter.getLatitude(),
                shelter.getLongitude()
        );
    }
}

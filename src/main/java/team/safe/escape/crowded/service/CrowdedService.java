package team.safe.escape.crowded.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.crowded.dto.LocationDto;
import team.safe.escape.crowded.dto.response.CrowdedExitResponse;
import team.safe.escape.crowded.entity.CrowdedArea;
import team.safe.escape.crowded.entity.CrowdedAreaExit;
import team.safe.escape.crowded.entity.CrowdedAreaLoc;
import team.safe.escape.crowded.repository.CrowdedAreaExitRepository;
import team.safe.escape.crowded.repository.CrowdedAreaLocRepository;
import team.safe.escape.crowded.repository.CrowdedAreaRepository;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.exit.entity.EmergencyExit;
import team.safe.escape.exit.repository.EmergencyExitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CrowdedService {

    private final CrowdedAreaRepository crowdedAreaRepository;
    private final CrowdedAreaLocRepository crowdedAreaLocRepository;
    private final CrowdedAreaExitRepository crowdedAreaExitRepository;
    private final EmergencyExitRepository emergencyExitRepository;

    public Long deleteCrowded(Long crowdedId) {
        CrowdedArea crowdedArea = crowdedAreaRepository.findById(crowdedId)
                .orElseThrow(() -> new EscapeException(ErrorCode.CROWDED_AREA_NOT_FOUND));

        final long crowdedAreaId = crowdedArea.getId();
        List<Long> exitIdList = getExitIdListByCrowdedAreaId(crowdedAreaId);

        crowdedAreaRepository.delete(crowdedArea);
        emergencyExitRepository.deleteAllById(exitIdList);
        return crowdedAreaId;
    }

    private List<Long> getExitIdListByCrowdedAreaId(long crowdedAreaId) {
        return crowdedAreaExitRepository
                .findByCrowdedAreaId(crowdedAreaId)
                .stream()
                .map(a -> a.getExit().getId())
                .toList();
    }

    public CrowdedExitResponse createCrowded(List<LocationDto> crowdedLocations, List<LocationDto> exitLocations) {
        CrowdedArea crowdedArea = saveCrowdedArea();

        saveCrowdedAreaLoc(crowdedLocations, crowdedArea);
        List<EmergencyExit> savedEmergencyExitList = saveEmergencyExit(exitLocations);
        saveCrowdedAreaExit(savedEmergencyExitList, crowdedArea);

        return CrowdedExitResponse.builder()
                .crowdedLocations(crowdedLocations)
                .exitLocations(exitLocations)
                .build();
    }

    private CrowdedArea saveCrowdedArea() {
        return crowdedAreaRepository.save(CrowdedArea.ofCreate());
    }

    private List<CrowdedAreaExit> saveCrowdedAreaExit(List<EmergencyExit> emergencyExitList, CrowdedArea crowdedArea) {
        List<CrowdedAreaExit> crowdedAreaExitList = emergencyExitList
                .stream()
                .map(exit -> CrowdedAreaExit.ofCreate(crowdedArea, exit))
                .toList();
        return crowdedAreaExitRepository.saveAll(crowdedAreaExitList);
    }

    private List<EmergencyExit> saveEmergencyExit(List<LocationDto> exitLocations) {
        List<EmergencyExit> emergencyExitList = exitLocations
                .stream()
                .map(e -> EmergencyExit.ofCreate(e.getLatitude(), e.getLongitude()))
                .toList();
        return emergencyExitRepository.saveAll(emergencyExitList);
    }

    private List<CrowdedAreaLoc> saveCrowdedAreaLoc(List<LocationDto> crowdedLocations, CrowdedArea crowdedArea) {
        List<CrowdedAreaLoc> crowdedAreaLocList = crowdedLocations
                .stream()
                .map(c -> CrowdedAreaLoc.ofCreate(c.getLatitude(), c.getLongitude(), crowdedArea))
                .toList();
        return crowdedAreaLocRepository.saveAll(crowdedAreaLocList);
    }

}

package team.safe.escape.population.service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.population.dto.PopulationAreaData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PopulationAreaDataLoader {
    private static final String CSV_NAME = "/population_areas.csv";
    private static final String COLUMN_CATEGORY = "CATEGORY";
    private static final String COLUMN_AREA_CD = "AREA_CD";
    private static final String COLUMN_AREA_NM = "AREA_NM";
    private static final String COLUMN_LONGITUDE = "경도";
    private static final String COLUMN_LATITUDE = "위도";
    private static final List<String> WANTED_HEADERS = List.of(COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_CATEGORY, COLUMN_AREA_NM, COLUMN_AREA_CD);


    public List<PopulationAreaData> loadPopulationData() {
        InputStream inputStream = getClass().getResourceAsStream(CSV_NAME);
        if (inputStream == null) {
            throw new EscapeException(ErrorCode.CSV_ERROR, "CSV 파일을 찾을 수 없습니다: " + CSV_NAME);
        }

        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
                .build()) {

            String[] headers = csvReader.readNext();
            if (headers == null) {
                throw new EscapeException(ErrorCode.CSV_ERROR, "빈 CSV 파일입니다.");
            }

            Map<String, Integer> headerIndexMap = mapHeaders(headers);
            List<PopulationAreaData> populationAreaDataList = new ArrayList<>();
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                populationAreaDataList.add(extractPopulationData(row, headerIndexMap));
            }

            return populationAreaDataList;
        } catch (Exception ex) {
            throw new EscapeException(ErrorCode.CSV_ERROR, ex.getMessage());
        }
    }

    private Map<String, Integer> mapHeaders(String[] headers) throws IOException {
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            if (WANTED_HEADERS.contains(headers[i])) {
                indexMap.put(headers[i], i);
            }
        }

        List<String> missingHeaders = new ArrayList<>();
        for (String wantedHeader : WANTED_HEADERS) {
            if (!indexMap.containsKey(wantedHeader)) {
                missingHeaders.add(wantedHeader);
            }
        }
        if (!missingHeaders.isEmpty()) {
            throw new IOException("CSV 파일에 필수 헤더가 없습니다: " + missingHeaders);
        }

        return indexMap;
    }


    private PopulationAreaData extractPopulationData(String[] row, Map<String, Integer> headerIndexMap) {
        String latitude = safeGet(row, headerIndexMap.get(COLUMN_LATITUDE));
        String longitude = safeGet(row, headerIndexMap.get(COLUMN_LONGITUDE));
        String category = safeGet(row, headerIndexMap.get(COLUMN_CATEGORY));
        String areaCode = safeGet(row, headerIndexMap.get(COLUMN_AREA_CD));
        String areaName = safeGet(row, headerIndexMap.get(COLUMN_AREA_NM));

        return PopulationAreaData.builder()
                .categoryName(category)
                .areaName(areaName)
                .areaCode(areaCode)
                .latitude(Double.parseDouble(latitude))
                .longitude(Double.parseDouble(longitude))
                .build();
    }

    private String safeGet(String[] array, int index) {
        if (index < 0 || index >= array.length) {
            return "";  // 빈 문자열 또는 null 반환으로 조정 가능
        }
        return array[index];
    }

}

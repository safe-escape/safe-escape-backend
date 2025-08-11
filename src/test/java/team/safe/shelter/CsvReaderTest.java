package team.safe.shelter;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvReaderTest {
    @Test
    @DisplayName("shelters.csv 파일을 읽을 수 있다.")
    void readCsvFile() {
        // given
        String csvName = "/shelters.csv";

        // when
        InputStream inputStream = getClass().getResourceAsStream(csvName);

        // then
        assertNotNull(inputStream, "CSV 파일을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("shelters.csv 파일의 컬럼명이 일치한다")
    void checkFileColumn() throws IOException, CsvValidationException {
        // given
        String csvName = "/shelters.csv";
        List<String> expectedColumns = Arrays.asList(
                "시설번호", "지역코드", "시설일련번호", "시도명", "시군구명", "수용시설명",
                "법정동코드", "행정동코드", "상세주소", "시설면적", "경도", "위도", "X좌표", "Y좌표"
        );
        InputStream inputStream = getClass().getResourceAsStream(csvName);

        // when
        assertNotNull(inputStream, "CSV 파일을 찾을 수 없습니다.");
        try (CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream))
                .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
                .build()) {

            String[] header = csvReader.readNext();
            assertNotNull(header, "CSV 파일이 비어있습니다.");

            if (header[0].startsWith("\uFEFF")) {
                header[0] = header[0].substring(1);
            }

            List<String> actualColumns = Arrays.asList(header);
            // then
            assertEquals(expectedColumns, actualColumns, "CSV 헤더 컬럼명이 예상과 다릅니다.");
        }
    }
}

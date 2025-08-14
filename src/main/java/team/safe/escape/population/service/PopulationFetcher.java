package team.safe.escape.population.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import team.safe.escape.population.dto.PopulationApiResponse;

@Component
@RequiredArgsConstructor
public class PopulationFetcher {

    @Value("${OPENAPI_KEY}")
    private String openApiKey;

    private final WebClient webClient;
    public static final String CITY_DATA_URL = "http://openapi.seoul.go.kr:8088/%s/json/citydata_ppltn/1/5/%s";

    public PopulationApiResponse fetchAreaForecast(String areaCode) {
        String url = buildUrl(areaCode);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(PopulationApiResponse.class)
                .block();
    }

    private String buildUrl(String areaCode) {
        return String.format(CITY_DATA_URL, openApiKey, areaCode);
    }

}

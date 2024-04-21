package org.example.servicerecommendaccommodation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.servicerecommendaccommodation.data.Hotel;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class RecommenderServiceImpl implements RecommenderService {

    @Value("${amadeus.clientId}")
    private String apiKey;

    @Value("${amadeus.clientSecret}")
    private String secretKey;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = restTemplateBuilder.build();
    }

    protected String authenticate() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", apiKey);
        map.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://test.api.amadeus.com/v1/security/oauth2/token", HttpMethod.POST, request, String.class);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.getBody());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            return jsonObject.getString("access_token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpEntity<String> buildRestTemplate() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = authenticate();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }
    public ArrayList<Hotel> searchHotelsByCityCode(String cityCode) throws JSONException {
        HttpEntity<String> entity = buildRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?cityCode=" + cityCode, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList<Hotel> hotels;
        try {
            hotels = mapper.readValue(jsonArray.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    @Override
    public String getCityCode(String city) throws JSONException {
        WebClient webClient = WebClient.create();
        String accessToken = authenticate();
        Mono<String> response = webClient.get()
                .uri("https://test.api.amadeus.com/v1/reference-data/locations/cities?keyword=" + city)
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String responseBody = response.block();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseBody);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            return jsonObject.getJSONArray("data").getJSONObject(0).getString("iataCode");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

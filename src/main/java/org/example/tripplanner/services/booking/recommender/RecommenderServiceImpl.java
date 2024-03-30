package org.example.tripplanner.services.booking.recommender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.example.tripplanner.data.booking.Hotel;
import org.example.tripplanner.services.booking.AmadeusConfig;
import org.example.tripplanner.utils.Print;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class RecommenderServiceImpl extends AmadeusConfig implements RecommenderService {

    @Value("${amadeus.clientId}")
    private String apiKey;

    @Value("${amadeus.clientSecret}")
    private String secretKey;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;
    Print print = new Print();

    @PostConstruct
    public void init() {
        restTemplate = restTemplateBuilder.build();
    }

    protected String authenticate(){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", apiKey);
        map.add("client_secret", secretKey);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://test.api.amadeus.com/v1/security/oauth2/token", HttpMethod.POST, request, String.class);
        JSONObject jsonObject = new JSONObject(response.getBody());
        return jsonObject.getString("access_token");
    }

    private HttpEntity<String> buildRestTemplate() {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = authenticate();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>("parameters", headers);
    }


    public ArrayList<Hotel> searchHotelsByCityCode(String cityCode) {
        HttpEntity<String> entity = buildRestTemplate();
        print.print(this.getClass(),"Code: "+ cityCode);
        ResponseEntity<String> response = restTemplate.exchange("https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?cityCode=" + cityCode, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        ArrayList<Hotel> hotels = null;
        try {
            hotels = mapper.readValue(jsonArray.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return hotels;
    }

    @Override
    public String getCityCode(String city) {
        HttpEntity<String> entity = buildRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://test.api.amadeus.com/v1/reference-data/locations/cities?keyword=" + city, HttpMethod.GET, entity, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        String name = jsonObject.getJSONArray("data").getJSONObject(0).getString("iataCode");
        return name;
    }
}

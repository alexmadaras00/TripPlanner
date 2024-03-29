package org.example.tripplanner.services.booking.select_accommodation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.example.tripplanner.data.booking.Hotel;
import org.example.tripplanner.data.booking.HotelOfferResponse;
import org.example.tripplanner.services.booking.AmadeusConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
public class SelectAccommodationImpl extends AmadeusConfig implements SelectAccommodation {
    @Value("${amadeus.clientId}")
    private String apiKey;

    @Value("${amadeus.clientSecret}")
    private String secretKey;

    RestTemplateBuilder restTemplateBuilder;
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = restTemplateBuilder.build();
    }

    protected String authenticate() throws JSONException {
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

    private HttpEntity<String> buildRestTemplate() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        String accessToken = authenticate();
        headers.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>("parameters", headers);
    }

    @Override
    public HotelOfferResponse receiveOffer(ArrayList<String> hotelID) throws JSONException {
        HttpEntity<String> entity = buildRestTemplate();
        String result = String.join(", ", hotelID);
        ResponseEntity<HotelOfferResponse> response = restTemplate.exchange("https://test.api.amadeus.com/v3/shopping/hotel-offers?hotelIds="+result+"&adults=1&checkInDate=2024-11-20&checkOutDate=2024-11-24&roomQuantity=1&paymentPolicy=NONE&bestRateOnly=true", HttpMethod.GET, entity, HotelOfferResponse.class);
        return response.getBody();
    }
}

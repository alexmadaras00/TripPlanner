package org.example.tripplanner.services.booking.select_accommodation;

import jakarta.annotation.PostConstruct;
import org.example.tripplanner.domain.booking.HotelOfferResponse;
import org.example.tripplanner.services.booking.AmadeusConfig;
import org.example.tripplanner.utils.Print;
import org.json.JSONException;
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

import java.util.Objects;

@Service
public class SelectAccommodationImpl extends AmadeusConfig implements SelectAccommodation {
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
    public HotelOfferResponse receiveOffer(String hotelID) throws JSONException {
        HttpEntity<String> entity = buildRestTemplate();

        print.print(this.getClass(),"HotelIDS: "+ hotelID);

        ResponseEntity<HotelOfferResponse> response = restTemplate.exchange("https://test.api.amadeus.com/v3/shopping/hotel-offers?hotelIds="+hotelID, HttpMethod.GET, entity, HotelOfferResponse.class);
        print.print(this.getClass(),"Response: "+ Objects.requireNonNull(response.getBody()).getData().get(0).getHotel().getName());
        return response.getBody();
    }
}

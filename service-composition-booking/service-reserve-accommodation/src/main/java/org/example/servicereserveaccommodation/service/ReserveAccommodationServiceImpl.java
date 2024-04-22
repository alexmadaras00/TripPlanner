package org.example.servicereserveaccommodation.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import okhttp3.*;
import org.example.servicereserveaccommodation.controller.ReserveAccommodationController;
import org.example.servicereserveaccommodation.data.BookingForm;
import org.example.servicereserveaccommodation.data.Data;
import org.example.servicereserveaccommodation.domain.Booking;
import org.example.servicereserveaccommodation.domain.BookingData;
import org.json.JSONArray;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReserveAccommodationServiceImpl implements ReserveAccommodationService {
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
        System.out.println(accessToken);
        headers.add("Authorization", "Bearer " + accessToken);

        return new HttpEntity<>(headers);
    }

    @SneakyThrows
    public BookingData bookHotel(BookingForm form) {
        // Initialize using parameters
        HttpEntity<String> entity = buildRestTemplate();
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(form);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );
        String url = "https://test.api.amadeus.com/v1/booking/hotel-bookings";
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);

        // Add headers from entity to request
        for (Map.Entry<String, List<String>> header : entity.getHeaders().entrySet()) {
            if (!header.getValue().isEmpty()) {
                requestBuilder.addHeader(header.getKey(), header.getValue().get(0));
            }
        }

        Request request = requestBuilder.build();
        BookingData bookingData = new BookingData();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = "";
            if (response.body() != null && response.code() == 200) {
                responseBody = response.body().string();

                bookingData = objectMapper.readValue(responseBody, BookingData.class);
            } else {
                responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray errors = jsonObject.getJSONArray("errors");
                for (int i = 0; i < errors.length(); i++) {
                    JSONObject error = errors.getJSONObject(i);
                    System.out.println("Error code: " + error.getInt("code"));
                    System.out.println("Error title: " + error.getString("title"));
                    System.out.println("Error detail: " + error.getString("detail"));
                    System.out.println("Error status: " + error.getInt("status"));
                }
                ReserveAccommodationController controller = new ReserveAccommodationController();
                controller.getErrorPage();
                Booking bookingError = new Booking();
                List<Booking> errorsData = new ArrayList<>();
                String errrorDetail = errors.getJSONObject(0).getString("detail")+'\n'+errors.getJSONObject(0).getString("title");
                bookingError.setType(errrorDetail);
                errorsData.add(bookingError);
                bookingData.setData(errorsData);
            }
            System.out.println("Response: " + responseBody);
        }
        return bookingData;
    }

}

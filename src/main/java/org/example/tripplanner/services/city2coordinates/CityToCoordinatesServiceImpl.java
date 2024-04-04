package org.example.tripplanner.services.city2coordinates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CityToCoordinatesServiceImpl implements CityToCoordinatesService {
    @Value("${openweathermap.host}")
    private String host;
    @Value("${openweathermap.key}")
    private String key;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    public ArrayList getConvertedCityIntoCoordinatesWithSpringRest(String city) {
        String url = host + "/geo/1.0/direct?q=" + city + "&limit=1&appid=" + key;
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(url, ArrayList.class);
    }
}

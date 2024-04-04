package org.example.tripplanner.services.nearby_places;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FindPlacesImpl implements FindPlaces {
    @Value("${googleplaces.host}")
    private String host;
    @Value("${googleplaces.key}")
    private String key;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Override
    public ArrayList getNearbyPlaces(double latitude, double longitude) {
        String url = host + "/nearbysearch/json?location=" + latitude + "," + longitude +
                "&radius=5000&key=" + key;
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(url, ArrayList.class);
    }
}

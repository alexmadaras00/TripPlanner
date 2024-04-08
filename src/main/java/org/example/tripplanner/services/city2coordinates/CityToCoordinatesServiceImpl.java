package org.example.tripplanner.services.city2coordinates;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CityToCoordinatesServiceImpl implements CityToCoordinatesService {
    @Value("${geocoding.key}")
    private String apiKey;
    @Value("${geocoding.host}")
    private String address;

    @Override
    public List<String> getCoordinatesFromCity(String cityName) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        GeocodingResult[] response = GeocodingApi.geocode(context,
                cityName).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<String> responseFinal = List.of(gson.toJson(response[0].geometry.location.lat),gson.toJson(response[0].geometry.location.lng));
        context.shutdown();
        return responseFinal;
    }
}

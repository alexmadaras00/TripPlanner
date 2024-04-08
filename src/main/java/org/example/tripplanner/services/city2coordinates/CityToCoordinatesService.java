package org.example.tripplanner.services.city2coordinates;

import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;


public interface CityToCoordinatesService {
    List<String> getCoordinatesFromCity(String cityName) throws IOException, InterruptedException, ApiException;

}

package org.example.tripplanner.services.booking.recommender;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.tripplanner.data.booking.Hotel;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface RecommenderService {
    ArrayList<Hotel> searchHotelsByCityCode(String cityCode) throws JSONException, JsonProcessingException;
    String getCityCode(String city) throws JSONException;
}

package org.example.servicerecommendaccommodation.service;

import org.example.servicerecommendaccommodation.data.Hotel;
import org.json.JSONException;

import java.util.ArrayList;

public interface RecommenderService {
    String getCityCode(String city) throws JSONException;
    ArrayList<Hotel> searchHotelsByCityCode(String cityCode) throws JSONException;

}

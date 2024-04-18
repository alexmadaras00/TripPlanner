package org.example.servicerecommendaccommodation.service;

import data.Hotel;
import org.json.JSONException;

import java.util.ArrayList;

public interface RecommenderService {
    String getCityCode(String city) throws JSONException;
    ArrayList<Hotel> searchHotelsByCityCode(String cityCode) throws JSONException;

}

package org.example.tripplanner.services.booking.recommender;

import org.example.tripplanner.data.booking.Hotel;

import java.util.ArrayList;

public interface RecommenderService {
    String getCityCode(String city);
    ArrayList<Hotel> searchHotelsByCityCode(String cityCode);

}

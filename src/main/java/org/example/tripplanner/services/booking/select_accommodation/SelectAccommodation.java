package org.example.tripplanner.services.booking.select_accommodation;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.tripplanner.data.booking.HotelOfferResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface SelectAccommodation {
        HotelOfferResponse receiveOffer(ArrayList<String> hotelID) throws JSONException, JsonProcessingException;
}

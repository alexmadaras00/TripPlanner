package org.example.tripplanner.services.booking.select_accommodation;


import org.example.tripplanner.domain.booking.HotelOfferResponse;

import org.springframework.stereotype.Service;

import java.util.ArrayList;


public interface SelectAccommodation {
        HotelOfferResponse receiveOffer(String hotelID) ;
}

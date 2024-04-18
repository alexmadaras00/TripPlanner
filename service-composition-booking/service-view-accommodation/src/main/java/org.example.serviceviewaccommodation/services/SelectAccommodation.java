package org.example.tripplanner.services.booking.select_accommodation;


import org.example.tripplanner.domain.booking.HotelOfferResponse;


public interface SelectAccommodation {
        HotelOfferResponse receiveOffer(String hotelID) ;
}

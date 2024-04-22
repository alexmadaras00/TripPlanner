package org.example.servicereserveaccommodation.service;

import com.amadeus.exceptions.ResponseException;
import org.example.servicereserveaccommodation.data.BookingForm;
import org.example.servicereserveaccommodation.domain.BookingData;

public interface ReserveAccommodationService {
    BookingData bookHotel(BookingForm form) throws ResponseException;

}
